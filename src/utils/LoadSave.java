package utils;

import entities.Piggy;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entities.Piggy;
import static utils.Constants.EnemyConstant.PIGGY;

public class LoadSave {


    //PLAYER ATLAS
    public static final String PLAYER_RUNNING_ATLAS = "Run (32x32).png";
    public static final String PLAYER_RUNNING_LEFT_ATLAS = "Run left(32x32).png";
    public static final String PLAYER_IDLE_ATLAS = "Idle (32x32).png";

    public static final String PLAYER_JUMP_ATLAS = "Jump (32x32).png";

    public static final String LEVEL_ATLAS = "Terrain (16x16).png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_GREEN = "Green.png";
    public static final String PIGGY_SPRITE = "PigSprite.png";



    public static BufferedImage GetSpriteAtlas(String filename){
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            image = ImageIO.read(is);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return image;
    }

    public static ArrayList<Piggy> GetPigs() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Piggy> list = new ArrayList<>();
        for(int j = 0 ; j < img.getHeight() ; j++)
            for(int i = 0; i <img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i,j));
                int entityType = color.getGreen();
                if(entityType == PIGGY)
                    list.add(new Piggy(i*Game.TILES_SIZE,j*Game.TILES_SIZE));
            }
        return list;
    }

    public static int[][][] GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][][] lvlData = new int[img.getWidth()][img.getHeight()][2];


        for(int j = 0 ; j < img.getHeight() ; j++)
            for(int i = 0; i <img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i,j));
                int tileType = color.getRed();
                int rotation = color.getBlue();
                if(tileType >= 242)
                    tileType = 0;
                if(rotation >= 36)
                    rotation = 0;
                lvlData[j][i][0] = tileType;
                lvlData[j][i][1] = rotation;
            }
        return lvlData;
    }

}
