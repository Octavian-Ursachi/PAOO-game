package entities;

import main.Game;
import utils.HelpMethods;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.EnemyConstant.*;
import static utils.Constants.EnemyConstant.PIGGY_HEIGHT;
import static utils.Constants.ShurikenConstant.*;

public class Shuriken extends Entity{

    private int[][][] levelData;
    private Player player;
    private int dir = 0;

    private BufferedImage shurikenImg;
    public boolean used;
    public boolean active;

    public Shuriken(float x, float y,Player player,int levelData[][][]) {
        super(x, y, SHURIKEN_WIDTH,SHURIKEN_HEIGHT);
        this.levelData = levelData;
        this.player = player;
        this.used = false;
        this.active = false;
        this.dir = 1;
        loadShurikenImage();
        initHitbox(x,y,SHURIKEN_WIDTH,SHURIKEN_HEIGHT);
    }

    public void loadShurikenImage() {
        shurikenImg = LoadSave.GetSpriteAtlas(LoadSave.SHURIKEN_SPRITE);
    }
    public void draw(Graphics g, int yLvlOffset) {
        if(active)
            g.drawImage(shurikenImg,(int)this.hitbox.x,(int)this.hitbox.y - yLvlOffset, SHURIKEN_DRAW_WIDTH,SHURIKEN_DRAW_HEIGHT,null);

    }

    public void updatePos(int levelData[][][]) {
        if(active && used) {
            if(HelpMethods.canMoveHere(this.hitbox.x,this.hitbox.y,this.hitbox.width,this.hitbox.height,levelData))
                this.hitbox.x = this.hitbox.x + (SHURIKEN_SPEED * dir);
            else
                active = false;
        }
        else if(!active && !used) {
            this.hitbox.x = player.hitbox.x+SHURIKEN_POS_OFFSET;
            this.hitbox.y = player.hitbox.y+SHURIKEN_POS_OFFSET;
        }
    }
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getDir() {return dir;}




}
