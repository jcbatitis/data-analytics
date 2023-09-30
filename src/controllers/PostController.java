package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Post;
import services.PostDao;
import views.PostView;

public class PostController {
    private PostView view;
    private PostDao dao;

    public PostController() {
        view = new PostView();
        dao = new PostDao();
    }

    public void show() {
        ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
        view.setPosts(posts);

        view.show();
    }
}
