package entities;

import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[] idleAnim, RunAnim,attackAnim;

    private int animTick, animIndex, animSpeed = 15;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left;
    private boolean up;
    private boolean right;
    private boolean down;
    private float playerSpeed = 2.0f;


    public Player(float x, float y) {
        super(x, y);
        RunAnim = loadAnimations("Run (32x32).png");
        idleAnim = loadAnimations("Idle (32x32).png");
        //attackAnim = loadAnimations("_Attack.png");

    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();

    }


    public void render(Graphics g) {
        if(playerAction == IDLE)
            g.drawImage(idleAnim[animIndex],(int) x,(int) y,100,100,null);
        if(playerAction == RUNNING)
            g.drawImage(RunAnim[animIndex],(int) x,(int) y,100,100,null);
        if(playerAction == ATTACK)
            g.drawImage(attackAnim[animIndex],(int) x,(int) y,300,200,null);

    }

    private void updatePos() {

        moving = false;

        if(left && !right){
            x -= playerSpeed;
            moving = true;
        }else if (right && !left){
            x += playerSpeed;
            moving = true;
        }

        if(up && !down){
            y -= playerSpeed;
            moving = true;
        }else if(down && !up){
            y += playerSpeed;
            moving = true;
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

        if(startAni != playerAction) { //verificam daca schimbam animatia (ex: e setata pe idle si nu s-a terminat si o setam pe animatia de attack)
            resetAniTick();             //resetam tick-urile si index-ul
        }

    }

    private void resetAniTick() {
        animTick = 0;
        animIndex = 0;
    }


    private BufferedImage[] loadAnimations(String path) {

            BufferedImage image = LoadSave.GetSpriteAtlas(path);

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

}

