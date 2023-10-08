package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import models.Post;
import utils.ContentUtil;
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
    public Post get(String postId) {
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
                }

                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean create(Post post) {
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
    public Boolean update(Post post) {
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
                System.out.println("Update into table executed successfully");
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
    public Boolean delete(Post post) {
        String query = "DELETE FROM Posts WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, post.getId());

            int result = statement.executeUpdate();

            if (result == 1) {
                System.out.println("Deletion from Posts table executed successfully");
                System.out.println(result + " row(s) affected");

                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
                "OR date_time LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= 6; i++) {
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

                    postList.add(new Post(id, content, author, likes, shares, date));
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

                    postList.add(new Post(id, content, author, likes, shares, date));
                }

                return postList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return postList;
        }
    }

    public Boolean exportPost(Post post) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Post");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("post.csv");

        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile == null) {
            return false;
        }

        String filePath = selectedFile.getAbsolutePath();
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.append("Id,Content,Author,Likes,Shares,Date\n");

            writer.append(post.getId() + ",");
            writer.append(post.getContent() + ",");
            writer.append(post.getAuthor() + ",");
            writer.append(post.getLikes() + ",");
            writer.append(post.getShares() + ",");
            writer.append(post.getDateTime() + "\n");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean exportAll(ObservableList<Post> posts) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Post");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("post.csv");

        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile == null) {
            return false;
        }

        String filePath = selectedFile.getAbsolutePath();
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.append("Id,Content,Author,Likes,Shares,Date\n");
            for (Post post : posts) {
                String content = ContentUtil.format(post.getContent());

                writer.append(post.getId() + ",");
                writer.append(post.getContent() + ",");
                writer.append(post.getAuthor() + ",");
                writer.append(post.getLikes() + ",");
                writer.append(post.getShares() + ",");
                writer.append(post.getDateTime() + "\n");
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean importAll() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Post");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile == null) {
            return false;
        }

        String filePath = selectedFile.getAbsolutePath();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data.length == 6) {

                    String id = data[0];
                    String content = data[1];
                    String author = data[2];
                    int likes = Integer.parseInt(data[3]);
                    int shares = Integer.parseInt(data[4]);
                    String date = data[5];

                    Post post = new Post(id, content, author, likes, shares, date);

                    if (!create(post)) {
                        System.err.println("Failed to insert post with ID: " + post.getId());
                        return false;
                    }
                }
            }

            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integer");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
