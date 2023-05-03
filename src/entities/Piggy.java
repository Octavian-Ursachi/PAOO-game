package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.EnemyConstant.*;
public class Piggy extends  Enemy{

    private static int piggyNR = 0;
    private int attackBoxOffsetX;
    public Piggy(float x, float y) {
        super(x, y, (int)(PIGGY_WIDTH),(int)(PIGGY_HEIGHT),PIGGY);
        initHitbox(x-60,y-50,(int)(22 * Game.SCALE),(int)(28 * Game.SCALE));
        piggyNR++;
    }

    public int getPiggyNR() {return piggyNR;}

    public void setPiggyNR(int nr) {
        piggyNR = nr;
    }

}
