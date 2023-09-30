package _sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Post;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:data_analytics.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public boolean checkUserIfValid(String username, String password) {
        String query = "SELECT * FROM Users where username = ? AND password = ?";

        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

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

    public List<Post> getPosts() {
        List<Post> postList = new ArrayList<>();
        String query = "SELECT * FROM Posts";

        try (Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date = resultSet.getString("date");

                    postList.add(new Post(id, content, author, likes, shares, date));
                }

                return postList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return postList;
        }

    }
}
