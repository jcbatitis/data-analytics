package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        grid = GridUtil.setupCenteredGrid();
        scene = new Scene(grid, 600, 275);

        setupHeader();
        setupUsername();
        setupPassword();
        setupButtons();
    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupHeader() {
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);
    }

    private void setupUsername() {
        Label usernameLabel = new Label("Username: ");

        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
    }

    private void setupPassword() {
        Label passwordLabel = new Label("Password: ");

        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);

    }

    private void setupButtons() {
        HBox bbox = new HBox(10);
        bbox.setAlignment(Pos.CENTER_RIGHT);
        bbox.getChildren().add(validationMessage);
        grid.add(bbox, 0, 3, 2, 1);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().addAll(registerButton, submitButton);

        grid.add(hBox, 1, 4);
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
