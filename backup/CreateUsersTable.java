package sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUsersTable {
    public static void main(String[] args) {
        final String TABLE_NAME = "Users";

        try (Connection con = CreateTableDatabaseConnection.getConnection();
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
