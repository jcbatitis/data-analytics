package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import exceptions.CustomDateTimeParseException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityNotFoundException;
import exceptions.FilePathRequiredException;
import exceptions.InvalidFormSubmissionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import models.Post;
import models.User;
import services.PostDao;
import utils.FileReaderUtil;
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
                .addListener(GlobalUtil.numericHandler(view.getLikesField()));
        view.getSharesField().textProperty()
                .addListener(GlobalUtil.numericHandler(view.getSharesField()));
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

    public void exportHandler(ActionEvent event) {
        try {
            view.setValidationMessage("");
            view.getExportButton().setDisable(true);

            File selectedFile = FileReaderUtil.getDirectoryForSaving();
            String filePath = selectedFile.getAbsolutePath();
            try (FileWriter writer = new FileWriter(filePath)) {

                writer.append("Id,Content,Author,Likes,Shares,Date\n");

                writer.append(post.getId() + ",");
                writer.append(post.getContent() + ",");
                writer.append(post.getAuthor() + ",");
                writer.append(post.getLikes() + ",");
                writer.append(post.getShares() + ",");
                writer.append(post.getDateTime() + "\n");

                view.toggleValidationMessageClass(true);
                view.setValidationMessage("File exported successfully!");

            } catch (IOException e) {
                view.toggleValidationMessageClass(false);
                view.setValidationMessage(e.getMessage());
            }
        } catch (FilePathRequiredException e) {
            view.toggleValidationMessageClass(false);
            view.setValidationMessage(e.getMessage());
        } finally {
            view.getExportButton().setDisable(false);
        }
    }

    /**
     * BACK HANDLER METHOD
     * 
     * @param e event handler variable
     */
    public void backHandler(ActionEvent e) {
        DashboardView view = new DashboardView();
        setupDashboardView(view, user);
    }

    /**
     * SUBMIT HANDLER METHOD
     * 
     * @param event event handler variable
     */
    public void submitHandler(ActionEvent event) {
        try {
            String content = view.getContent();
            String author = view.getAuthor();
            Integer likes = view.getLikes();
            Integer shares = view.getShares();
            String dateTime = view.getDateTime();

            Object[] fields = { content, author, likes, shares, dateTime };

            view.setValidationMessage("");

            GlobalUtil.validateFormControls(fields);
            GlobalUtil.validateDateControl(dateTime);

            String postId = hasPostDetails ? post.getId()
                    : UUIDUtil.guid();

            Post postPayload = new Post(postId, content, author, likes, shares, dateTime);

            if (!hasPostDetails) {
                createPost(postPayload);
            } else {
                updatePost(postPayload);
            }
        } catch (InvalidFormSubmissionException e) {
            view.toggleValidationMessageClass(false);
            view.setValidationMessage(e.getMessage());
        } catch (CustomDateTimeParseException e) {
            view.toggleValidationMessageClass(false);
            view.setValidationMessage(e.getMessage());
        }
    }

    /**
     * CREATES POST
     * 
     * @param payload object user to be created
     */
    private void createPost(Post payload) {
        try {
            Boolean job = dao.create(payload);

            if (job) {

                DashboardView view = new DashboardView();
                view.toggleValidationMessageClass(true);
                view.setValidationMessage("Post created successfully.");

                setupDashboardView(view, user);
            }
        } catch (EntityAlreadyExistsException e) {
            view.toggleValidationMessageClass(false);
            view.setValidationMessage(e.getMessage());
        }
    }

    /**
     * UPDATES POST
     * 
     * @param payload object user to be updated
     */
    private void updatePost(Post payload) {
        try {
            Boolean job = dao.update(payload);
            if (job) {
                view.toggleValidationMessageClass(true);
                view.getEditButton().setDisable(false);
                view.getExportButton().setDisable(false);
                view.getDeleteButton().setDisable(false);
                view.getSubmitButton().setDisable(true);
                view.setValidationMessage("Post updated successfully!");
            }
        } catch (EntityNotFoundException e) {
            view.toggleValidationMessageClass(false);
            view.setValidationMessage(e.getMessage());
        }
    }

    private void deleteHandler(ActionEvent event) {
        try {
            Boolean job = dao.delete(post);
            if (job) {
                DashboardView view = new DashboardView();
                view.toggleValidationMessageClass(true);
                view.setValidationMessage("Post deleted successfully.");

                setupDashboardView(view, user);
            }
        } catch (EntityNotFoundException e) {
            view.toggleValidationMessageClass(false);
            view.setValidationMessage(e.getMessage());
        }
    }

    private void editHandler(ActionEvent e) {
        view.disableFormControls(false);
        view.setValidationMessage("");
        view.getExportButton().setDisable(true);
        view.getEditButton().setDisable(true);
        view.getDeleteButton().setDisable(true);

    }

    /**
     * SETUPS DASHBOARD VIEW
     * 
     * @param user set selected user
     */
    private void setupDashboardView(DashboardView view, User user) {
        DashboardController controller = new DashboardController(view);

        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Data Analytics Hub - Dashboard");
        primaryStage.setScene(view.getScene());
    }
}
