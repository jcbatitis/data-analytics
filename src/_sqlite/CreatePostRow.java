package _sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePostRow {
    public static void main(String[] args) {
        final String TABLE_NAME = "Posts";

        try (Connection con = CreateTableDatabaseConnection.getConnection();
                Statement stmt = con.createStatement();) {
            String query = "INSERT INTO " + TABLE_NAME +
                    " VALUES ('XE3a', 'Cute', 'Ruffles', 1000000, 1000000, '30/09/1995 23:59')";

            int result = stmt.executeUpdate(query);

            if (result == 1) {
                System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
                System.out.println(result + " row(s) affected");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
