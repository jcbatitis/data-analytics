package controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import models.Post;
import models.User;
import services.PostDao;
import views.DashboardView;

public class DashboardController {
    private DashboardView view;
    private PostDao dao;
    private User user;

    public DashboardController(User user) {
        view = new DashboardView();
        dao = new PostDao();

        this.user = user;
    }

    public void show() {
        view.show();

        view.setSelectButtonAction(post -> {
            view.close();
            PostController postController = new PostController(post.getId());
            postController.setUserDetails(user);
            postController.show();
        });

        getPosts();

        String sceneTitle = String.format("Welcome %s", user.getFullName());
        view.sceneTitle.setText(sceneTitle);

        view.editProfile.setOnAction(e -> {
            new UserController(this.user).show();
            view.close();
        });

        view.deleteButton.setOnAction(e -> {

        });

        view.refreshButton.setOnAction(e -> {
            view.searchField.clear();
            getPosts();
        });

        PauseTransition delay = new PauseTransition(Duration.seconds(1));

        delay.setOnFinished(evt -> {
            String searchBy = view.searchToggleGroup.getSelectedToggle().getUserData().toString();
            String searchTerm = view.searchField.getText();

            if (searchBy == "term") {
                ObservableList<Post> posts = FXCollections.observableArrayList(dao.getPostsBySearchTerm(searchTerm));
                setTableItems(posts);
            }

            else if (searchBy == "postId") {
                ObservableList<Post> posts = FXCollections.observableArrayList(dao.getPostsById(searchTerm));
                setTableItems(posts);
            }
        });

        view.searchField.textProperty().addListener((o, oldValue, newValue) -> delay.playFromStart());
    }

    public void getPosts() {
        ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
        setTableItems(posts);
    }

    public void setTableItems(ObservableList<Post> posts) {
        view.table.setItems(posts);
    }
}
