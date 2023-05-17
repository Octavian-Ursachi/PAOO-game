package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x,y;
    protected int width, height;

    protected Rectangle2D.Float hitbox;

    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float((int) x ,(int) y ,width,height);
    }

//    public void updateHitbox() {
//        hitbox.x = (int) this.x;
//        hitbox.y = (int) this.y;
//    }

    public void drawHitbox(Graphics g,int yLvlOffset){
        g.setColor(Color.BLACK);
        g.drawRect((int) hitbox.x,(int) hitbox.y - yLvlOffset, (int) hitbox.width,(int) hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }


}
