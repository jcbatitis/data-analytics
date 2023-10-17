package _sqlite;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateTableDatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:data_analytics.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
