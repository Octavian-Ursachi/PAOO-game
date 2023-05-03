package entities;

import gameStates.Playing;
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
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImager();
        addEnemies();
    }

    private void addEnemies() {
        piggies = LoadSave.GetPigs();
    }

    public void update(){
        for(Piggy p : piggies) {
            if (p.isActive())
                p.update();
        }

    }

    public void draw(Graphics g,int yLvlOffset) {
        drawPigs(g,yLvlOffset);
    }

    private void drawPigs(Graphics g,int yLvlOffset) {
        for(Piggy p : piggies)
            if(p.isActive())
                if(p.x > playing.getPlayer().hitbox.x) {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x, (int) p.getHitbox().y - yLvlOffset, PIGGY_WIDTH, PIGGY_HEIGHT, null);
                } else {
                    g.drawImage(piggyArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x + PIGGY_WIDTH, (int) p.getHitbox().y - yLvlOffset, -PIGGY_WIDTH, PIGGY_HEIGHT, null);

                }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for(Piggy p : piggies)
            if(attackBox.intersects(p.getHitbox()) && p.isActive()) {
                p.newState(DEAD);
                p.setPiggyNR(p.getPiggyNR()-1);
                if(p.getPiggyNR() == 0)
                    playing.setGameOver(true);
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
        for(Piggy p : piggies)
            p.resetEnemy();
    }
}
