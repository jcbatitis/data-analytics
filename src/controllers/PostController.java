package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Database;
import models.Post;
import views.PostView;

public class PostController {
    private PostView view;
    private Database database;

    public PostController() {
        view = new PostView();
        database = new Database();
    }

    public void show() {
        ObservableList<Post> posts = FXCollections.observableArrayList(database.getPosts());
        view.setPosts(posts);

        view.show();
    }
}
