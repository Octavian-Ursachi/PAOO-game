package entities;

import gameStates.Playing;
import levels.LevelManager;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static main.Game.SCALE;
import static utils.Constants.PlayerConstants.*;
import utils.HelpMethods;
import static main.Game.TILES_SIZE;

public class Player extends Entity {
    private BufferedImage[] idleAnim, RunAnim,attackAnim, jumpAnim,fallAnim,hitAnim;
    private int[][][] levelData;
    private int animTick, animIndex, animSpeed = 15;
    private boolean attackDelay = false;
    private int attackSpeed = 100;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false, jumping = false;
    private boolean left, up ,right ,down,jump;
    private float playerSpeed = 1.0f * SCALE;
    private float hitboxOffset = 9 * Game.SCALE; //pt hitbox
    public boolean isDead = false;

    //Jumping/Gravity
    private double airSpeed = 0f;
    private float gravity = 0.04f* Game.SCALE;
    private double jumpSpeed = -2.25* Game.SCALE;
    private boolean inAir = true;

    private static boolean canDoubleJump = true;
    private boolean firstJump = true;
    private double fallSpeedAfterCollision = 0.5f * Game.SCALE;
    //AtackBox
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked = false;
    private Playing playing;
    public boolean hasMoved = false;

    //Shurikens
    private Shuriken[] shurikens = new Shuriken[3];
    private boolean throwShuriken;

    //ROTATION/KILL
    private boolean hitFromLeft;
    private boolean hitFromRight;
    private boolean hitFromAbove;
    private static int rotation = 0;
    private BufferedImage imageToRotate;
    int randomValue;
    boolean endHitAnim = false;
    boolean airSpeedChanged = false;

    public Player(float x, float y, Playing playing) throws EntityCoordsLessThanZeroException {
        super(x, y,TILES_SIZE*2,TILES_SIZE*2);
        this.playing = playing;
        initHitbox(x+hitboxOffset,y+hitboxOffset,17 * Game.SCALE,18 * Game.SCALE);
        initAttackBox();
        initShurikens();
        loadLevelData(LevelManager.getCurrentLevel().getLevelData());
        RunAnim = loadAnimations(LoadSave.PLAYER_RUNNING_ATLAS);
        idleAnim = loadAnimations(LoadSave.PLAYER_IDLE_ATLAS);
        jumpAnim = loadAnimations(LoadSave.PLAYER_JUMP_ATLAS);
        fallAnim = loadAnimations("Fall (32x32).png");
        attackAnim = loadAnimations("Attack (SWORD).png");
        hitAnim = loadAnimations("Hit (32x32).png");
        imageToRotate = hitAnim[6];

        //attackAnim = loadAnimations("_Attack.png");
    }

    public void updatePosAfterDeath() {
        if(!airSpeedChanged) {
            airSpeed = jumpSpeed;
            airSpeedChanged = true;
        }
        if(hitFromLeft) {
            hitbox.x--;
            hitbox.y = (int)(hitbox.y + airSpeed);
            airSpeed += gravity;
        } else if(hitFromRight) {
            hitbox.x++;
            hitbox.y = (int)(hitbox.y + airSpeed);
            airSpeed += gravity;
        } else if( hitFromAbove) {
            hitbox.y = (int)(hitbox.y + airSpeed);
            airSpeed += gravity;
        }

    }

    public void kill() {
        isDead = true;
        playerAction = HIT;
    }

    public void setPlayerPositionAfterLvlChanges(LevelManager levelManager) {
        int[] coord = LoadSave.getPlayerCoord(levelManager);
        this.x = coord[0];
        this.y = coord[1];
        this.hitbox.x = coord[0];
        this.hitbox.y = coord[1];
    }

    public void throwShuriken() {
        if(throwShuriken) {
            for(int i = 0 ; i < 3 ; i++) {
                if(!shurikens[i].used) {
                    if(shurikens[i].getDir() != 0) {
                        shurikens[i].setDir(flipW);
                    }
                    shurikens[i].used = true;
                    shurikens[i].active = true;
                    System.out.println("SHURIKEN THROWED!!!");
                    break;
                }
            }
        }
        throwShuriken = false;
    }

