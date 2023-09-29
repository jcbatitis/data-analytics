package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePostsTable {
    public static void main(String[] args) {
        final String TABLE_NAME = "Posts";

        try (Connection con = DatabaseConnection.getConnection();
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "(id INT NOT NULL,"
                    + "content TEXT NOT NULL,"
                    + "author TEXT NOT NULL,"
                    + "likes INT,"
                    + "shares INT,"
                    + "date TEXT NOT NUll,"
                    + "PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
