package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {


    //PLAYER ATLAS
    public static final String PLAYER_RUNNING_ATLAS = "Run (32x32).png";
    public static final String PLAYER_RUNNING_LEFT_ATLAS = "Run left(32x32).png";
    public static final String PLAYER_IDLE_ATLAS = "Idle (32x32).png";

    public static final String PLAYER_JUMP_ATLAS = "Jump (32x32).png";

    public static final String LEVEL_ATLAS = "Terrain (16x16).png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";



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

    public static int[][][] GetLevelData() {
        int[][][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH][2];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

        for(int j = 0 ; j < img.getHeight() ; j++)
            for(int i = 0; i <img.getWidth(); i++)
            {
                System.out.println(img.getWidth());
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
