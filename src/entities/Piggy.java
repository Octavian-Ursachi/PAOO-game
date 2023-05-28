package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.EnemyConstant.*;
public class Piggy extends  Enemy{

    private int attackBoxOffsetX;
    public Piggy(float x, float y) throws EntityCoordsLessThanZeroException {
        super(x, y, (int)(PIGGY_WIDTH),(int)(PIGGY_HEIGHT),PIGGY,1);
        initHitbox(x,y-10,(int)(22 * Game.SCALE),(int)(28 * Game.SCALE));
    }

    public boolean isHitFromLeft() {
        return hitFromLeft;
    }

    public void setHitFromLeft(boolean hitFromLeft) {
        this.hitFromLeft = hitFromLeft;
    }

    public boolean isHitFromRight() {
        return hitFromRight;
    }

    public void setHitFromRight(boolean hitFromRight) {
        this.hitFromRight = hitFromRight;
    }

}
