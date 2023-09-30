// https://www.baeldung.com/java-dao-pattern
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import utils.DatabaseUtil;

public class LoginDao implements Dao<User> {
    private Connection connection;

    public LoginDao() {
        connection = DatabaseUtil.getConnection();
    }

    @Override
    public User get(String[] params) {
        return null;
    }

    @Override
    public void create(User user) {
    }

    @Override
    public void update(User user, String[] params) {
    }

    @Override
    public void delete(User user) {
    }

    public boolean checkUserIfValid(String username, String password) {
        String query = "SELECT * FROM Users where username = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
