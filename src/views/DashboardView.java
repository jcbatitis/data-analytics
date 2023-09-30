package views;

import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.GridUtil;
import utils.StageUtil;

public class DashboardView extends Stage {

    public GridPane grid;
    private Scene scene;

    public Text sceneTitle = new Text("HELLO");

    public DashboardView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        this.setupDefaults();
    }

    private void setupDefaults() {

        BorderPane root = new BorderPane();

        StageUtil.centerStage(this);
        Menu profileMenu = new Menu("Profile");
        final Menu postMenu = new Menu("Posts");

        MenuItem editProfile = new MenuItem("Edit Profile");
        MenuItem subscribe = new MenuItem("Subscribe to VIP program");

        profileMenu.getItems().addAll(editProfile, subscribe);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(profileMenu, postMenu);

        grid = GridUtil.setupGrid();

        root.setTop(menuBar);
        root.setCenter(grid);

        scene = new Scene(root, 400, 350);
        this.setScene(scene);

        setupHeader();
    }

    private void setupHeader() {
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 1, 1);
    }
}
