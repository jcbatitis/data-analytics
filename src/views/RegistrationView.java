package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.GridUtil;
import utils.StageUtil;

public class RegistrationView extends Stage {

    public GridPane grid;
    private Scene scene;

    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label confirmPasswordLabel;
    private Label isVipLabel;

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField confirmPasswordField;
    public CheckBox isVipField;

    public Button loginButton;
    public Button submitButton;

    public Text validationMessage = new Text();

    public RegistrationView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        setupDefaults();
    }

    private void setupDefaults() {
        StageUtil.centerStage(this);

        grid = GridUtil.setupGrid();
        scene = new Scene(grid, 400, 350);
        this.setScene(scene);

        setupHeader();
        setupFirstName();
        setupLastName();
        setupUsername();
        setupPassword();
        setupConfirmPassword();
        setupIsVip();
        setupButtons();
    }

    private void setupHeader() {
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);
    }

    private void setupFirstName() {
        firstNameLabel = new Label("First Name: ");
        firstNameField = new TextField();
        grid.add(firstNameLabel, 0, 1);
        grid.add(firstNameField, 1, 1);
    }

    private void setupLastName() {
        lastNameLabel = new Label("Last Name: ");
        lastNameField = new TextField();
        grid.add(lastNameLabel, 0, 2);
        grid.add(lastNameField, 1, 2);
    }

    private void setupUsername() {
        usernameLabel = new Label("Username: ");
        usernameField = new TextField();
        grid.add(usernameLabel, 0, 3);
        grid.add(usernameField, 1, 3);
    }

    private void setupPassword() {
        passwordLabel = new Label("Password: ");
        passwordField = new TextField();
        grid.add(passwordLabel, 0, 4);
        grid.add(passwordField, 1, 4);
    }

    private void setupConfirmPassword() {
        confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPasswordField = new TextField();
        grid.add(confirmPasswordLabel, 0, 5);
        grid.add(confirmPasswordField, 1, 5);
    }

    private void setupIsVip() {
        isVipLabel = new Label("Is VIP: ");
        isVipField = new CheckBox();
        grid.add(isVipLabel, 0, 6);
        grid.add(isVipField, 1, 6);
    }

    private void setupButtons() {
        HBox bbox = new HBox(10);
        grid.add(validationMessage, 1, 7);
        bbox.setAlignment(Pos.CENTER_RIGHT);
        bbox.getChildren().add(validationMessage);
        grid.add(bbox, 1, 7);

        loginButton = new Button("Back to Login");
        submitButton = new Button("Register");

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(loginButton);
        hBox.getChildren().add(submitButton);
        grid.add(hBox, 1, 8);

    }
}
