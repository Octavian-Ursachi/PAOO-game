package dataBases;
import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class DataBaseManager {

    private static final String DB_URL = "jdbc:sqlite:src/dataBases/DataBase.db";
    private BufferedImage[] levels;
    private float[] bestTime;

    public DataBaseManager() {
        this.levels = readLevels();
        this.bestTime = readBestTime();
    }

   public BufferedImage[] readLevels() {
       try {

           BufferedImage[] imgs = new BufferedImage[6];

           Class.forName("org.sqlite.JDBC");
           Connection conn = DriverManager.getConnection(DB_URL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT Level1, Level2, Level3, Level4, Level5, Level6 FROM Levels");

           for(int i = 0 ; i < 6 ; i++) {
               String columnName = "Level" + (i+1);
               InputStream inputStream = rs.getBinaryStream(columnName);
               imgs[i] = ImageIO.read(inputStream);
           }

           rs.close();
           stmt.close();
           conn.close();

           return imgs;
       }catch(SQLException | ClassNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       return null;
   }

   public float[] readBestTime() {
       try {

           float[] bestTime = new float[6];

           Class.forName("org.sqlite.JDBC");
           Connection conn = DriverManager.getConnection(DB_URL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT Level1, Level2, Level3, Level4, Level5, Level6 FROM Levels_Best_Time");

           for(int i = 0 ; i < 6 ; i++) {
               String columnName = "Level" + (i+1);
               bestTime[i] = rs.getFloat(columnName);
               //System.out.println(bestTime[i]);
           }

           rs.close();
           stmt.close();
           conn.close();

           return bestTime;
       }catch(SQLException | ClassNotFoundException e) {
           e.printStackTrace();
       }
       return null;
   }

    public void writeBestTime(float bestTime, int lvlIndex) {
        try {

            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Level1, Level2, Level3, Level4, Level5, Level6 FROM Levels_Best_Time");


            String columnName = "Level" + (lvlIndex+1);
            String sql = "UPDATE Levels_Best_Time SET " + columnName + " = " + bestTime;
            stmt.executeUpdate(sql);

            rs.close();
            stmt.close();
            conn.close();

        }catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] getLevels() {
        return  levels;
    }

    public float[] getBestTime() {
        return bestTime;
    }


}
