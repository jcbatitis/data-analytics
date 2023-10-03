package controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
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

        List<Post> posts = dao.getPostsBySearchTerm("3");
        System.out.println(posts);
    }

    private void setupCheckboxListeners() {
        for (Post post : view.posts) {
            post.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    System.out.println("Checkbox for post with ID " + post.getId() + " is checked.");
                    // Handle the checked event here
                } else {
                    System.out.println("Checkbox for post with ID " + post.getId() + " is unchecked.");
                    // Handle the unchecked event here
                }
            });
        }
    }

    public void show() {
        view.show();

        getPosts();

        String sceneTitle = String.format("Welcome %s", user.getFullName());
        view.sceneTitle.setText(sceneTitle);

        view.editProfile.setOnAction(e -> {
            new UserController(this.user).show();
            view.close();
        });

        view.deleteButton.setOnAction(e -> {
            TableColumn<Post, Boolean> select = new TableColumn<>("Action");
            select.setCellValueFactory(new PropertyValueFactory<>("selected"));
            select.setCellFactory(CheckBoxTableCell.forTableColumn(select));
            select.setEditable(true);
            view.table.getColumns().add(0, select);
            // List<Post> selectedPosts = new ArrayList<Post>();

            // for (Post post : view.table.getItems()) {
            // if (post.getSelected()) {
            // dao.delete(post);
            // selectedPosts.add(post);
            // }
            // }
            // getPosts();
        });

        // view.searchButton.setOnAction(e -> {
        // String searchBy =
        // view.searchToggleGroup.getSelectedToggle().getUserData().toString();
        // String searchTerm = view.searchField.getText();

        // System.out.println("Test");

        // if (searchBy == "term") {
        // ObservableList<Post> posts =
        // FXCollections.observableArrayList(dao.getPostsBySearchTerm(searchTerm));
        // setTableItems(posts);
        // }

        // else if (searchBy == "postId") {
        // ObservableList<Post> posts =
        // FXCollections.observableArrayList(dao.getPostsById(searchTerm));
        // setTableItems(posts);
        // }
        // });

        // view.refreshButton.setOnAction(e -> {
        // view.searchField.clear();
        // getPosts();
        // });

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
