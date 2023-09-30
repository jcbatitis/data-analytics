package controllers;

import _sqlite.Database;
import services.LoginDao;
import views.LoginView;

public class LoginController {
    private LoginView view;
    private LoginDao dao;

    public LoginController() {
        view = new LoginView();
        dao = new LoginDao();
    }

    public void show() {
        view.show();

        view.submitButton.setOnAction(e -> {
            String username = view.usernameField.getText();
            String password = view.passwordField.getText();

            if (dao.checkUserIfValid(username, password)) {
                System.out.println("Valid");
            } else {
                System.out.println("Invalid");

            }
        });
    }
}
