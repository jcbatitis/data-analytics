
package main.controllers;

import main.exceptions.InvalidFormSubmissionException;
import main.exceptions.EntityNotFoundException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.models.User;
import main.services.UserDao;
import main.utils.GlobalUtil;
import main.views.DashboardView;
import main.views.LoginView;
import main.views.UserView;

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
        view.setPassword("ruff");

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
    private void submitHandler(ActionEvent event) {
        try {

            String username = view.getUsername();
            String password = view.getPassword();

            String[] fields = { username, password };

            view.setValidationMessage("");
            GlobalUtil.validateFormControls(fields);

            User user = dao.checkUserIfValid(username, password);

            DashboardView view = new DashboardView();
            DashboardController controller = new DashboardController(view);

            view.setTitle("Data Analytics Hub - Dashboard");

            controller.setUser(user);
            controller.setPrimaryStage(primaryStage);

            primaryStage.setTitle(view.getTitle());
            primaryStage.setScene(view.getScene());

        } catch (InvalidFormSubmissionException e) {
            view.setValidationMessage(e.getMessage());
        } catch (EntityNotFoundException e) {
            view.setValidationMessage(e.getMessage());
        }
    }

}