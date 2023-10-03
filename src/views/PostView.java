package views;

import java.util.Arrays;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Post;
import utils.GridUtil;
import utils.StageUtil;

public class PostView extends Stage {

    public GridPane grid;
    private Scene scene;

    private Label postIdLabel;
    private Label contentLabel;
    private Label authorLabel;
    private Label likesLabel;
    private Label sharesLabel;
    private Label dateLabel;

    public TextField postIdField;
    public TextArea contentField;
    public TextField authorField;
    public TextField likesField;
    public TextField sharesField;
    public TextField dateField;

    public Button backButton = new Button();
    public Button submitButton = new Button();
    public Button deleteButton = new Button("Delete");
    public Button editButton = new Button("Edit");

    public Text sceneTitle = new Text();
    public Text validationMessage = new Text();

    private BooleanProperty formDisabled = new SimpleBooleanProperty(false);

    public PostView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        formDisabled.addListener((observer, oldValue, disable) -> toggleEditableFields(disable));

        setupDefaults();
    }

    public BooleanProperty formDisabledProperty() {
        return formDisabled;
    }

    public boolean isFormDisabled() {
        return formDisabled.get();
    }

    public void disableFormControls(boolean disable) {
        this.formDisabled.set(disable);
        this.submitButton.setDisable(disable);
    }

    private void toggleEditableFields(boolean disable) {
        postIdField.setDisable(disable);
        contentField.setDisable(disable);
        authorField.setDisable(disable);
        likesField.setDisable(disable);
        sharesField.setDisable(disable);
        dateField.setDisable(disable);
    }

    private void setupDefaults() {
        StageUtil.centerStage(this);

        grid = GridUtil.setupCenteredGrid();
        scene = new Scene(grid, 700, 500);
        this.setScene(scene);

        setupHeader();
        setupPostDetailControls();
        setupButtons();
    }

    private void setupHeader() {
        sceneTitle.setFont(Font.font("Tahoba", FontWeight.BOLD, 22));
        grid.add(sceneTitle, 0, 0, 2, 1);
    }

    private void setupPostDetailControls() {
        Text postDetailTitle = new Text("Details");
        HBox hbox = new HBox(10);
        Region region1 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        hbox.getChildren().addAll(postDetailTitle, region1, deleteButton, editButton);

        postDetailTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        grid.add(hbox, 0, 1, 2, 1);

        postIdLabel = new Label("Post Id: ");
        postIdField = new TextField();
        grid.add(postIdLabel, 0, 2);
        grid.add(postIdField, 1, 2);

        contentLabel = new Label("Content: ");
        contentField = new TextArea();
        grid.add(contentLabel, 0, 3);
        grid.add(contentField, 1, 3, 1, 2);

        authorLabel = new Label("Author: ");
        authorField = new TextField();
        grid.add(authorLabel, 0, 5);
        grid.add(authorField, 1, 5);

        likesLabel = new Label("Likes: ");
        likesField = new TextField();
        grid.add(likesLabel, 0, 6);
        grid.add(likesField, 1, 6);

        sharesLabel = new Label("Shares: ");
        sharesField = new TextField();
        grid.add(sharesLabel, 0, 7);
        grid.add(sharesField, 1, 7);

        dateLabel = new Label("Date: ");
        dateField = new TextField();
        grid.add(dateLabel, 0, 8);
        grid.add(dateField, 1, 8);
    }

    private void setupButtons() {
        HBox bbox = new HBox(10);
        // grid.add(validationMessage, 1, 8);
        bbox.setAlignment(Pos.CENTER_RIGHT);
        bbox.getChildren().add(validationMessage);
        grid.add(bbox, 1, 9);

        HBox hBox = new HBox(10);
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        hBox.getChildren().addAll(backButton, region1, submitButton);
        grid.add(hBox, 0, 10, 2, 1);
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