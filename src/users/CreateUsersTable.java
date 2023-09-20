package users;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseConnection;

public class CreateUsersTable {
    public static void main(String[] args) {
        final String TABLE_NAME = "Users";

        try (Connection con = DatabaseConnection.getConnection();
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "(id INT NOT NULL,"
                    + "first_name VARCHAR(20) NOT NULL,"
                    + "last_name VARCHAR(20) NOT NULL,"
                    + "PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
