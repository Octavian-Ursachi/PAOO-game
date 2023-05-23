package entities;

import gameStates.Playing;
import levels.Level;
import main.Game;
import utils.HelpMethods;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


import static main.Game.TILES_SIZE;
import static utils.Constants.EnemyConstant.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] piggyArr;
    private BufferedImage[][] piggyBloodArr;
    private ArrayList<Piggy> piggies = new ArrayList<>();
    private boolean allEnemyDead = false;
    private int rotation = 1;
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImager();
    }

    public void loadEnemies(Level level) {
        piggies = level.getPigs();
    }

    public void update(){
        allEnemyDead = true;
        for(Piggy p : piggies) {
            if (p.isActive()) {
                p.update();
                allEnemyDead = false;
            }
            else {
                p.updatePosAfterDeath();
                //p.hitbox.x = 3000;
                //p.hitbox.y = 3000;
            }
        }
        if(allEnemyDead)
            playing.setGameOver(true);

    }

    public void draw(Graphics g,int yLvlOffset) {
        drawPigs(g,yLvlOffset);
    }

    private void drawPigs(Graphics g,int yLvlOffset) {
        Random random = new Random();
        for(Piggy p : piggies)
            if(p.isActive()) {
                p.randomValue = random.nextInt(2) *2 -1;
                if (p.x > playing.getPlayer().hitbox.x) {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x - PIGGY_DRAWOFFSET_X, (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, PIGGY_WIDTH, PIGGY_HEIGHT, null);
                } else {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) (p.getHitbox().x - PIGGY_DRAWOFFSET_X + p.width + 20), (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, -PIGGY_WIDTH, PIGGY_HEIGHT, null); // la x trebuie sa-mi dau seama ce sa fac in loc sa adaug + 20

                }
            } else {
                Graphics2D g2d = (Graphics2D)g;
                p.updateAnimationTick();
                rotation++;
                if(rotation >= 360)
                    rotation = 0;
                float rotationX = p.getHitbox().x + p.getHitbox().width/2;
                float rotationY = p.getHitbox().y + p.getHitbox().height/2;
                if(!p.oneTimeDeadAnim) {
                    g2d.rotate(Math.toRadians(rotation * p.randomValue), rotationX, rotationY);
                    g2d.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x - PIGGY_DRAWOFFSET_X, (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, PIGGY_WIDTH, PIGGY_HEIGHT, null);
                    g2d.rotate(-Math.toRadians(rotation * p.randomValue), rotationX, rotationY);
                } else {
                    g2d.rotate(Math.toRadians(rotation * p.randomValue), rotationX, rotationY);
                    g.drawImage(piggyArr[p.getEnemyState()][2], (int) p.getHitbox().x - PIGGY_DRAWOFFSET_X, (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, PIGGY_WIDTH, PIGGY_HEIGHT, null);
                    g2d.rotate(-Math.toRadians(rotation * p.randomValue), rotationX, rotationY);

                }
                if(p.getAniIndex() >= 2)
                    p.oneTimeDeadAnim = true;
            }

    }

//    //HIT/ROTATION
//    Graphics2D g2d = (Graphics2D)g;
//    Random random = new Random();
//        if(!isDead)
//    randomValue = random.nextInt(2) * 2 - 1; //get a value of 1 or -1
//        if(isDead) {
//        updateAnimationTick();
//        rotation++;
//        if(rotation >= 360)
//            rotation = 0;
//        float rotationX = hitbox.x+hitbox.width/2;
//        float rotationY = hitbox.y+hitbox.height/2;
//        if(!endHitAnim) {
//            g2d.rotate(Math.toRadians(rotation * randomValue), rotationX, rotationY);
//            g2d.drawImage(hitAnim[animIndex], (int) (hitbox.x - hitboxOffset) + flipX, (int) (hitbox.y - hitboxOffset) - lvlOffset, TILES_SIZE * 2 * flipW, TILES_SIZE * 2, null);
//            g2d.rotate(-Math.toRadians(rotation * randomValue), rotationX, rotationY);
//        } else {
//            g2d.rotate(Math.toRadians(rotation * randomValue), rotationX, rotationY);
//            g2d.drawImage(imageToRotate, (int) (hitbox.x - hitboxOffset) + flipX, (int) (hitbox.y - hitboxOffset) - lvlOffset, TILES_SIZE * 2 * flipW, TILES_SIZE * 2, null);
//            g2d.rotate(-Math.toRadians(rotation * randomValue), rotationX, rotationY);
//        }
//
//        if(animIndex >= 7) {
//            endHitAnim = true;
//        }
//        return;
//    }
//    //HIT/ROTATION

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for(Piggy p : piggies) {
            if (attackBox.intersects(p.getHitbox()) && p.isActive()) {
                if(playing.getPlayer().hitbox.x < p.getHitbox().x) {
                    p.setHitFromLeft(false);
                    p.setHitFromRight(true);
                } else {
                    p.setHitFromLeft(true);
                    p.setHitFromRight(false);
                }
                p.newState(DEAD);
            }
        }
    }
    public void checkEnemyHitWithShuriken(Shuriken[] shurikens) {
        for(Piggy p : piggies) {
            for(int i = 0 ; i < 3 ; i++) {
                if (shurikens[i].getHitbox().intersects(p.getHitbox()) && p.isActive() && shurikens[i].active) {
                    p.newState(DEAD);
                    if(shurikens[i].getHitbox().x < p.getHitbox().x) {
                        p.setHitFromLeft(true);
                        p.setHitFromRight(false);
                    } else {
                        p.setHitFromLeft(false);
                        p.setHitFromRight(true);
                    }
                }
            }
        }
    }

    private void loadEnemyImager() {
        piggyArr = new BufferedImage[3][11];
        piggyBloodArr = new BufferedImage[3][16];
        BufferedImage temp = LoadSave.GetSpriteAtlas((LoadSave.PIGGY_SPRITE));
        BufferedImage blood1 = LoadSave.GetSpriteAtlas(LoadSave.BLOOD_1);
        BufferedImage blood2 = LoadSave.GetSpriteAtlas(LoadSave.BLOOD_2);
        BufferedImage blood3 = LoadSave.GetSpriteAtlas(LoadSave.BLOOD_3);

        for(int j = 0 ; j < 2; j++) // de schimbat
            for(int i = 0 ; i < piggyArr[j].length; i++){
                piggyArr[j][i] = temp.getSubimage(i * PIGGY_WIDTH_DEFAULT, j * PIGGY_HEIGHT_DEFAULT, PIGGY_WIDTH_DEFAULT,PIGGY_HEIGHT_DEFAULT);
            }

        for(int j = 0 ; j < 3 ; j ++)
            for(int i = 0 ; i < 3 ; i++) {
                piggyBloodArr[0][i] = blood1.getSubimage(i* 128, j * 128, 128,128);
                piggyBloodArr[1][i] = blood2.getSubimage(i* 128, j * 128, 128,128);
            }

        for(int j = 0 ; j < 4 ; j ++)
            for(int i = 0 ; i < 4 ; i++) {
                piggyBloodArr[2][i] = blood3.getSubimage(i* 128, j * 128, 128, 128);
            }

    }

    public void resetAllEnemies() {
        rotation = 0;
        for(Piggy p : piggies) {
            p.resetEnemy();
        }
    }
}
