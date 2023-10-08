package _sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePostsTable {
    public static void main(String[] args) {
        final String TABLE_NAME = "Posts";

        try (Connection con = CreateTableDatabaseConnection.getConnection();
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "(id TEXT NOT NULL,"
                    + "content TEXT NOT NULL,"
                    + "author TEXT NOT NULL,"
                    + "likes INT,"
                    + "shares INT,"
                    + "date_time TEXT NOT NUll,"
                    + "PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
