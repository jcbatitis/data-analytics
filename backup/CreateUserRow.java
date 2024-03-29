package sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUserRow {
    public static void main(String[] args) {
        final String TABLE_NAME = "Users";

        try (Connection con = CreateTableDatabaseConnection.getConnection();
                Statement stmt = con.createStatement();) {
            String query = "INSERT INTO " + TABLE_NAME +
                    " VALUES ('1', 'Ruff', 'Malt', 'ruff', 'ruff', 1)";

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
