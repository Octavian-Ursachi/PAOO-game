package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x,y;
    protected int width, height;

    protected Rectangle2D.Float hitbox;

    public Entity(float x, float y, int width, int height) throws EntityCoordsLessThanZeroException {
        try {
            this.x = x;
            this.y = y;
            validateCoords();
            this.width = width;
            this.height = height;
        } catch (EntityCoordsLessThanZeroException e) {
            e.printStackTrace();
        }
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

    private void validateCoords() throws EntityCoordsLessThanZeroException {
        if(this.x < 0 || this.y < 0) {
            throw new EntityCoordsLessThanZeroException("Entity coordinates can't be less than zero!!!");
        }
    }

}
