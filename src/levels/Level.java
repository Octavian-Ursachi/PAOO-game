package levels;

public class Level {

    private int[][][] lvlData;

    public Level(int[][][] lvlData){
        this.lvlData = lvlData;

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

}
