package levels;

import entities.Piggy;
import main.Game;
import objects.Spikes;
import utils.HelpMethods;

import java.awt.image.BufferedImage;
import java.nio.BufferOverflowException;
import java.util.ArrayList;

public class Level {

    public float bestTime;
    public float secondStar, thirdStar;
    private BufferedImage backGround;
    private BufferedImage img;
    private int[][][] lvlData;
    private ArrayList<Piggy> pigs;
    private ArrayList<Spikes> spikes;
    private int lvlTilesHeight;
    private int maxTilesOffset;
    private int maxLvlOffsetY;


    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        createSpikes();
        calcLvlOffsets();
        

    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void calcLvlOffsets() {
        lvlTilesHeight = img.getHeight();
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
    public BufferedImage getLevelBackground() {return backGround;}
    public void setLevelBackground(BufferedImage backGround) {
        this.backGround = backGround;
    }
    public int getLvlOffset() {
        return maxLvlOffsetY;
    }
    public ArrayList<Piggy> getPigs() {
        return pigs;
    }
    public ArrayList<Spikes> getSpikes() {return spikes;}

}
