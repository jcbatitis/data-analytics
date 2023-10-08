package controllers;

import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidFormSubmissionException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import models.User;
import services.UserDao;
import utils.GlobalUtil;
import utils.UUIDUtil;
import views.DashboardView;
import views.LoginView;
import views.UserView;

public class UserController {
    private Stage primaryStage;
    private UserView view;
    private User user;
    private final UserDao dao = new UserDao();

    private Boolean hasLoggedUser;

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
    public UserController(UserView view) {
        this.view = view;
        this.hasLoggedUser = false;

        setupEventHandlers();
    }

    /**
     * SETUPS EVENT HANDLERS FOR VIEW
     */
    private void setupEventHandlers() {
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
        hasLoggedUser = true;

        view.setFirstName(user.getFirstName());
        view.setLastName(user.getLastName());
        view.setUsername(user.getUsername());
        view.setVipField(user.isVIP());

        view.getBackButton().setText("Back to Dashboard");
        view.getSubmitButton().setText("Update");
        // view.getUsernameField().setDisable(true);
        view.getVipGrid().setVisible(true);
    }

    /**
     * BACK HANDLER METHOD
     * 
     * @param e event handler variable
     */
    private void backHandler(ActionEvent e) {
        if (hasLoggedUser) {
            setupDashboardView(user);
        } else {
            LoginView view = new LoginView();
            LoginController controller = new LoginController(view);

            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle(view.getTitle());
            primaryStage.setScene(view.getScene());
        }
    }

    /**
     * SUBMIT HANDLER METHOD
     * 
     * @param event event handler variable
     */
    private void submitHandler(ActionEvent event) {
        try {
            String firstName = view.getFirstName();
            String lastName = view.getLastName();
            String username = view.getUsername();
            String password = view.getPassword();
            String confirmPassword = view.getConfirmPassword();
            Boolean isVip = view.getVipField();

            Boolean isExisting = dao.checkUserIfExisting(username);

            String[] fields = { firstName, lastName, username, password, confirmPassword
            };

            GlobalUtil.validateFormControls(fields);
            view.setValidationMessage("");

            if (isExisting && !hasLoggedUser) {
                view.setValidationMessage("Username taken, please use a different username.");
                return;
            }

            if (isExisting && !username.equals(user.getUsername())) {
                view.setValidationMessage("Username taken, please use a different username.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                view.setValidationMessage("Password and confirm password is not the same.");
                return;
            }

            String userId = hasLoggedUser ? user.getUserId()
                    : UUIDUtil.guid();

            User userPayload = new User(userId, firstName, lastName, username, password,
                    isVip);

            userPayload.setConfirmPassword(confirmPassword);

            if (!hasLoggedUser) {
                createUser(userPayload);
            } else {
                updateUser(userPayload);
            }
        } catch (InvalidFormSubmissionException e) {
            view.setValidationMessage(e.getMessage());
        }
    }

    /**
     * CREATES USER
     * 
     * @param payload object user to be created
     */
    private void createUser(User payload) {
        try {
            Boolean job = dao.create(payload);

            if (job) {
                setupDashboardView(payload);
                this.user = payload;
            }
        } catch (EntityAlreadyExistsException e) {
            view.setValidationMessage(e.getMessage());
        }
    }

    /**
     * UPDATES USER
     * 
     * @param payload object user to be updated
     */
    private void updateUser(User payload) {
        try {
            Boolean job = dao.update(payload);
            if (job) {
                view.setValidationMessage("User details updated successfully");
                this.user = payload;
            }
        } catch (EntityNotFoundException e) {
            view.setValidationMessage(e.getMessage());
        }
    }

    /**
     * SETUPS DASHBOARD VIEW
     * 
     * @param user set selected user for the dashboarv iew
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
