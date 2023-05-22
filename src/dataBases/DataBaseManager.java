package dataBases;
import java.sql.*;

public class DataBaseManager {




    public DataBaseManager() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE Time " +
                        "Best Time REAL";
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Table created succesfully");
    }


}
