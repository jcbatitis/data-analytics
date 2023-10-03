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
        List<Post> postList = new ArrayList<Post>();
        String query = "SELECT * FROM Posts";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date = resultSet.getString("date_time");
                    String addedBy = resultSet.getString("user_id");

                    postList.add(new Post(id, content, author, likes, shares, date, addedBy));
                }

                return postList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return postList;
        }
    }

    @Override
    public Post get(String postId) {
        String query = "SELECT * FROM Posts " +
                "WHERE id LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + postId + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date = resultSet.getString("date_time");
                    String addedBy = resultSet.getString("user_id");

                    return new Post(id, content, author, likes, shares, date, addedBy);
                }

                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(Post post) {
    }

    @Override
    public void update(Post post) {
    }

    @Override
    public void delete(Post post) {
        String query = "DELETE FROM Posts WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, post.getId());

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("Deletion from Posts table executed successfully");
                System.out.println(result + " row(s) affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Post> getPostsBySearchTerm(String searchTerm) {
        List<Post> postList = new ArrayList<Post>();
        String query = "SELECT * FROM Posts " +
                "WHERE id LIKE ? " +
                "OR content LIKE ? " +
                "OR author LIKE ? " +
                "OR likes LIKE ? " +
                "OR shares LIKE ? " +
                "OR date_time LIKE ? " +
                "OR user_id LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= 7; i++) {
                statement.setString(i, "%" + searchTerm + "%");
            }

            // statement.setString(1, "%" + searchTerm + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date = resultSet.getString("date_time");
                    String addedBy = resultSet.getString("user_id");

                    postList.add(new Post(id, content, author, likes, shares, date, addedBy));
                }

                return postList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return postList;
        }
    }

    public List<Post> getPostsById(String postId) {
        List<Post> postList = new ArrayList<Post>();
        String query = "SELECT * FROM Posts " +
                "WHERE id LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + postId + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date = resultSet.getString("date_time");
                    String addedBy = resultSet.getString("user_id");

                    postList.add(new Post(id, content, author, likes, shares, date, addedBy));
                }

                return postList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return postList;
        }
    }
}
