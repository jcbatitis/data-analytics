package controllers;

import models.Post;
import models.User;
import services.PostDao;
import utils.GlobalUtil;
import views.PostView;

public class PostController {
    private PostView view;
    private PostDao dao;
    private Post post;
    private User user;

    public PostController() {
        view = new PostView();
        dao = new PostDao();

        view.sceneTitle.setText("Add New Post");
        view.backButton.setText("Back to Dashboard");
        view.submitButton.setText("Save");
    }

    public PostController(String id) {
        view = new PostView();
        dao = new PostDao();

        view.sceneTitle.setText(String.format("Post #%s", id));
        view.backButton.setText("Back to Dashboard");
        view.submitButton.setText("Save");
        view.disableFormControls(true);

        setPostDetails(id);
    }

    public void setUserDetails(User user) {
        this.user = user;
    }

    public void show() {
        view.show();

        view.deleteButton.setOnAction(e -> {
            dao.delete(post);

            new DashboardController(user).show();
            view.close();
        });

        view.backButton.setOnAction(e -> {
            new DashboardController(user).show();
            view.close();
        });

        view.editButton.setOnAction(e -> {
            view.disableFormControls(false);
            view.validationMessage.setText("");
            view.editButton.setDisable(true);
        });

        view.submitButton.setOnAction(e -> {
            String id = view.postIdField.getText();
            String content = view.contentField.getText();
            String author = view.authorField.getText();
            Integer likes = !view.likesField.getText().isEmpty() ? Integer.parseInt(view.likesField.getText()) : null;
            Integer shares = !view.likesField.getText().isEmpty() ? Integer.parseInt(view.sharesField.getText()) : null;
            String dateTime = view.dateField.getText();

            Object[] fields = { id, content, author, likes, shares, dateTime };
            view.validationMessage.setText("");

            if (GlobalUtil.hasEmptyField(fields)) {
                view.validationMessage.setText("All fields must be filled out.");
                return;
            }

            Post tempPost = new Post(id, content, author, likes, shares, dateTime, post.getUserId());

            if (post != null) {
                dao.update(tempPost);
                view.editButton.setDisable(false);

                view.disableFormControls(true);
                view.validationMessage.setText("Post successfully updated!");
            }
        });
    }

    public void setPostDetails(String id) {
        post = dao.get(id);

        view.postIdField.setText(post.getId());
        view.contentField.setText(post.getContent());
        view.authorField.setText(post.getAuthor());
        view.likesField.setText(post.getLikes().toString());
        view.sharesField.setText(post.getShares().toString());
        view.dateField.setText(post.getDateTime());
    }
}
