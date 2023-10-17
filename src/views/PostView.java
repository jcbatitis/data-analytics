package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.GridUtil;

public class PostView {

    // Layout
    private GridPane grid;
    private Scene scene;

    // Controls
    private final Text title = new Text();
    private final Text header = new Text();

    private final Label contentLabel = new Label("Content: ");
    private final Label authorLabel = new Label("Author: ");
    private final Label likesLabel = new Label("Likes: ");
    private final Label sharesLabel = new Label("Shares: ");
    private final Label dateLabel = new Label("Date: ");

    private final TextField postIdField = new TextField();
    private final TextField contentField = new TextField();
    private final TextField authorField = new TextField();
    private final TextField likesField = new TextField();
    private final TextField sharesField = new TextField();
    private final TextField dateTimeField = new TextField();;

    private final Button backButton = new Button("Back To Dashboard");
    private final Button submitButton = new Button("Submit");
    private final Button exportButton = new Button("Export");

    private final Button deleteButton = new Button("Delete");
    private final Button editButton = new Button("Edit");

    private final Text validationMessage = new Text();

    private final BooleanProperty formDisabled = new SimpleBooleanProperty(false);

    /**
     * INITIALISES VIEW
     */
    public PostView() {
        setupLayout();
        setupFormListener();
    }

    /*
     * FORM LISTENER FOR DISABLING FORM CONTROLS
     */
    private void setupFormListener() {
        formDisabled.addListener((observable, oldValue, newValue) -> toggleEditableFields(newValue));
    }

    /**
     * SETUP THE LAYOUT FOR THE VIEW
     */
    private void setupLayout() {
        grid = GridUtil.setupCenteredGridSingleColumn();
        scene = new Scene(grid, 600, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());

        setupHeader();
        setupPostDetailControls();
        setupButtons();
        setupStyleClasses();
    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupHeader() {
        header.setFont(Font.font("Tahona", FontWeight.BOLD, 22));
        grid.add(header, 0, 0);
    }

    private void setupPostDetailControls() {
        Text postDetailTitle = new Text("Details");
        HBox hbox = new HBox(10);
        Region region1 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        hbox.getChildren().addAll(postDetailTitle, region1, deleteButton, editButton);

        postDetailTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        grid.add(hbox, 0, 1);

        // Post Id
        // Label postIdLabel = new Label("Post Id: ");

        // grid.add(postIdLabel, 0, 2);
        // grid.add(postIdField, 1, 2);

        grid.add(contentLabel, 0, 2);
        grid.add(contentField, 0, 3);

        grid.add(authorLabel, 0, 4);
        grid.add(authorField, 0, 5);

        grid.add(likesLabel, 0, 6);
        grid.add(likesField, 0, 7);

        grid.add(sharesLabel, 0, 8);
        grid.add(sharesField, 0, 9);

        grid.add(dateLabel, 0, 10);
        grid.add(dateTimeField, 0, 11);
    }

    private void setupButtons() {
        HBox bbox = new HBox(10);
        // grid.add(validationMessage, 1, 8);
        bbox.setAlignment(Pos.CENTER_RIGHT);
        bbox.getChildren().add(validationMessage);
        validationMessage.getStyleClass().add("error-message");
        validationMessage.getStyleClass().add("error-message");
        grid.add(bbox, 0, 12);

        HBox hBox = new HBox(10);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        hBox.getChildren().addAll(backButton, region, exportButton, submitButton);
        grid.add(hBox, 0, 13);
    }

    private void setupStyleClasses() {
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());

        contentLabel.getStyleClass().add("label");
        authorLabel.getStyleClass().add("label");
        likesLabel.getStyleClass().add("label");
        sharesLabel.getStyleClass().add("label");
        dateLabel.getStyleClass().add("label");

        postIdField.getStyleClass().add("text-field");
        contentField.getStyleClass().add("text-field");
        authorField.getStyleClass().add("text-field");
        likesField.getStyleClass().add("text-field");
        sharesField.getStyleClass().add("text-field");
        dateTimeField.getStyleClass().add("text-field");

        validationMessage.getStyleClass().add("error-message");
        submitButton.getStyleClass().setAll("submit-button");
        backButton.getStyleClass().setAll("back-button");

        submitButton.setMinWidth(75);
        backButton.setMinWidth(75);
        submitButton.setAlignment(Pos.CENTER);
        backButton.setAlignment(Pos.CENTER);
    }

