package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;

import models.User;
import utils.DatabaseUtil;

public class UserDao implements Dao<User> {
    private Connection connection;

    public UserDao() {
        DatabaseUtil db = DatabaseUtil.getInstance();
        connection = db.getConnection();
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
    public User get(String userId) throws EntityNotFoundException {
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
                } else {
                    throw new EntityNotFoundException(
                            String.format("[Error] Failed to get user as USER ID: %s does not exist", userId));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean create(User user)
            throws EntityAlreadyExistsException {

        for (User existingUser : getAll()) {
            String userId = user.getUserId();

            if (existingUser.getUserId().equals(userId)) {
                throw new EntityAlreadyExistsException(
                        String.format("[Error] Failed to insert user as USER ID: %s already exists", userId));
            }
        }

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

                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(User user)
            throws EntityNotFoundException {
        String query = "UPDATE Users SET " +
                "user_id = ?," +
                "first_name = ?," +
                "last_name = ?," +
                "username = ?," +
                "password = ?," +
                "is_vip = ?" +
                "WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setBoolean(6, user.isVIP());
            statement.setString(7, user.getUserId());

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("Update into table executed successfully");
                System.out.println(result + " row(s) affected");

                return true;
            } else {
                throw new EntityNotFoundException(
                        String.format("[Error] Failed to update user as USER ID: %s does not exist", user.getUserId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(User user) throws EntityNotFoundException {
        String query = "DELETE FROM Users WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUserId());

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("Deletion from table executed successfully");
                System.out.println(result + " row(s) affected");

                return true;
            } else {
                throw new EntityNotFoundException(
                        String.format("[Error] Failed to delete user as USER ID: %s does not exist", user.getUserId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User checkUserIfValid(String entereredUsername, String enteredPassword) throws EntityNotFoundException {
        String query = "SELECT * FROM Users where username = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, entereredUsername);
            statement.setString(2, enteredPassword);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("user_id");
                    String first_name = resultSet.getString("first_name");
                    String last_name = resultSet.getString("last_name");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Boolean is_vip = resultSet.getBoolean("is_vip");
                    return new User(id, first_name, last_name, username, password, is_vip);
                } else {
                    throw new EntityNotFoundException(
                            String.format("[Error] Username or password is incorrect"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    public Boolean updateRole(User user) throws EntityNotFoundException {
        String query = "UPDATE Users SET " +
                "is_vip = ?" +
                "WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, user.isVIP());
            statement.setString(2, user.getUserId());

            int result = statement.executeUpdate();

            if (result == 1) {
                return true;
            } else {
                throw new EntityNotFoundException(
                        String.format("[Error] Failed to update user as USER ID: %s does not exist", user.getUserId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
