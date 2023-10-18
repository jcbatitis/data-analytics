package sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {
    public static void main(String[] args) {

    }

    private static void createPostTable() {
        final String TABLE_NAME = "Posts";

        try (Connection con = DatabaseManager.getConnection();
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

    private static void createUserTable() {
        final String TABLE_NAME = "Users";

        try (Connection con = DatabaseManager.getConnection();
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "(user_id TEXT NOT NULL,"
                    + "first_name TEXT NOT NULL,"
                    + "last_name TEXT NOT NULL,"
                    + "username TEXT NOT NULL,"
                    + "password TEXT NOT NULL,"
                    + "is_vip INT NOT NULL,"
                    + "PRIMARY KEY (username))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
