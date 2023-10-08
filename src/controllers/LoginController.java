
package controllers;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import models.User;
import services.UserDao;
import utils.GlobalUtil;
import views.DashboardView;
import views.LoginView;
import views.UserView;

public class LoginController {
    private Stage primaryStage;
    private LoginView view;
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
    public LoginController(LoginView view) {
        this.view = view;

        view.getSubmitButton().setOnAction(this::submitHandler);
        view.getRegisterButton().setOnAction(this::registerHandler);

        view.setUsername("ruff");
        view.setPassword("1234");

    }

    /**
     * REGISTER HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void registerHandler(ActionEvent e) {
        UserView view = new UserView();
        UserController controller = new UserController(view);

        view.setTitle("Data Analytics Hub - Registration");
        view.setHeader("Registration");
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle(view.getTitle());
        primaryStage.setScene(view.getScene());
    }

    /**
     * SUBMIT HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void submitHandler(ActionEvent e) {
        String username = view.getUsername();
        String password = view.getPassword();

        String[] fields = { username, password };

        if (GlobalUtil.hasEmptyField(fields)) {
            view.setValidationMessage("All fields must be filled out.");
            return;
        }

        if (dao.checkUserIfValid(username, password)) {
            User user = dao.getUserByUsername(username);

            DashboardView view = new DashboardView();
            DashboardController controller = new DashboardController(view);

            view.setTitle("Data Analytics Hub - Dashboard");

            controller.setUser(user);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle(view.getTitle());
            primaryStage.setScene(view.getScene());

        } else {
            view.setValidationMessage("Username or password is incorrect.");
        }
    }

}