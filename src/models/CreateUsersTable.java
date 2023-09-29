package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUsersTable {
    public static void main(String[] args) {
        final String TABLE_NAME = "Users";

        try (Connection con = DatabaseConnection.getConnection();
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "(id INT NOT NULL,"
                    + "first_name VARCHAR(20) NOT NULL,"
                    + "last_name VARCHAR(20) NOT NULL,"
                    + "username VARCHAR(20) NOT NULL,"
                    + "password VARCHAR(20) NOT NULL,"
                    + "is_vip INT NOT NULL,"
                    + "PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
