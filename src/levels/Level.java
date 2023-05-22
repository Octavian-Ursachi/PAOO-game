package levels;

import entities.Piggy;
import main.Game;
import utils.HelpMethods;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {

    private BufferedImage img;
    private int[][][] lvlData;
    private ArrayList<Piggy> pigs;
    private int lvlTilesHeight;
    private int maxTilesOffset;
    private int maxLvlOffsetY;


    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        

    }

    private void calcLvlOffsets() {
        lvlTilesHeight = img.getWidth();
        maxTilesOffset = lvlTilesHeight - Game.TILES_IN_HEIGHT;
        maxLvlOffsetY = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        pigs = HelpMethods.GetPigs(img);
    }

    private void createLevelData() {
        lvlData = HelpMethods.GetLevelData(img);
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
    public int getLvlOffset() {
        return maxLvlOffsetY;
    }
    public ArrayList<Piggy> getPigs() {
        return pigs;

    }

}
