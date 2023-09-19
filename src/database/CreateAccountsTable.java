package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateAccountsTable {
    public static void main(String[] args) {
        final String TABLE_NAME = "Accounts";

        try (Connection con = DatabaseConnection.getConnection();
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "(user_id INT NOT NULL,"
                    + "role INT NOT NULL,"
                    + "username VARCHAR(20) NOT NULL,"
                    + "password VARCHAR(20) NOT NULL,"
                    + "PRIMARY KEY (user_id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
