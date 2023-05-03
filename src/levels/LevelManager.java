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

    public BufferedImage rotate(BufferedImage imagineAux,int unghi) {
        // Calculate the new size of the image based on the angle of rotaion
        unghi = unghi * 10;
        double radians = Math.toRadians(unghi);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(imagineAux.getWidth() * cos + imagineAux.getHeight() * sin);
        int newHeight = (int) Math.round(imagineAux.getWidth() * sin + imagineAux.getHeight() * cos);

        // Create a new image
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        // Calculate the "anchor" point around which the image will be rotated
        int x = (newWidth - imagineAux.getWidth()) / 2;
        int y = (newHeight - imagineAux.getHeight()) / 2;
        // Transform the origin point around the anchor point
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + ((float)imagineAux.getWidth() / 2), y + ((float)imagineAux.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        // Paint the originl image
        g2d.drawImage(imagineAux, 0, 0, null);
        g2d.dispose();
        return rotate;
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
