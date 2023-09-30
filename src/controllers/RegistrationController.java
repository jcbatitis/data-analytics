package controllers;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.User;
import services.UserDao;
import utils.GlobalUtil;
import views.RegistrationView;

public class RegistrationController {
    private RegistrationView view;
    private UserDao dao;

    public RegistrationController() {
        view = new RegistrationView();
        dao = new UserDao();
    }

    public void show() {
        view.show();

        view.loginButton.setOnAction(e -> {
            view.close();
            new LoginController().show();
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

            if (isExisting) {
                view.validationMessage.setText("Please use a different username.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                view.validationMessage.setText("Password and confirm password is not the same.");
                return;
            }


            User newUser = new User(String.valueOf(usersLength + 1), firstName, lastName, username, password, isVip);
            dao.create(newUser);

            view.hide();

            DashboardController dashboardController = new DashboardController(newUser);
            dashboardController.show();
        });
    }

}
