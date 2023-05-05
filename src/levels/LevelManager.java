package levels;

import main.Game;
import org.w3c.dom.Entity;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class LevelManager{

    private Game game;
    private BufferedImage[] levelSprite,backgroundSprite;
    private static Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importBackgroundSprites();
        //levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData(),backgroundSprite[2]);

    }

    public void importBackgroundSprites(){
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
        for(int j = 0; j < 11 ; j++)
            for(int i = 0 ; i < 22 ; i++)
            {
                int index = j*22 + i;
                levelSprite[index] = img.getSubimage(i*16,j*16,16,16);
            }
    }

    public void draw(Graphics g, int lvlOffset) {
        for(int j = 0 ; j < levelOne.getLevelData().length ; j++)
            for(int i = 0 ; i < Game.TILES_IN_WIDTH; i++)
            {
                int index = levelOne.getSpriteIndex(i,j);
                g.drawImage(levelOne.getBackground(),i*Game.TILES_SIZE,j*Game.TILES_SIZE,Game.TILES_SIZE,Game.TILES_SIZE,null);
                g.drawImage(levelSprite[index],i*Game.TILES_SIZE,j*Game.TILES_SIZE - lvlOffset,Game.TILES_SIZE,Game.TILES_SIZE,null);

            }
        //g.drawImage(levelSprite[17],0,0,null);
    }


    public void update() {

    }
    /*
    public Rectangle getTileBounds() {
        return new Rectangle(,,Game.TILES_SIZE,Game.TILES_SIZE);
    }
    */
    public static Level getCurrentLevel(){
        return levelOne;
    }
}
