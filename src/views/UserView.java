package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.GridUtil;
import utils.StageUtil;

public class UserView extends Stage {

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

    public Button backButton = new Button();
    public Button submitButton = new Button();

    public Text sceneTitle = new Text();
    public Text validationMessage = new Text();

    public UserView() {
        this.setTitle("Data Analytics Hub - Registration");
        this.show();

        setupDefaults();
    }

    private void setupDefaults() {
        StageUtil.centerStage(this);

        grid = GridUtil.setupGrid();
        scene = new Scene(grid, 400, 450);
        this.setScene(scene);

        setupHeader();
        setupPersonalDetailControls();
        setupAccountDetailControls();
        setupIsVip();
        setupButtons();
    }

    private void setupHeader() {
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        grid.add(sceneTitle, 0, 0, 2, 1);
    }

    private void setupPersonalDetailControls() {
        Text personalDetailsTitle = new Text("Personal Details");
        personalDetailsTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        grid.add(personalDetailsTitle, 0, 1, 2, 1);

        firstNameLabel = new Label("First Name: ");
        firstNameField = new TextField();
        grid.add(firstNameLabel, 0, 2);
        grid.add(firstNameField, 1, 2);

        lastNameLabel = new Label("Last Name: ");
        lastNameField = new TextField();
        grid.add(lastNameLabel, 0, 3);
        grid.add(lastNameField, 1, 3);

        Separator separator = new Separator();
        grid.add(separator, 0, 4, 2, 1);
    }

    private void setupAccountDetailControls() {
        Text accountDetailsTitle = new Text("Account Details");
        accountDetailsTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        grid.add(accountDetailsTitle, 0, 5, 2, 1);
        usernameLabel = new Label("Username: ");
        usernameField = new TextField();
        grid.add(usernameLabel, 0, 6);
        grid.add(usernameField, 1, 6);

        passwordLabel = new Label("Password: ");
        passwordField = new TextField();
        grid.add(passwordLabel, 0, 7);
        grid.add(passwordField, 1, 7);

        confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPasswordField = new TextField();
        grid.add(confirmPasswordLabel, 0, 8);
        grid.add(confirmPasswordField, 1, 8);

    }

    private void setupIsVip() {
        isVipLabel = new Label("Is VIP: ");
        isVipField = new CheckBox();
        grid.add(isVipLabel, 0, 9);
        grid.add(isVipField, 1, 9);
    }

    private void setupButtons() {
        HBox bbox = new HBox(10);
        // grid.add(validationMessage, 1, 10);
        bbox.setAlignment(Pos.CENTER_RIGHT);
        bbox.getChildren().add(validationMessage);
        grid.add(bbox, 1, 10);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(backButton);
        hBox.getChildren().add(submitButton);
        grid.add(hBox, 1, 11);

    }
}
