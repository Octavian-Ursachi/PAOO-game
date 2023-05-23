package utils;

import levels.LevelManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {


    //PLAYER ATLAS
    public static int nrStartPigs;
    public static final String PLAYER_RUNNING_ATLAS = "Run (32x32).png";
    public static final String PLAYER_RUNNING_LEFT_ATLAS = "Run left(32x32).png";
    public static final String PLAYER_IDLE_ATLAS = "Idle (32x32).png";

    public static final String PLAYER_JUMP_ATLAS = "Jump (32x32).png";
    public static final String LEVEL_ATLAS = "Terrain (16x16).png";
    public static final String LEVEL_ONE_DATA = "lvls/2.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_GREEN = "Green.png";
    public static final String PIGGY_SPRITE = "PigSprite.png";
    public static final String SHURIKEN_SPRITE = "myShuriken.png";
    public static final String SHURIKEN_EMPTY_SLOT = "UsedShuriken.png";
    public static final String SHURIKEN_FULL_SLOT = "ReadyShuriken.png";
    public static final String BLOOD_1 = "bloodAnim1.png";
    public static final String BLOOD_2 = "bloodAnim2.png";
    public static final String BLOOD_3 = "bloodAnim3.png";


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

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for(int i = 0 ; i < filesSorted.length; i++) {
            for(int j = 0 ; j < files.length ; j++) {
                if(files[j].getName().equals(""+(i+1)+".png"))
                    filesSorted[i] = files[j];
            }
        }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for(int i = 0 ; i < imgs.length ; i ++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imgs;
    }

    public static int[] getPlayerCoord(LevelManager levelManager) {
        int[] coord = new int[2];
        BufferedImage img = GetAllLevels()[levelManager.getLvlIndex()];
        for(int j = 0; j < img.getHeight() ; j++ )
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i,j));
                if(color.getBlue() == 255 && color.getGreen() == 255 && color.getRed() == 255) {
                    coord[0] = i * 40;
                    coord[1] = j * 40;
                }
            }
        return coord;
    }

}
