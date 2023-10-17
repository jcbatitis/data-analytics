package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.GridUtil;

public class LoginView {

    // Layout
    private GridPane grid;
    private Scene scene;

    // Controls
    Text sceneTitle = new Text("Dashboard Analytics Hub - S3856624");
    Label usernameLabel = new Label("Username");
    Label passwordLabel = new Label("Password");

    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button registerButton = new Button("Register");
    private final Button submitButton = new Button("Sign in");
    private final Text validationMessage = new Text();

    /**
     * INITIALISES VIEW
     */
    public LoginView() {
        setupLayout();
    }

    /**
     * SETUP THE LAYOUT FOR THE VIEW
     */
    private void setupLayout() {
        grid = GridUtil.setupCenteredGridSingleColumn();
        scene = new Scene(grid, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());

        setupHeader();
        setupUsername();
        setupPassword();
        setupButtons();
        setupStyleClasses();
    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupHeader() {
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(sceneTitle, 0, 0);
    }

    private void setupUsername() {

        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 0, 2);
    }

    private void setupPassword() {
        grid.add(passwordLabel, 0, 3);
        grid.add(passwordField, 0, 4);

    }

    private void setupButtons() {
        HBox bbox = new HBox(10);
        bbox.setAlignment(Pos.CENTER_RIGHT);
        bbox.getChildren().add(validationMessage);

        grid.add(bbox, 0, 5);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().addAll(registerButton, submitButton);

        grid.add(hBox, 0, 6);
    }

    private void setupStyleClasses() {
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());
        usernameLabel.getStyleClass().add("label");
        passwordLabel.getStyleClass().add("label");

        usernameField.getStyleClass().add("text-field");
        passwordField.getStyleClass().add("text-field");

        validationMessage.getStyleClass().add("error-message");
        submitButton.getStyleClass().setAll("submit-button");
        registerButton.getStyleClass().setAll("back-button");

        submitButton.setMinWidth(100);
        registerButton.setMinWidth(100);
        submitButton.setAlignment(Pos.CENTER);
        registerButton.setAlignment(Pos.CENTER);

    }

    /*
     * GETTERS
     */
    public String getTitle() {
        return "Login";
    }

    public Scene getScene() {
        return scene;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    /*
     * SETTERS
     */

    public void setValidationMessage(String message) {
        this.validationMessage.setText(message);
    }

    public void setUsername(String username) {
        this.usernameField.setText(username);
    }

    public void setPassword(String password) {
        this.passwordField.setText(password);
    }
}
