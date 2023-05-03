package levels;

import java.awt.image.BufferedImage;
import java.nio.BufferOverflowException;

public class Level {

    private int[][][] lvlData;

    private BufferedImage background;

    public Level(int[][][] lvlData, BufferedImage background){
        this.lvlData = lvlData;
        this.background = background;

    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x][0];
    }

    public int getSpriteRotation(int x,int y){
        return lvlData[y][x][1];
    }

    public int[][][] getLevelData(){
        return lvlData;
    }

    public BufferedImage getBackground(){
        return background;
    }

}
