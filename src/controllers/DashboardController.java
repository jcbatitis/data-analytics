package controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Action;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Post;
import models.User;
import services.PostDao;
import utils.GlobalUtil;
import views.DashboardView;
import views.DataVisualisationView;
import views.LoginView;
import views.PostView;
import views.SubscriptionView;
import views.UserView;

public class DashboardController {
    private PostDao dao = new PostDao();

    private Stage primaryStage;
    private DashboardView view;
    private User user;

    /**
     * @param primaryStage sets current primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * INITIALISES CONTROLLER
     * 
     * @param view initialises the current view
     */
    public DashboardController(DashboardView view) {
        this.view = view;
        this.setupEventHandlers();
        this.setupDefaultPosts();
    }

    /**
     * SETS CURRENT USER
     * 
     * @param user set selected user on dashboard view
     */
    public void setUser(User user) {
        this.user = user;

        String welcomeMessage = String.format("Welcome %s!", user.getFullName());
        view.setHeader(welcomeMessage);
    }

    /**
     * SETUPS EVENT HANDLERS FOR VIEW
     */
    private void setupEventHandlers() {
        toggleSearchSectionHandler();
        toggleSortSectionHandler();

        // adds delay for searching in table
        PauseTransition searchDelay = new PauseTransition(Duration.seconds(1));
        PauseTransition sortDelay = new PauseTransition(Duration.seconds(1));

        view.getAddButton().setOnAction(this::addPostHandler);
        view.getRefreshButton().setOnAction(this::refreshHandler);
        view.getDataVisualisationButton().setOnAction(this::dataVisualisationHandler);
        view.getExportButton().setOnAction(this::exportHandler);
        view.getImportButton().setOnAction(this::importHandler);

        view.getToggleSearchSection().selectedProperty().addListener((o, ol, nv) -> toggleSearchSectionHandler());
        view.getToggleSortSection().selectedProperty().addListener((o, ol, nv) -> toggleSortSectionHandler());

        view.getEditProfileMenu().setOnAction(this::editProfileHandler);
        view.getSubscriptionMenu().setOnAction(this::subscribeHandler);

        view.getLogoutMenu().setOnAction(this::logoutHandler);
        view.getSearchButton().setOnAction(this::searchHandler);
        view.getSortButton().setOnAction(this::sortHandler);

        view.getSearchField().textProperty().addListener((o, oldValue, newValue) -> searchDelay.playFromStart());
        view.getSortField().textProperty().addListener(GlobalUtil.numericHandler(view.getSortField()));

        view.setupSelectButtonEventHandler(event -> {
            Button clickedButton = (Button) event.getSource();
            Post post = (Post) clickedButton.getUserData();

            PostView view = new PostView();
            PostController controller = new PostController(view);

            view.setTitle("Data Analytics Hub - Update Post Details");
            view.setHeader("Update Post Details");

            controller.setUser(user);
            controller.setPost(post);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle(view.getTitle());
            primaryStage.setScene(view.getScene());

        });

        searchDelay.setOnFinished(this::searchHandler);
        sortDelay.setOnFinished(this::sortHandler);
    }

    /**
     * GET ALL POSTS FROM DB
     */
    private void setupDefaultPosts() {
        ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
        setTableItems(posts);
    }

    /**
     * SETS TABLE ITEMS
     * 
     * @param posts sets selected posts (all, filtered)
     */
    private void setTableItems(ObservableList<Post> posts) {
        view.setTableItems(posts);
    }

