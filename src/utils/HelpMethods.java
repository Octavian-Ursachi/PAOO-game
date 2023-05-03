package utils;

import main.Game;
import org.ietf.jgss.GSSManager;

import java.awt.geom.Rectangle2D;

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
        if(value != 5)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {

        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        int nextTile = (int)((hitbox.x +Game.TILES_SIZE) / Game.TILES_SIZE);
        System.out.println(currentTile);
        if( xSpeed > 0) {
            //Right
            int nextXPos = nextTile * Game.TILES_SIZE;
            System.out.println(hitbox.width + ":" + Game.TILES_SIZE);
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




}
