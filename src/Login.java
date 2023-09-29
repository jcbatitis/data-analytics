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

public class Login extends Stage {

    Login() {
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

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label username = new Label("Username: ");
        grid.add(username, 0, 1);

        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        Label password = new Label("Password: ");
        grid.add(password, 0, 2);

        TextField passwordField = new TextField();
        grid.add(passwordField, 1, 2);

        Button loginBtn = new Button("Sign in");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);

        hBox.getChildren().add(loginBtn);

        Text actiontarget = new Text();
        HBox bbox = new HBox(10);
        grid.add(actiontarget, 1, 4);
        bbox.getChildren().add(actiontarget);

        grid.add(hBox, 1, 3);
        grid.add(bbox, 1, 4);

        loginBtn.setOnAction((event) -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");

            // applicationStage.close();
            // start(new Stage());
        });
    }
}
