
import controllers.LoginController;
import controllers.PostController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage applicationStage) {
        // LoginController loginController = new LoginController();
        // loginController.show();

        PostController postController = new PostController();
        postController.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