    /**
     * REFRESH EVENT HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void refreshHandler(ActionEvent event) {
        ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
        setTableItems(posts);

        view.getSearchField().setText("");
        view.getSortField().setText("");

    }

    /**
     * EDIT PROFILE HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void editProfileHandler(ActionEvent e) {
        UserView view = new UserView();
        UserController controller = new UserController(view);

        view.setTitle("Data Analytics Hub - Update User Details");
        view.setHeader("Update User Details");
        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle(view.getTitle());
        primaryStage.setScene(view.getScene());
    }

    /**
     * ADD POST HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void addPostHandler(ActionEvent e) {
        PostView view = new PostView();
        PostController controller = new PostController(view);

        view.setTitle("Data Analytics Hub - Add New Post");
        view.setHeader("Add New Post");
        view.getEditButton().setVisible(false);
        view.getDeleteButton().setVisible(false);

        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle(view.getTitle());
        primaryStage.setScene(view.getScene());
    }

    private void logoutHandler(ActionEvent e) {
        LoginView view = new LoginView();
        LoginController controller = new LoginController(view);

        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Data Analytics Hub - Login");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    private void subscribeHandler(ActionEvent e) {
        SubscriptionView view = new SubscriptionView();
        SubscriptionController controller = new SubscriptionController(view);

        controller.setUser(user);

        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Data Analytics Hub - Subscriptions");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    private void searchHandler(ActionEvent e) {
        String searchBy = view.getSearchToggleGroupValue();
        String searchTerm = view.getSearchTerm();

        if (searchTerm.isEmpty()) {
            return;
        }

        if (searchBy == "term") {
            ObservableList<Post> posts = FXCollections.observableArrayList(dao.getPostsBySearchTerm(searchTerm));
            setTableItems(posts);
        }

        else if (searchBy == "postId") {
            ObservableList<Post> posts = FXCollections.observableArrayList(dao.getPostsById(searchTerm));
            setTableItems(posts);
        }
    }

    private void sortHandler(ActionEvent e) {
        String sortBy = view.getSortToggleGroupValue();
        Integer limitValue = view.getSortValue();
        ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());

        if (limitValue == null) {
            setupDefaultPosts();
            return;
        }

        if (sortBy == "likes") {
            posts.sort((p1, p2) -> p2.getLikes() - p1.getLikes());
            List<Post> limitedList = posts.stream().limit(limitValue).collect(Collectors.toList());
            ObservableList<Post> limitedPosts = FXCollections.observableArrayList(limitedList);
            setTableItems(limitedPosts);
        } else if (sortBy == "shares") {
            posts.sort((p1, p2) -> p2.getShares() - p1.getShares());
            List<Post> limitedList = posts.stream().limit(limitValue).collect(Collectors.toList());
            ObservableList<Post> limitedPosts = FXCollections.observableArrayList(limitedList);
            setTableItems(limitedPosts);
        }
    }

    private void dataVisualisationHandler(ActionEvent e) {
        ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());

        DataVisualisationView view = new DataVisualisationView();
        DataVisualisationController controller = new DataVisualisationController(view);

        controller.setUser(user);
        controller.setPieData(posts);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Data Analytics Hub - Data Visualisation");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    private void exportHandler(ActionEvent e) {
        String message = "";
        ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
        view.getExportButton().setDisable(true);

        Boolean job = dao.exportAll(posts);

        if (job) {
            view.getExportButton().setDisable(false);
            message = "Posts exported successfully!";
        } else {
            view.getExportButton().setDisable(false);
            message = "Error exporting posts";
        }

        view.setValidationMessage(message);
    }

    private void importHandler(ActionEvent e) {
        String message = "";

        view.getImportButton().setDisable(true);
        Boolean job = dao.importAll();

        if (job) {
            view.getImportButton().setDisable(false);
            ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
            setTableItems(posts);
            message = "Posts imported successfully!";

        } else {
            view.getImportButton().setDisable(false);
            message = "Error importing posts";
        }
        view.setValidationMessage(message);

    }

    private void toggleSearchSectionHandler() {
        Boolean isDisabled = !view.getToggleSearchSection().isSelected();
        for (Toggle toggle : view.getSearchToggleGroup().getToggles()) {
            Node node = (Node) toggle;
            node.setDisable(isDisabled);
        }

        view.getSearchField().setDisable(isDisabled);
        view.getSearchButton().setDisable(isDisabled);

        if (!isDisabled) {
            // If the search section is enabled, disable the sort section
            view.getToggleSortSection().setSelected(false);
            toggleSortSectionHandler();
        }
        view.getSortField().setText("");
    }

    private void toggleSortSectionHandler() {
        Boolean isDisabled = !view.getToggleSortSection().isSelected();
        for (Toggle toggle : view.getSortToggleGroup().getToggles()) {
            Node node = (Node) toggle;
            node.setDisable(isDisabled);
        }

        view.getSortField().setDisable(isDisabled);
        view.getSortButton().setDisable(isDisabled);

        if (!isDisabled) {
            // If the sort section is enabled, disable the search section
            view.getToggleSearchSection().setSelected(false);
            toggleSearchSectionHandler();
        }
        view.getSearchField().setText("");
    }

}
