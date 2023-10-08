package controllers;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import models.User;
import services.UserDao;
import views.DashboardView;
import views.LoginView;
import views.SubscriptionView;

public class SubscriptionController {
    private Stage primaryStage;
    private SubscriptionView view;
    private User user;
    private Boolean enforceLogout = false;

    private final UserDao dao = new UserDao();

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
    public SubscriptionController(SubscriptionView view) {
        this.view = view;

        view.getVipToggleGroup().selectedToggleProperty()
                .addListener((o, oldValue, newValue) -> vipToggleHandler());
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
        Boolean isVIP = user.isVIP();
        view.setHeader(welcomeMessage);

        if (isVIP) {
            view.getYesOption().setSelected(true);
        } else {
            view.getNoOption().setSelected(true);
        }
    }

    /**
     * SUBMIT HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void submitHandler(ActionEvent e) {

        Boolean isVIP = view.getVipToggleGroupValue();
        user.setIsVip(isVIP);

        Boolean job = dao.updateRole(user);
        if (job) {

            String backLabel = isVIP ? "Logout" : "Back";
            String message = isVIP
                    ? "Subscription approved! Please log out\n and log in again to access VIP functionalities."
                    : "Subscription sucessfully cancelled.";
            view.setValidationMessage(message);
            view.getBackButton().setText(backLabel);
            view.getSubmitButton().setDisable(true);

            enforceLogout = isVIP;
        } else {
            view.setValidationMessage("Error updating this user.");
        }
    }

    /**
     * BACK HANDLER METHOD
     * 
     * @param e event handler variable
     */
    private void backHandler(ActionEvent e) {
        if (enforceLogout) {
            LoginView view = new LoginView();
            LoginController controller = new LoginController(view);

            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle("Data Analytics Hub - Login");
            primaryStage.setScene(view.getScene());
            primaryStage.show();
            return;
        }
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

    private void vipToggleHandler() {
        view.getSubmitButton().setDisable(false);
    }
}