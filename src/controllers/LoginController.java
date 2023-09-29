package controllers;

import models.Database;
import views.LoginView;

public class LoginController {
    private LoginView view;
    private Database database;

    public LoginController() {
        view = new LoginView();
        database = new Database();
    }

    public void showLogin() {
        view.show();

        view.submitButton.setOnAction(e -> {
            String username = view.usernameField.getText();
            String password = view.passwordField.getText();

            if (database.checkUserIfValid(username, password)) {
                System.out.println("Valid");
            } else {
                System.out.println("Invalid");

            }
        });
    }
}
