package entities;

import levels.LevelManager;
import main.Game;
import utils.LoadSave;


import java.awt.*;
import java.awt.image.BufferedImage;


import static main.Game.SCALE;
import static utils.Constants.PlayerConstants.*;
import utils.HelpMethods;
import static main.Game.TILES_SIZE;
import levels.LevelManager;

public class Player extends Entity {

    private BufferedImage[] idleAnim, RunAnim,attackAnim, RunAnimleft, jumpAnim,fallAnim;


    private int[][][] levelData;

    private int animTick, animIndex, animSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false, jumping = false;
    private boolean left, up ,right ,down,jump;
    private float playerSpeed = 1.0f * SCALE;
    private float hitboxOffset = 9 * Game.SCALE; //pt hitbox

    //Jumping/Gravity
    private double airSpeed = 0f;
    private float gravity = 0.04f* Game.SCALE;
    private double jumpSpeed = -2.25* Game.SCALE;
    private boolean inAir = false,canDoubleJump = false;


    private double fallSpeedAfterCollision = 0.5f * Game.SCALE;

    public Player(float x, float y) {
        super(x, y,TILES_SIZE*2,TILES_SIZE*2);
        initHitbox(x+hitboxOffset,y+hitboxOffset,17 * Game.SCALE,18 * Game.SCALE);
        loadLevelData(LevelManager.getCurrentLevel().getLevelData());
        RunAnim = loadAnimations(LoadSave.PLAYER_RUNNING_ATLAS);
        idleAnim = loadAnimations(LoadSave.PLAYER_IDLE_ATLAS);
        RunAnimleft = loadAnimations(LoadSave.PLAYER_RUNNING_LEFT_ATLAS);
        jumpAnim = loadAnimations(LoadSave.PLAYER_JUMP_ATLAS);
        fallAnim = loadAnimations("Fall (32x32).png");

        //attackAnim = loadAnimations("_Attack.png");

    }

    public void loadLevelData(int[][][] levelData){
        this.levelData = levelData;
    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();

    }


    public void render(Graphics g) {
        if(playerAction == IDLE){
            g.drawImage(idleAnim[animIndex],(int)(hitbox.x - hitboxOffset),(int)( hitbox.y - hitboxOffset),TILES_SIZE*2,TILES_SIZE*2,null);
            drawHitbox(g);
        }
        if(playerAction == RUNNING) {
            if(right && !left){
                g.drawImage(RunAnim[animIndex], (int)(hitbox.x - hitboxOffset),(int)( hitbox.y - hitboxOffset), TILES_SIZE*2,TILES_SIZE*2, null);
                drawHitbox(g);
            }
            else if(left && !right){
                g.drawImage(RunAnimleft[animIndex], (int)(hitbox.x - hitboxOffset),(int)( hitbox.y - hitboxOffset), TILES_SIZE*2,TILES_SIZE*2, null);
                drawHitbox(g);
            }
            else if(left && right){
                g.drawImage(idleAnim[animIndex],(int)(hitbox.x - hitboxOffset),(int)( hitbox.y - hitboxOffset),TILES_SIZE*2,TILES_SIZE*2,null);
                drawHitbox(g);
            }

        }
        if(playerAction == ATTACK){
            g.drawImage(attackAnim[animIndex],(int)(hitbox.x - hitboxOffset),(int)( hitbox.y - hitboxOffset),TILES_SIZE*2,TILES_SIZE*2,null);
            drawHitbox(g);
        }

        if(playerAction == JUMP) {
            g.drawImage(jumpAnim[0], (int) (hitbox.x - hitboxOffset), (int) (hitbox.y - hitboxOffset), TILES_SIZE * 2, TILES_SIZE * 2, null);
            drawHitbox(g);
        }
        if(playerAction == FALLING) {
            g.drawImage(fallAnim[0], (int) (hitbox.x - hitboxOffset), (int) (hitbox.y - hitboxOffset), TILES_SIZE * 2, TILES_SIZE * 2, null);
            drawHitbox(g);
        }

    }

    private void updateAnimationTick() {
        animTick++;
        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;
            if (animIndex >= GetSpriteAmount(playerAction)) {
                animIndex = 0;
                attacking = false;
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

        }

        if(startAni != playerAction) { //verificam daca schimbam animatia (ex: e setata pe idle si nu s-a terminat si o setam pe animatia de attack)
            resetAniTick();             //resetam tick-urile si index-ul
        }

    }

    private void updatePos() {

        moving = false;

        if(HelpMethods.IsEntityOnFloor(hitbox,levelData))
            setCanDoubleJump(true);


        if(jump) {

            jump();
        }

        if(canDoubleJump && inAir && jump)
        {
            doubleJump();
        }


        if(!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if(left)
            xSpeed -= playerSpeed;
        if (right )
            xSpeed += playerSpeed;

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
        if(inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
        System.out.println("JUMP!");
    }

    private void doubleJump() {

        if(!inAir)
            return;

        inAir = true;
        airSpeed = jumpSpeed;
        canDoubleJump = false;
        System.out.println("DOUBLEJUMP!");
    }


    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
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

    public void setCanDoubleJump(boolean jump)
    {
        this.canDoubleJump = jump;
    }




}

