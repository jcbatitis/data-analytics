
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage applicationStage) {
        new Login();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
