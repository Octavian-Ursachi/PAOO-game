package objects;

import main.Game;
import utils.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameObject {

    protected int x,y,objType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation,active = true;
    protected int aniTick,aniIndex;
    protected  int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }
    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick >= Constants.GameObjectsConstants.ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= Constants.GameObjectsConstants.GetSpriteAmount(objType)) {
                aniIndex = 0;
            }
        }
    }
    public void reset() {
        aniIndex = 0 ;
        aniTick = 0 ;
        active = true;

        //TODO
        doAnimation = true;
    }

    protected  void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x,y+height*Game.SCALE,(int)(width*Game.SCALE),(int)(height*Game.SCALE));
    }

    public void drawHitbox(Graphics g, int yLvlOffset){
        g.setColor(Color.BLACK);
        g.drawRect((int) hitbox.x,(int) hitbox.y - yLvlOffset, (int) hitbox.width,(int) hitbox.height);
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public void setxDrawOffset(int xDrawOffset) {
        this.xDrawOffset = xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public void setyDrawOffset(int yDrawOffset) {
        this.yDrawOffset = yDrawOffset;
    }

    public int getAniIndex() {
        return aniIndex;
    }
}
