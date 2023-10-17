package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.EntityAlreadyExistsException;
import exceptions.FilePathRequiredException;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Post;
import models.User;
import services.PostDao;
import utils.FileReaderUtil;
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
        view.setupVipControlSection(this.user.isVIP());
    }

    /**
     * SETUPS EVENT HANDLERS FOR VIEW
     */
    private void setupEventHandlers() {
        toggleSortSectionHandler();

        // adds delay for searching in table
        PauseTransition searchDelay = new PauseTransition(Duration.seconds(1));
        PauseTransition sortDelay = new PauseTransition(Duration.seconds(1));

        view.getAddButton().setOnAction(this::addPostHandler);
        view.getRefreshButton().setOnAction(this::refreshHandler);
        view.getDataVisualisationButton().setOnAction(this::dataVisualisationHandler);
        view.getExportButton().setOnAction(this::exportHandler);
        view.getImportButton().setOnAction(this::importHandler);
        view.getSubscribeButton().setOnAction(this::subscribeHandler);

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
        view.getExportButton().setVisible(false);

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
        String searchBy = "term";
        String searchTerm = view.getSearchTerm();

        if (searchTerm.isEmpty()) {
            return;
        }

        view.getToggleSortSection().setSelected(false);
        toggleSortSectionHandler();

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

    private void exportHandler(ActionEvent error) {
        try {
            ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
            view.getExportButton().setDisable(true);

            File selectedFile = FileReaderUtil.getDirectoryForSaving();
            String filePath = selectedFile.getAbsolutePath();
            try (FileWriter writer = new FileWriter(filePath)) {

                writer.append("Id,Content,Author,Likes,Shares,Date\n");

                for (Post post : posts) {
                    writer.append(post.getId() + ",");
                    writer.append(post.getContent() + ",");
                    writer.append(post.getAuthor() + ",");
                    writer.append(post.getLikes() + ",");
                    writer.append(post.getShares() + ",");
                    writer.append(post.getDateTime() + "\n");
                }

                view.toggleValidationMessageClass(true);
                view.setValidationMessage("File exported successfully!");

            } catch (IOException e) {
                view.toggleValidationMessageClass(false);
                view.setValidationMessage(e.getMessage());
            }
        } catch (FilePathRequiredException e) {
            view.toggleValidationMessageClass(false);
            view.setValidationMessage(e.getMessage());
        } finally {
            view.getExportButton().setDisable(false);
        }
    }

    private void importHandler(ActionEvent event) {
        try {
            view.getImportButton().setDisable(true);
            File selectedFile = FileReaderUtil.readFile();
            String filePath = selectedFile.getAbsolutePath();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                String line;
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

                        if (!dao.create(post)) {
                            System.err.println("Failed to insert post with ID: " + post.getId());
                        }
                    }
                }

                view.toggleValidationMessageClass(true);
                view.setValidationMessage("File imported successfully!");

            } catch (IOException e) {
                view.toggleValidationMessageClass(false);
                view.setValidationMessage(e.getMessage());
            } catch (EntityAlreadyExistsException e) {
                view.toggleValidationMessageClass(false);
                view.setValidationMessage(e.getMessage());
            } finally {
                ObservableList<Post> posts = FXCollections.observableArrayList(dao.getAll());
                setTableItems(posts);
            }

        } catch (FilePathRequiredException e) {
            view.setValidationMessage(e.getMessage());
        } finally {
            view.getImportButton().setDisable(false);
        }
    }

    private void toggleSortSectionHandler() {
        Boolean isDisabled = !view.getToggleSortSection().isSelected();
        for (Toggle toggle : view.getSortToggleGroup().getToggles()) {
            Node node = (Node) toggle;
            node.setDisable(isDisabled);
        }

        if (!isDisabled) {
            view.getSearchField().setText("");
        }

        view.getSortField().setDisable(isDisabled);
        view.getSortButton().setDisable(isDisabled);
        view.getSortField().setText("");
    }
}
