package levels;

import entities.Player;
import gameStates.GameStates;
import gameStates.Playing;
import main.Game;
import org.w3c.dom.Entity;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite, backgroundSprite;
    private static ArrayList<Level> levels;
    private static int lvlIndex = 0;
    private static int drawOffset = 0;
    private static int offsetTick = 0;
    private boolean levelChanged = false;

    public LevelManager(Game game) {
        this.game = game;
        importBackgroundSprites();
        //levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();

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
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));
        }

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
        levelSprite = new BufferedImage[242];
        for (int j = 0; j < 11; j++)
            for (int i = 0; i < 22; i++) {
                int index = j * 22 + i;
                levelSprite[index] = img.getSubimage(i * 16, j * 16, 16, 16);
            }
    }

    public void draw(Graphics g, int lvlOffset) {
        offsetTick++;
        if (offsetTick > 5) {
            offsetTick = 0;
            drawOffset++;
        }
        if (drawOffset >= 128)
            drawOffset = 0;

        for (int j = 0; j < levels.get(lvlIndex).getLevelData().length; j++)
            for (int i = 0; i <= Game.TILES_IN_WIDTH; i++) {
                g.drawImage(backgroundSprite[5], j * 128, i * 128 + drawOffset - 128, 128, 128, null);
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

}

