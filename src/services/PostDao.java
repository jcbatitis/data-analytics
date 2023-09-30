package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Post;
import utils.DatabaseUtil;

public class PostDao implements Dao<Post> {
    private Connection connection;

    public PostDao() {
        connection = DatabaseUtil.getConnection();
    }

    @Override
    public List<Post> getAll() {
        List<Post> postList = new ArrayList<>();
        String query = "SELECT * FROM Posts";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

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

    @Override
    public Post get(String id) {
        return null;
    }

    @Override
    public void create(Post user) {
    }

    @Override
    public void update(Post user, String[] params) {
    }

    @Override
    public void delete(Post user) {
    }
}
