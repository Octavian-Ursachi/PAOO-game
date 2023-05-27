package levels;

import dataBases.DataBaseManager;
import entities.Player;
import gameStates.GameStates;
import gameStates.Playing;
import main.Game;
import org.w3c.dom.Entity;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;


public class LevelManager {

    private DataBaseManager dataBaseManager;
    private Game game;
    private BufferedImage[] levelSprite, backgroundSprite;
    private static ArrayList<Level> levels;
    private static int lvlIndex = 0;
    private static int drawOffset = 0;
    private static int offsetTick = 0;
    private boolean levelChanged = false;

    public LevelManager(Game game) {
        this.dataBaseManager = game.getDataBaseManager();
        this.game = game;
        importBackgroundSprites();
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();

    }

    public void loadBestTime(){
        float[] bestTime = dataBaseManager.readBestTime();
        for(int i = 0 ; i < 6 ; i++) {
            levels.get(i).bestTime = bestTime[i];
            //System.out.println(bestTime[i] + " jjj");
        }
    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("Nom ore leveles! Game completed!");
            GameStates.gameState = GameStates.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = dataBaseManager.readLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));

        }
        setAllLevelsBackGround();
        getStarData();
        loadBestTime();

    }

    private void setAllLevelsBackGround() {
        levels.get(0).setLevelBackground(backgroundSprite[0]);
        levels.get(1).setLevelBackground(backgroundSprite[1]);
        levels.get(2).setLevelBackground(backgroundSprite[4]);
        levels.get(3).setLevelBackground(backgroundSprite[3]);
        levels.get(4).setLevelBackground(backgroundSprite[5]);
        levels.get(5).setLevelBackground(backgroundSprite[6]);

    }

    public void getStarData(){
        levels.get(0).secondStar = Constants.Level1.SECOND_STAR;
        levels.get(0).thirdStar = Constants.Level1.THIRD_STAR;
        levels.get(1).secondStar = Constants.Level2.SECOND_STAR;
        levels.get(1).thirdStar = Constants.Level2.THIRD_STAR;
        levels.get(2).secondStar = Constants.Level3.SECOND_STAR;
        levels.get(2).thirdStar = Constants.Level3.THIRD_STAR;
        levels.get(3).secondStar = Constants.Level4.SECOND_STAR;
        levels.get(3).thirdStar = Constants.Level4.THIRD_STAR;
        levels.get(4).secondStar = Constants.Level5.SECOND_STAR;
        levels.get(4).thirdStar = Constants.Level5.THIRD_STAR;
        levels.get(5).secondStar = Constants.Level6.SECOND_STAR;
        levels.get(5).thirdStar = Constants.Level6.THIRD_STAR;
    }

    public void importBackgroundSprites() {
        backgroundSprite = new BufferedImage[7];
        backgroundSprite[0] = LoadSave.GetSpriteAtlas("Blue.png");
        backgroundSprite[1] = LoadSave.GetSpriteAtlas("Brown.png");
        backgroundSprite[2] = LoadSave.GetSpriteAtlas("Gray.png");
        backgroundSprite[3] = LoadSave.GetSpriteAtlas("Green.png");
        backgroundSprite[4] = LoadSave.GetSpriteAtlas("Pink.png");
        backgroundSprite[5] = LoadSave.GetSpriteAtlas("Purple.png");
        backgroundSprite[6] = LoadSave.GetSpriteAtlas("Yellow.png");

    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[243];
        for (int j = 0; j < 11; j++)
            for (int i = 0; i < 22; i++) {
                int index = j * 22 + i;
                levelSprite[index] = img.getSubimage(i * 16, j * 16, 16, 16);
            }
    }

//    public void brightenBackgroundPulse() {
//        BufferedImage image = levels.get(lvlIndex).getLevelBackground();
//        for (int y = 0; y < image.getHeight(); y++) {
//            for (int x = 0; x < image.getWidth(); x++) {
//                image.setRGB(x,y,1);
//            }
//        }
//    }
    public void draw(Graphics g, int lvlOffset) {
//        brightenBackgroundPulse();
        offsetTick++;
        if (offsetTick > 5) {
            offsetTick = 0;
            drawOffset++;
        }
        if (drawOffset >= 128)
            drawOffset = 0;

        for (int j = 0; j < levels.get(lvlIndex).getLevelData().length; j++)
            for (int i = 0; i <= Game.TILES_IN_WIDTH; i++) {
                g.drawImage(levels.get(lvlIndex).getLevelBackground(), j * 128, i * 128 + drawOffset - 128, 128, 128, null);
            }
        for (int j = 0; j < levels.get(lvlIndex).getLevelData().length; j++)
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * Game.TILES_SIZE, j * Game.TILES_SIZE - lvlOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        //g.drawImage(levelSprite[17],0,0,null);
    }
    public void update() {
        if(levelChanged) {
            game.getPlaying().getPlayer().setPlayerPositionAfterLvlChanges(this);
            levelChanged = false;
        }
    }

    /*
    public Rectangle getTileBounds() {
        return new Rectangle(,,Game.TILES_SIZE,Game.TILES_SIZE);
    }
    */
    public static Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLvlIndex() {return lvlIndex;}
    public void setLevelChanged(boolean levelChanged) {
        this.levelChanged = levelChanged;
    }

    public ArrayList<Level> getLevels(){return levels;}

    public DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

}

