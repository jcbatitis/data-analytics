package controllers;

import java.util.Optional;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.User;
import services.UserDao;
import utils.GlobalUtil;
import views.UserView;

public class UserController {
    private UserView view;
    private UserDao dao;

    private User user;
    private Boolean hasLoggedUser;

    public UserController() {
        view = new UserView();
        dao = new UserDao();
        hasLoggedUser = false;

        view.sceneTitle.setText("REGISTRATION");
        view.backButton.setText("Back to Login");
        view.submitButton.setText("Register");
    }

    public UserController(User user) {
        view = new UserView();
        dao = new UserDao();
        hasLoggedUser = true;

        view.sceneTitle.setText("UPDATE USER PROFILE");
        view.backButton.setText("Back to Dashboard");
        view.submitButton.setText("Update");
        view.usernameField.setDisable(true);

        this.setUserDetails(user);
    }

    public void show() {
        view.show();

        view.backButton.setOnAction(e -> {
            if (!hasLoggedUser) {
                new LoginController().show();
            } else {
                new DashboardController(user).show();
            }
            view.close();
        });

        view.submitButton.setOnAction(e -> {

            String firstName = view.firstNameField.getText();
            String lastName = view.lastNameField.getText();
            String username = view.usernameField.getText();
            String password = view.passwordField.getText();
            String confirmPassword = view.confirmPasswordField.getText();
            Boolean isVip = view.isVipField.isSelected();

            Boolean isExisting = dao.checkUserIfExisting(username);
            Integer usersLength = dao.getAll().size();

            String[] fields = { firstName, lastName, username, password, confirmPassword };
            view.validationMessage.setText("");

            if (GlobalUtil.hasEmptyField(fields)) {
                view.validationMessage.setText("All fields must be filled out.");
                return;
            }

            if (isExisting && !hasLoggedUser) {
                view.validationMessage.setText("Please use a different username.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                view.validationMessage.setText("Password and confirm password is not the same.");
                return;
            }

            String userId = hasLoggedUser ? user.getUserId() : String.valueOf(usersLength + 1);
            User tempUser = new User(userId, firstName, lastName, username, password,
                    isVip);

            if (!hasLoggedUser) {
                dao.create(tempUser);
            } else {
                dao.update(tempUser);
            }

            new DashboardController(tempUser).show();
            view.close();
        });
    }

    public void setUserDetails(User user) {
        this.user = user;

        view.firstNameField.setText(user.getFirstName());
        view.lastNameField.setText(user.getLastName());
        view.usernameField.setText(user.getUsername());
        view.isVipField.setSelected(user.isVIP());
    }

}
