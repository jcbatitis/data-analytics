
import controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage applicationStage) {
        LoginController loginController = new LoginController();
        loginController.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
