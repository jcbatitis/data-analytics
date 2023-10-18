package main.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.models.Post;
import main.models.User;
import main.views.DashboardView;
import main.views.DataVisualisationView;

public class DataVisualisationController {
    private Stage primaryStage;
    private DataVisualisationView view;
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
    public DataVisualisationController(DataVisualisationView view) {
        this.view = view;

        view.getSubmitButton().setOnAction(this::submitHandler);
        view.getBackButton().setOnAction(this::backHandler);

    }

    /**
     * SETS CURRENT USER
     * 
     * @param user set selected user on dashboard view
     */
    public void setUser(User user) {
        this.user = user;

        String welcomeMessage = String.format("Welcome %s!", user.getFirstName());
        view.setHeader(welcomeMessage);
    }

    public void setPieData(ObservableList<Post> posts) {
        List<Post> hundredsCategory = posts.stream()
                .filter(post -> post.getLikes() >= 0 && post.getLikes() <= 99)
                .collect(Collectors.toList());

        List<Post> thousandsCategory = posts.stream()
                .filter(post -> post.getLikes() > 100 && post.getLikes() <= 999)
                .collect(Collectors.toList());

        List<Post> aboveThousandsCategory = posts.stream()
                .filter(post -> post.getLikes() > 999)
                .collect(Collectors.toList());

        view.setHundrendsData(hundredsCategory.size());
        view.setThousandsData(thousandsCategory.size());
        view.setExceedsThousandsData(aboveThousandsCategory.size());
    }

    /**
     * SUBMIT HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void submitHandler(ActionEvent e) {

    }

    /**
     * BACK HANDLER METHOD
     * 
     * @param e event handler variable
     */
    private void backHandler(ActionEvent e) {
        setupDashboardView(user);
    }

    /**
     * SETUPS DASHBOARD VIEW
     * 
     * @param user set selected user
     */
    private void setupDashboardView(User user) {
        DashboardView view = new DashboardView();
        DashboardController controller = new DashboardController(view);

        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Data Analytics Hub - Dashboard");
        primaryStage.setScene(view.getScene());
    }
}
