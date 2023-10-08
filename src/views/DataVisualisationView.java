package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.GridUtil;

public class DataVisualisationView {

    // Layout
    private GridPane grid;
    private Scene scene;

    // Controls
    private final Text title = new Text();
    private final Text header = new Text();
    private final Button backButton = new Button("Back");
    private final Button submitButton = new Button("Submit");
    private final Text validationMessage = new Text();
    private final PieChart pieChart = new PieChart();

    private final PieChart.Data hundredsCategory = new PieChart.Data("0-99", 0);
    private final PieChart.Data thousandsCategory = new PieChart.Data("100-999", 0);
    private final PieChart.Data exceedsThousandsCategory = new PieChart.Data("1000+", 0);

    /**
     * INITIALISES VIEW
     */
    public DataVisualisationView() {
        setupLayout();
    }

    /**
     * SETUP THE LAYOUT FOR THE VIEW
     */
    private void setupLayout() {
        grid = GridUtil.setupCenteredGrid();
        scene = new Scene(grid, 550, 550);

        setupHeader();
        setupPieChart();
        setupButtons();
    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupHeader() {
        header.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        grid.add(header, 0, 0);
    }

    private void setupPieChart() {
        pieChart.getData().add(hundredsCategory);
        pieChart.getData().add(thousandsCategory);
        pieChart.getData().add(exceedsThousandsCategory);

        grid.add(pieChart, 0, 1);
    }

    private void setupButtons() {

        HBox validationBox = new HBox(10);
        validationBox.setAlignment(Pos.CENTER_RIGHT);
        validationBox.getChildren().add(validationMessage);

        HBox buttonBox = new HBox(10);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        buttonBox.getChildren().addAll(backButton);

        VBox vBox = new VBox(10, validationBox, buttonBox);

        grid.add(vBox, 0, 2);
    }

    /*
     * GETTERS
     */
    public Text getTitle() {
        return title;
    }

    public Scene getScene() {
        return scene;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    /*
     * SETTERS
     */

    public void setValidationMessage(String message) {
        this.validationMessage.setText(message);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public void setHundrendsData(Integer num) {
        this.hundredsCategory.setPieValue(num);
    }

    public void setThousandsData(Integer num) {
        this.thousandsCategory.setPieValue(num);
    }

    public void setExceedsThousandsData(Integer num) {
        this.exceedsThousandsCategory.setPieValue(num);
    }
}
