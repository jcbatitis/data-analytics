package controllers;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Post;
import models.User;
import services.PostDao;
import utils.GlobalUtil;
import utils.UUIDUtil;
import views.DashboardView;
import views.PostView;

public class PostController {
    private Stage primaryStage;
    private PostView view;
    private Post post;
    private User user;
    private final PostDao dao = new PostDao();

    private Boolean hasPostDetails = false;

    /**
     * @param primaryStage sets current primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public PostController(PostView view) {
        this.view = view;

        setupEventHandlers();
    }

    /**
     * SETUPS EVENT HANDLERS FOR VIEW
     */
    public void setupEventHandlers() {
        view.getExportButton().setOnAction(this::exportHandler);
        view.getEditButton().setOnAction(this::editHandler);
        view.getDeleteButton().setOnAction(this::deleteHandler);
        view.getSubmitButton().setOnAction(this::submitHandler);
        view.getBackButton().setOnAction(this::backHandler);
        view.getLikesField().textProperty()
                .addListener(numericHandler(view.getLikesField()));
        view.getSharesField().textProperty()
                .addListener(numericHandler(view.getSharesField()));
    }

    public void setPost(Post post) {
        this.post = post;
        this.hasPostDetails = true;

        view.setPostId(post.getId());
        view.setContent(post.getContent());
        view.setAuthor(post.getAuthor());
        view.setLikes(post.getLikes());
        view.setShares(post.getShares());
        view.setDateTime(post.getDateTime());

        view.disableFormControls(true);
        view.getSubmitButton().setText("Update");
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void exportHandler(ActionEvent e) {
        view.setValidationMessage("");
        view.getExportButton().setDisable(true);

        Boolean job = dao.exportPost(post);

        if (job) {
            view.setValidationMessage("Post exported successfully!");
            view.getExportButton().setDisable(false);
        } else {
            view.getExportButton().setDisable(false);
        }
    }

    /**
     * BACK HANDLER METHOD
     * 
     * @param e event handler variable
     */
    public void backHandler(ActionEvent e) {
        setupDashboardView(user);
    }

    /**
     * SUBMIT HANDLER METHOD
     * 
     * @param event event handler variable
     */
    public void submitHandler(ActionEvent e) {
        try {
            String content = view.getContent();
            String author = view.getAuthor();
            Integer likes = view.getLikes();
            Integer shares = view.getShares();
            String dateTime = view.getDateTime();

            Object[] fields = { content, author, likes, shares, dateTime };
            view.setValidationMessage("");

            if (GlobalUtil.hasEmptyField(fields)) {
                view.setValidationMessage("All fields must be filled out.");
                return;
            }

            String postId = hasPostDetails ? post.getId()
                    : UUIDUtil.guid();

            Post payload = new Post(postId, content, author, likes, shares, dateTime);

            if (!hasPostDetails) {
                Boolean job = dao.create(payload);
                if (job) {
                    view.setValidationMessage("Post created successfully!");
                    setupDashboardView(user);
                    return;
                }
            } else {
                Boolean job = dao.update(payload);
                if (job) {
                    view.setValidationMessage("Post updated successfully!");

                    view.getEditButton().setDisable(false);
                    view.disableFormControls(true);
                    return;
                }
            }

            view.setValidationMessage("Error!");
        } catch (NumberFormatException numException) {
            numException.printStackTrace();
        }
    }

    private void deleteHandler(ActionEvent e) {
        dao.delete(post);
    }

    private void editHandler(ActionEvent e) {
        view.disableFormControls(false);
        view.setValidationMessage("");
        view.getEditButton().setDisable(true);
    }

    private ChangeListener<String> numericHandler(TextField textField) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
    }

    /**
     * SETUPS DASHBOARD VIEW
     * 
     * @param user set selected user
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
