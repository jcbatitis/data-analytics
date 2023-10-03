
package controllers;

import models.User;
import services.UserDao;
import views.LoginView;

public class LoginController {
    private LoginView view;
    private UserDao dao;

    public LoginController() {
        view = new LoginView();
        dao = new UserDao();
    }

    public void show() {
        view.show();

        view.submitButton.setOnAction(e -> {
            String username = view.usernameField.getText();
            String password = view.passwordField.getText();
            view.validationMessage.setText("");

            if (dao.checkUserIfValid(username, password)) {
                User user = dao.getUserByUsername(username);
                DashboardController dashboardController = new DashboardController(user);
                view.close();
                dashboardController.show();
            } else {
                view.validationMessage.setText("Please fill in the required details.");

            }
        });

        view.registerButton.setOnAction(e -> {
            view.close();
            new UserController().show();
            ;
        });
    }
}
