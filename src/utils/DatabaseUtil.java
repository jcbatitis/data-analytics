package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:data_analytics.db";
    
    private static Connection connection;
    private static DatabaseUtil databaseObject;

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static DatabaseUtil getInstance() {
      if(databaseObject == null) {
        databaseObject = new DatabaseUtil();
     }

      return databaseObject;

    }
}
