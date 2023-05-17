package entities;

import gameStates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import static utils.Constants.EnemyConstant.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] piggyArr;
    private ArrayList<Piggy> piggies = new ArrayList<>();
    private boolean allEnemyDead = false;
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImager();
        addEnemies();
    }

    private void addEnemies() {
        piggies = LoadSave.GetPigs();
    }

    public void update(){
        allEnemyDead = true;
        for(Piggy p : piggies) {
            if (p.isActive()) {
                p.update();
                allEnemyDead = false;
            }
            else {
                p.hitbox.x = 3000;
                p.hitbox.y = 3000;
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
            if(p.isActive())
                if(p.x > playing.getPlayer().hitbox.x) {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x - PIGGY_DRAWOFFSET_X, (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, PIGGY_WIDTH, PIGGY_HEIGHT, null);
                } else {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) (p.getHitbox().x - PIGGY_DRAWOFFSET_X + p.width + 20), (int) p.getHitbox().y - PIGGY_DRAWOFFSET_Y - yLvlOffset, -PIGGY_WIDTH, PIGGY_HEIGHT, null); // la x trebuie sa-mi dau seama ce sa fac in loc sa adaug + 20

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
        BufferedImage temp = LoadSave.GetSpriteAtlas((LoadSave.PIGGY_SPRITE));
        for(int j = 0 ; j < 2; j++) // de schimbat
            for(int i = 0 ; i < piggyArr[j].length; i++){
                piggyArr[j][i] = temp.getSubimage(i * PIGGY_WIDTH_DEFAULT, j * PIGGY_HEIGHT_DEFAULT, PIGGY_WIDTH_DEFAULT,PIGGY_HEIGHT_DEFAULT);
            }

    }

    public void resetAllEnemies() {
        for(Piggy p : piggies) {
            p.resetEnemy();
        }



    }

}