    /*
     * GETTER
     */
    public boolean isFormDisabled() {
        return formDisabled.get();
    }

    public String getTitle() {
        return title.getText();
    }

    public Scene getScene() {
        return scene;
    }

    public String getPostId() {
        return postIdField.getText();
    }

    public String getContent() {
        return contentField.getText();
    }

    public String getAuthor() {
        return authorField.getText();
    }

    public Integer getLikes() {
        try {
            return Integer.parseInt(likesField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getShares() {
        try {
            return Integer.parseInt(sharesField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getDateTime() {
        return dateTimeField.getText();
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getExportButton() {
        return exportButton;
    }

    public TextField getLikesField() {
        return likesField;
    }

    public TextField getSharesField() {
        return sharesField;
    }

    /*
     * SETTER
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public void disableFormControls(boolean disable) {
        this.formDisabled.set(disable);
        this.submitButton.setDisable(disable);
    }

    public void toggleEditableFields(boolean disable) {
        postIdField.setDisable(disable);
        contentField.setDisable(disable);
        authorField.setDisable(disable);
        likesField.setDisable(disable);
        sharesField.setDisable(disable);
        dateTimeField.setDisable(disable);
    }

    public void setPostId(String postId) {
        postIdField.setText(postId);
    }

    public void setContent(String content) {
        contentField.setText(content);
    }

    public void setAuthor(String author) {
        authorField.setText(author);
    }

    public void setLikes(Integer likes) {
        likesField.setText(likes.toString());
    }

    public void setShares(Integer shares) {
        sharesField.setText(shares.toString());
    }

    public void setDateTime(String dateTime) {
        dateTimeField.setText(dateTime);
    }

    public void setValidationMessage(String message) {
        validationMessage.setText(message);
    }

    public void toggleValidationMessageClass(Boolean isSuccess) {
        if (isSuccess) {
            validationMessage.getStyleClass().setAll("success-message");
        } else {
            validationMessage.getStyleClass().setAll("error-message");
        }
    }
}
// public void setPosts(ObservableList<Post> posts) {
// this.posts = posts;
// table.setItems(this.posts);
// }

// public PostView() {
// this.setTitle("Data Analytics Hub");
// this.show();

// Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
// this.setX((primScreenBounds.getWidth() - this.getWidth()) / 2);
// this.setY((primScreenBounds.getHeight() - this.getHeight()) / 2);

// table = new TableView<Post>();
// TableColumn<Post, Integer> id = new TableColumn<>("ID");
// TableColumn<Post, String> content = new TableColumn<>("Content");
// TableColumn<Post, String> author = new TableColumn<>("Author");
// TableColumn<Post, Integer> likes = new TableColumn<>("Likes");
// TableColumn<Post, Integer> shares = new TableColumn<>("Shares");
// TableColumn<Post, String> date = new TableColumn<>("Date");

// id.setCellValueFactory(new PropertyValueFactory<>("id"));
// content.setCellValueFactory(new PropertyValueFactory<>("content"));
// author.setCellValueFactory(new PropertyValueFactory<>("author"));
// likes.setCellValueFactory(new PropertyValueFactory<>("likes"));
// shares.setCellValueFactory(new PropertyValueFactory<>("shares"));
// date.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

// List<TableColumn<Post, ?>> columns = Arrays.asList(id, content, author,
// likes, shares, date);
// table.getColumns().setAll(columns);
// table.setItems(posts);

// VBox layout = new VBox(10);
// layout.getChildren().addAll(table);

// Scene scene = new Scene(layout, 600, 400);
// this.setScene(scene);
// }