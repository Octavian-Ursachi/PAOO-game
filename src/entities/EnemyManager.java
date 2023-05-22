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


import static utils.Constants.EnemyConstant.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] piggyArr;
    private BufferedImage[][] piggyBloodArr;
    private ArrayList<Piggy> piggies = new ArrayList<>();
    private boolean allEnemyDead = false;
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
                p.update();
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
        for(Piggy p : piggies)
            if(p.isActive()) {
                if (p.x > playing.getPlayer().hitbox.x) {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x - PIGGY_DRAWOFFSET_X, (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, PIGGY_WIDTH, PIGGY_HEIGHT, null);
                } else {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) (p.getHitbox().x - PIGGY_DRAWOFFSET_X + p.width + 20), (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, -PIGGY_WIDTH, PIGGY_HEIGHT, null); // la x trebuie sa-mi dau seama ce sa fac in loc sa adaug + 20

                }
            } else {
                if(!p.oneTimeDeadAnim) {
                    //g.drawImage(piggyBloodArr[randomNum][deathAniIndex],(int) p.getHitbox().x - PIGGY_DRAWOFFSET_X, (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, 128, 128, null);
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x - PIGGY_DRAWOFFSET_X, (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, PIGGY_WIDTH, PIGGY_HEIGHT, null);
                }
                if(p.getAniIndex() == 5)
                    p.oneTimeDeadAnim = true;
            }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for(Piggy p : piggies) {
            if (attackBox.intersects(p.getHitbox()) && p.isActive()) {
                p.newState(DEAD);
            }
        }
    }
    public void checkEnemyHitWithShuriken(Shuriken[] shurikens) {
        for(Piggy p : piggies) {
            for(int i = 0 ; i < 3 ; i++) {
                if (shurikens[i].getHitbox().intersects(p.getHitbox()) && p.isActive() && shurikens[i].active) {
                    p.newState(DEAD);
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
        for(Piggy p : piggies) {
            p.resetEnemy();
        }
    }
}
