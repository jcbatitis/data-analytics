package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class LoginView extends Stage {

    private GridPane grid;
    private Scene scene;

    private Label usernameLabel;
    private Label passwordLabel;

    public TextField usernameField;
    public TextField passwordField;

    public Button submitButton;

    public LoginView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        setupDefaults();
    }

    private void setupDefaults() {
        StageUtil.centerStage(this);

        grid = GridUtil.setupGrid();
        scene = new Scene(grid, 300, 275);
        this.setScene(scene);

        setupHeader();
        setupUsername();
        setupPassword();
        setupSubmitButton();
    }

    private void setupHeader() {
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);
    }

    private void setupUsername() {
        usernameLabel = new Label("Username: ");
        usernameField = new TextField();
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
    }

    private void setupPassword() {
        passwordLabel = new Label("Password: ");
        passwordField = new TextField();
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
    }

    private void setupSubmitButton() {
        submitButton = new Button("Sign in");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(submitButton);
        grid.add(hBox, 1, 3);

        Text validationMessage = new Text();
        HBox bbox = new HBox(10);
        grid.add(validationMessage, 1, 4);
        bbox.getChildren().add(validationMessage);
        grid.add(bbox, 1, 4);
    }

}
