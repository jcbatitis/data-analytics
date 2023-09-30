package views;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.GridUtil;
import utils.StageUtil;

public class DashboardView extends Stage {

    public GridPane grid;
    private Scene scene;

    public Text sceneTitle = new Text();

    public DashboardView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        this.setupDefaults();
    }

    private void setupDefaults() {
        StageUtil.centerStage(this);

        grid = GridUtil.setupGrid();
        scene = new Scene(grid, 400, 350);
        this.setScene(scene);

        setupHeader();
    }

    private void setupHeader() {
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);
    }
}
