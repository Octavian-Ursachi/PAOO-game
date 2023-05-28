package utils;

import entities.EntityCoordsLessThanZeroException;
import entities.Piggy;
import main.Game;
import objects.Spikes;
import org.ietf.jgss.GSSManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstant.PIGGY;

public class HelpMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int levelData[][][]){
        if(!isSolid(x,y,levelData)) //colt stanga sus
            if(!isSolid(x+width,y,levelData)) //colt dreapta jos
                if(!isSolid(x,y+height,levelData)) //colt stanga jos
                    if(!isSolid(x+width,y+height,levelData)) //colt dreapta sus
                        return true;
        return false;
    }

    public static boolean isSolid(float x,float y,int levelData[][][]){
        int maxHeight = levelData.length * Game.TILES_SIZE;
        if(x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if(y < 0 || y >= maxHeight)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = levelData[(int) yIndex][(int) xIndex][0];
        if(value != 5 && value!=255 && value!=242)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {

        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        int nextTile = (int)((hitbox.x +Game.TILES_SIZE) / Game.TILES_SIZE);
        if( xSpeed > 0) {
            //Right
            int nextXPos = nextTile * Game.TILES_SIZE;
            return  currentTile * Game.TILES_SIZE + 14*Game.SCALE ;
        }else {
            //Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetPlayerYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
        int nextTile = (int)((hitbox.y +Game.TILES_SIZE) / Game.TILES_SIZE);

        if(airSpeed > 0) {
            // Falling
            int nextYPos = nextTile * Game.TILES_SIZE;
            int tileYpos = currentTile * Game.TILES_SIZE;
            return currentTile * Game.TILES_SIZE+ 13*Game.SCALE;
        }else {
            // Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor (Rectangle2D.Float hitbox,int[][][] lvlData) {
        if(!isSolid(hitbox.x+hitbox.width, hitbox.y+hitbox.height+5*Game.SCALE, lvlData)) //colt dreapta jos
            if(!isSolid(hitbox.x, hitbox.y+hitbox.height+5*Game.SCALE, lvlData)) //colt stanga jos
                return false;
        return true;


        //if(!isSolid(x+width,y,levelData)) //colt dreapta jos
        //                if(!isSolid(x,y+height,levelData)) //colt stanga jos
        //
    }

    public static int[][][] GetLevelData(BufferedImage img) {
        int[][][] lvlData = new int[img.getHeight()][img.getWidth()][2];
        for(int j = 0 ; j < img.getHeight() ; j++)
            for(int i = 0; i < img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i,j));
                int tileType = color.getRed();
                int rotation = color.getBlue();
                if(tileType >= 244)
                    tileType = 5;
                if(rotation >= 36)
                    rotation = 0;
                lvlData[j][i][0] = tileType;
                lvlData[j][i][1] = rotation;
            }
        return lvlData;
    }

    public static ArrayList<Piggy> GetPigs(BufferedImage img) {
        ArrayList<Piggy> list = new ArrayList<>();
        for(int j = 0 ; j < img.getHeight() ; j++)
            for(int i = 0; i <img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i,j));
                int entityType = color.getGreen();
                if(entityType == PIGGY) {
                    try {
                        list.add(new Piggy(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                    } catch (EntityCoordsLessThanZeroException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        return list;
    }

    public static ArrayList<Spikes> GetSpikes(BufferedImage img) {
        ArrayList<Spikes> list = new ArrayList<>();
        for(int j = 0 ; j < img.getHeight() ; j++)
            for(int i = 0; i <img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i,j));
                int entityType = color.getRed();
                if(entityType == Constants.GameObjectsConstants.SPIKES) {
                    list.add(new Spikes(i * Game.TILES_SIZE, j * Game.TILES_SIZE,Constants.GameObjectsConstants.SPIKES));
                }
            }
        return list;
    }




}
