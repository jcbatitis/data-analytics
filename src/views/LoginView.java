package views;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginView extends Stage {

    private Label usernameLabel;
    private Label passwordLabel;

    public TextField usernameField;
    public TextField passwordField;

    public Button submitButton;

    public LoginView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        this.setX((primScreenBounds.getWidth() - this.getWidth()) / 2);
        this.setY((primScreenBounds.getHeight() - this.getHeight()) / 2);

        Scene scene = new Scene(grid, 300, 275);
        this.setScene(scene);

        // Header
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // Username
        usernameLabel = new Label("Username: ");
        usernameField = new TextField();
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);

        // Password
        passwordLabel = new Label("Password: ");
        passwordField = new TextField();
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);

        // Submit
        submitButton = new Button("Sign in");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(submitButton);

        Text validationMessage = new Text();
        HBox bbox = new HBox(10);
        grid.add(validationMessage, 1, 4);
        bbox.getChildren().add(validationMessage);

        grid.add(hBox, 1, 3);
        grid.add(bbox, 1, 4);
    }
}
