
import controllers.LoginController;
import controllers.PostController;
import controllers.RegistrationController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage applicationStage) {
        LoginController loginController = new LoginController();
        loginController.show();

        // PostController postController = new PostController();
        // postController.show();

        // RegistrationController registrationController = new RegistrationController();
        // registrationController.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
