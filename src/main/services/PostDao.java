package main.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.exceptions.CustomDateTimeParseException;
import main.exceptions.EntityAlreadyExistsException;
import main.exceptions.EntityNotFoundException;
import main.models.Post;
import main.utils.DatabaseUtil;
import main.utils.GlobalUtil;

public class PostDao implements Dao<Post> {
    private Connection connection;

    public PostDao() {
        DatabaseUtil db = DatabaseUtil.getInstance();
        connection = db.getConnection();
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

                    try {
                        postList.add(new Post(id, content, author, likes, shares, date));
                    } catch (CustomDateTimeParseException e) {
                        System.out.println(
                                String.format("[Error] Error parsing datetime of POST with the ID of %s", id));
                    }
                }

                return postList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return postList;
        }
    }

    @Override
    public Post get(String postId) throws EntityNotFoundException, CustomDateTimeParseException {
        String query = "SELECT * FROM Posts " +
                "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, postId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date = resultSet.getString("date_time");

                    return new Post(id, content, author, likes, shares, date);
                } else {
                    throw new EntityNotFoundException(
                            String.format("[Error] Failed to get post as POST ID: %s does not exist", postId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean create(Post post)
            throws EntityAlreadyExistsException, CustomDateTimeParseException {

        try {
            GlobalUtil.validateDateControl(post.getDateTime());
        } catch (CustomDateTimeParseException e) {
            return false;
        }

        for (Post existingPost : getAll()) {
            String postId = post.getId();

            if (existingPost.getId().equals(postId)) {
                throw new EntityAlreadyExistsException(
                        String.format("[Error] Failed to insert post as POST ID: %s already exists", postId));
            }
        }

        String query = "INSERT INTO Posts" +
                " VALUES (?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, post.getId());
            statement.setString(2, post.getContent());
            statement.setString(3, post.getAuthor());
            statement.setInt(4, post.getLikes());
            statement.setInt(5, post.getShares());
            statement.setString(6, post.getDateTime());

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("[LOG] Insert into table executed successfully");
                System.out.println("[LOG] " + result + " row(s) affected");

                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(Post post) throws EntityNotFoundException {
        String query = "UPDATE Posts SET " +
                "content = ?," +
                "author = ?," +
                "likes = ?," +
                "shares = ?," +
                "date_time = ?" +
                "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, post.getContent());
            statement.setString(2, post.getAuthor());

            statement.setInt(3, post.getLikes());
            statement.setInt(4, post.getShares());
            statement.setString(5, post.getDateTime());
            statement.setString(6, post.getId());

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("[LOG] Update into table executed successfully");
                System.out.println("[LOG] " + result + " row(s) affected");

                return true;
            } else {
                throw new EntityNotFoundException(
                        String.format("[Error] Failed to update postser as POST ID: %s does not exist",
                                post.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(String id) throws EntityNotFoundException {
        String query = "DELETE FROM Posts WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("[LOG] Deletion from Posts table executed successfully");
                System.out.println("[LOG] "+result + " row(s) affected");

                return true;
            } else {
                throw new EntityNotFoundException(
                        String.format("[Error] Failed to delete post as POST ID: %s does not exist", id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Post> getPostsBySearchTerm(String searchTerm) throws CustomDateTimeParseException {
        List<Post> postList = new ArrayList<Post>();
        String query = "SELECT * FROM Posts " +
                "WHERE id LIKE ? " +
                "OR content LIKE ? " +
                "OR author LIKE ? " +
                "OR likes LIKE ? " +
                "OR shares LIKE ? " +
                "OR date_time LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= 6; i++) {
                statement.setString(i, "%" + searchTerm + "%");
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date = resultSet.getString("date_time");

                    try {
                        postList.add(new Post(id, content, author, likes, shares, date));
                    } catch (CustomDateTimeParseException e) {
                        System.out
                                .println(String.format(
                                        "[Error] Error parsing datetime of POST with the ID of %s",
                                        id));
                    }
                }

                return postList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return postList;
        }
    }

    public List<Post> getPostsById(String postId) throws CustomDateTimeParseException {
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
