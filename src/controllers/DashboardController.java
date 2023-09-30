package controllers;

import models.User;
import services.UserDao;
import views.DashboardView;

public class DashboardController {
    private DashboardView view;
    private UserDao dao;
    private User user;

    public DashboardController(User user) {
        view = new DashboardView();
        dao = new UserDao();

        this.user = user;
    }

    public void show() {
        view.show();
        String sceneTitle = String.format("Welcome %s", user.getFullName());
        view.sceneTitle.setText(sceneTitle);
    }
}