    public void initShurikens() {
        for(int i = 0 ; i < 3 ; i++) {
            try {
                shurikens[i] = new Shuriken(hitbox.x, hitbox.y, this,levelData);
            } catch (EntityCoordsLessThanZeroException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean allShurikenUsed() {
        if(shurikens[0].used && shurikens[1].used && shurikens[2].used)
            return true;
        return false;
    }

    public void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(20* SCALE),(int)(20 * SCALE));

    }

    public void loadLevelData(int[][][] levelData){
        this.levelData = levelData;
    }

    public void update() {
        updateAttackBox();
        if(moving)
            playing.checkSpikesTouched(this);

        updatePos();
        if(attacking)
            checkAttack();
        checkShurikenAttack();

        if(throwShuriken)
            throwShuriken();
        for(int i = 0 ; i < 3 ; i++) {
                shurikens[i].updatePos(levelData);
        }

        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if(attackChecked)
            return;
        attackChecked = true;
            playing.checkEnemyHit(attackBox);
    }

    private void checkShurikenAttack() {
        if(!shurikens[0].active && !shurikens[1].active && !shurikens[2].active) {
            return;
        }
        playing.checkEnemyHitWithShuriken(shurikens);
    }

    private void updateAttackBox() {
        if(right) {
            attackBox.x = hitbox.x + hitbox.width + (int)(SCALE * 10);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int)(SCALE * 12);
        }
        attackBox.y = hitbox.y + (SCALE * 5);
    }

    public void render(Graphics g, int lvlOffset) {

        //HIT/ROTATION
        Graphics2D g2d = (Graphics2D)g;
        Random random = new Random();
        if(!isDead)
            randomValue = random.nextInt(2) * 2 - 1; //get a value of 1 or -1
        if(isDead) {
            updateAnimationTick();
            rotation++;
            if(rotation >= 360)
                rotation = 0;
            float rotationX = hitbox.x+hitbox.width/2;
            float rotationY = hitbox.y+hitbox.height/2;
            if(!endHitAnim && hitbox.y < Game.GAME_HEIGHT * SCALE) {
                g2d.rotate(Math.toRadians(rotation * randomValue), rotationX, rotationY);
                g2d.drawImage(hitAnim[animIndex], (int) (hitbox.x - hitboxOffset) + flipX, (int) (hitbox.y - hitboxOffset) - lvlOffset, TILES_SIZE * 2 * flipW, TILES_SIZE * 2, null);
                g2d.rotate(-Math.toRadians(rotation * randomValue), rotationX, rotationY);
            } else if(endHitAnim && hitbox.y < Game.GAME_HEIGHT * SCALE) {
                g2d.rotate(Math.toRadians(rotation * randomValue), rotationX, rotationY);
                g2d.drawImage(imageToRotate, (int) (hitbox.x - hitboxOffset) + flipX, (int) (hitbox.y - hitboxOffset) - lvlOffset, TILES_SIZE * 2 * flipW, TILES_SIZE * 2, null);
                g2d.rotate(-Math.toRadians(rotation * randomValue), rotationX, rotationY);
            }

            if(animIndex >= 7) {
                endHitAnim = true;
            }
            return;
        }
        //HIT/ROTATION

        if(playerAction == IDLE){
            g.drawImage(idleAnim[animIndex],(int)(hitbox.x - hitboxOffset) + flipX,(int)( hitbox.y - hitboxOffset) - lvlOffset,TILES_SIZE*2 * flipW,TILES_SIZE*2,null);
        }
        if(playerAction == RUNNING) {
            g.drawImage(RunAnim[animIndex], (int)(hitbox.x - hitboxOffset) + flipX,(int)( hitbox.y - hitboxOffset)- lvlOffset, TILES_SIZE*2 * flipW,TILES_SIZE*2, null);
        }

        if(playerAction == JUMP) {
            g.drawImage(jumpAnim[0], (int) (hitbox.x - hitboxOffset) + flipX, (int) (hitbox.y - hitboxOffset)- lvlOffset, TILES_SIZE * 2 * flipW, TILES_SIZE * 2, null);
        }
        if(playerAction == FALLING) {
            g.drawImage(fallAnim[0], (int) (hitbox.x - hitboxOffset) + flipX, (int) (hitbox.y - hitboxOffset)- lvlOffset, TILES_SIZE * 2 * flipW, TILES_SIZE * 2, null);

        }
        if(playerAction == ATTACK){
            attackDelay = true;
        }
        if(attackDelay == true)
        {
            g.drawImage(attackAnim[0],(int)(hitbox.x - hitboxOffset + 55),(int)( hitbox.y - hitboxOffset - 10) - lvlOffset,TILES_SIZE*2 * flipW,TILES_SIZE*2,null);
            attackSpeed--;
            if(attackSpeed < 0)
            {
                attackSpeed = 100;
                attackDelay = false;
            }
        }

        for(int i = 0 ; i < 3 ; i++) {
            if(shurikens[i].active) {
                shurikens[i].draw(g,lvlOffset);
            }
        }

        //drawHitbox(g,lvlOffset);
        //drawAttackBox(g,lvlOffset);

    }

