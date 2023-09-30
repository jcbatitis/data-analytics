package views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.GridUtil;
import utils.StageUtil;

public class RegistrationView extends Stage {

    private GridPane grid;
    private Scene scene;

    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label isVipLabel;

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField isVipField;

    public Button submitButton;

    public RegistrationView() {

    }

    private void setupDefaults() {
        StageUtil.centerStage(this);

        grid = GridUtil.setupGrid();
        scene = new Scene(grid, 600, 275);
        this.setScene(scene);

    }

}
