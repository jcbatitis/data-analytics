
import controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import views.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView view = new LoginView();
        LoginController controller = new LoginController(view);

        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Data Analytics Hub - Login");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