    private void drawAttackBox(Graphics g, int lvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x,(int)attackBox.y - lvlOffset,(int)attackBox.width,(int)attackBox.height);
    }

    private void updateAnimationTick() {
        animTick++;
        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(playerAction)) {
                animIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {

        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if(attacking)
            playerAction = ATTACK;

        if(inAir)
        {
            if(airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;

            if(attacking)
                playerAction = ATTACK;

        }

        if(startAni != playerAction) { //verificam daca schimbam animatia (ex: e setata pe idle si nu s-a terminat si o setam pe animatia de attack)
            resetAniTick();             //resetam tick-urile si index-ul
        }

    }

    private void updatePos() {

        moving = false;

        if(jump)
            jump();

        if(!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if(left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
            hasMoved = true;
        }
        if (right ) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
            hasMoved = true;
        }
        if(!inAir){
            if(!HelpMethods.IsEntityOnFloor(hitbox,levelData)) {
                inAir = true;
            }
        }

        if(inAir){

            if(HelpMethods.canMoveHere(hitbox.x, (float)(hitbox.y + airSpeed), hitbox.width, hitbox.height,levelData)) {
                hitbox.y = (int)(hitbox.y + airSpeed);
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else {
                hitbox.y = HelpMethods.GetPlayerYPosUnderRoofOrAboveFloor(hitbox,(float)airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        }else{
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void updateXPos(float xSpeed){

        if(HelpMethods.canMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,levelData)) // coliziune
        {
            hitbox.x += xSpeed;
        }else{
            hitbox.x = HelpMethods.GetEntityXPosNextToWall(hitbox,xSpeed);
        }

    }

    private void jump() {
        if((inAir) && !canDoubleJump) {
            return;
        }
        if(!inAir && firstJump) {
            inAir = true;
            firstJump = false;
            airSpeed = jumpSpeed;
        } else if(inAir && firstJump) {
            inAir = true;
            airSpeed = jumpSpeed;
            canDoubleJump = false;
        }
        hasMoved = true;

    }


    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        canDoubleJump = true;
    }

    private void resetAniTick() {
        animTick = 0;
        animIndex = 0;
    }

    private BufferedImage[] loadAnimations(String atlas) {

            BufferedImage image = LoadSave.GetSpriteAtlas(atlas);

            BufferedImage[] type = new BufferedImage[12];
            for (int i = 0; i < type.length; i++) {
                if (i * 32 + 32 <= image.getWidth())
                    type[i] = image.getSubimage(i * 32, 0, 32, 32);
            }

            return type;
    }

    public void setThrowShuriken(boolean throwShuriken) {this.throwShuriken = throwShuriken;}

    public Shuriken[] getShurikens() {
        return shurikens;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump)
    {
        this.jump = jump;
    }

    public void setFirstJump(boolean released)
    {
        this.firstJump = released;
    }

    public void setHitFromLeft(boolean hitFromLeft) {
        this.hitFromLeft = hitFromLeft;
    }

    public void setHitFromRight(boolean hitFromRight) {
        this.hitFromRight = hitFromRight;
    }

    public void setHitFromAbove(boolean hitFromAbove) {
        this.hitFromAbove = hitFromAbove;
    }

    public boolean getInAir() {
        return inAir;
    }

    public void resetAll() {
        inAir = true;
        attacking = false;
        moving = false;
        hasMoved = false;
        isDead = false;
        endHitAnim = false;
        airSpeedChanged = false;
        playerAction = IDLE;
        for(int i = 0 ; i < 3 ; i++) {
            shurikens[i].used = false;
            shurikens[i].active = false;
        }

        rotation = 0;
        hitbox.x = x;
        hitbox.y = y;

    }
}
