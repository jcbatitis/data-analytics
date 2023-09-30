package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;
import utils.DatabaseUtil;

public class UserDao implements Dao<User> {
    private Connection connection;

    public UserDao() {
        connection = DatabaseUtil.getConnection();
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM Users";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("user_id");
                    String first_name = resultSet.getString("first_name");
                    String last_name = resultSet.getString("last_name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Boolean is_vip = resultSet.getBoolean("is_vip");

                    userList.add(new User(id, first_name, last_name, username, password, is_vip));
                }

                return userList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return userList;
        }
    }

    @Override
    public User get(String userId) {
        String query = "SELECT * FROM Users WHERE userId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String id = resultSet.getString("user_id");
                    String first_name = resultSet.getString("first_name");
                    String last_name = resultSet.getString("last_name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Boolean is_vip = resultSet.getBoolean("is_vip");
                    return new User(id, first_name, last_name, username, password, is_vip);
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(User user) {
        String query = "INSERT INTO Users" +
                " VALUES (?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setInt(6, 1);

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("Insert into table executed successfully");
                System.out.println(result + " row(s) affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public boolean checkUserIfExisting(String username) {
        String query = "SELECT * FROM Users where username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public User getUserByUsername(String selectedUsername) {
        String query = "SELECT * FROM Users WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, selectedUsername);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String id = resultSet.getString("user_id");
                    String first_name = resultSet.getString("first_name");
                    String last_name = resultSet.getString("last_name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Boolean is_vip = resultSet.getBoolean("is_vip");
                    return new User(id, first_name, last_name, username, password, is_vip);
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
