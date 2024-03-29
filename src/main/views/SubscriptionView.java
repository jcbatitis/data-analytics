package main.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.utils.GridUtil;

public class SubscriptionView {

    // Layout
    private GridPane grid;
    private Scene scene;

    // Controls
    private final Text title = new Text();
    private final Text header = new Text();
    private final ToggleGroup vipToggleGroup = new ToggleGroup();
    private final GridPane vipGrid = GridUtil.setupNoPaddingGrid();
    private final Button backButton = new Button("Back to Dashboard");
    private final Button submitButton = new Button("Submit");
    private final Text validationMessage = new Text();
    private final RadioButton yesOption = new RadioButton("Yes");
    private final RadioButton noOption = new RadioButton("No");

    /**
     * INITIALISES VIEW
     */
    public SubscriptionView() {
        setupLayout();
    }

    /**
     * SETUP THE LAYOUT FOR THE VIEW
     */
    private void setupLayout() {
        grid = GridUtil.setupCenteredGrid();
        scene = new Scene(grid, 600, 275);
        scene.getStylesheets().add(getClass().getResource("/main/resources/style.css").toExternalForm());

        setupHeader();
        setupIsVip();
        setupButtons();
        setupStyleClasses();
    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupHeader() {
        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        grid.add(header, 0, 0);
    }

    private void setupIsVip() {
        Label vipDescriptionLabel = new Label(
                "Would you like to subscribe to the application for a monthly fee of $0?");

        noOption.setSelected(true);

        yesOption.setToggleGroup(vipToggleGroup);
        yesOption.setUserData(true);
        noOption.setToggleGroup(vipToggleGroup);
        noOption.setUserData(false);

        HBox hBox = new HBox(10, yesOption, noOption);
        VBox vBox = new VBox(5, new Separator(), vipDescriptionLabel, hBox);

        vipGrid.add(vBox, 0, 1);

        grid.add(vipGrid, 0, 1, 2, 1);
    }

    private void setupButtons() {
        HBox bbox = new HBox(10);
        bbox.setAlignment(Pos.CENTER_RIGHT);
        bbox.getChildren().add(validationMessage);
        validationMessage.getStyleClass().add("error-message");
        validationMessage.getStyleClass().add("error-message");
        grid.add(bbox, 0, 3, 2, 1);

        HBox hBox = new HBox(10);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().addAll(backButton, region, submitButton);

        grid.add(hBox, 0, 4, 2, 1);
    }

    private void setupStyleClasses() {
        scene.getStylesheets().add(getClass().getResource("/main/resources/style.css").toExternalForm());

        validationMessage.getStyleClass().add("error-message");
        submitButton.getStyleClass().setAll("submit-button");
        backButton.getStyleClass().setAll("back-button");

        submitButton.setMinWidth(100);
        backButton.setMinWidth(100);
        submitButton.setAlignment(Pos.CENTER);
        backButton.setAlignment(Pos.CENTER);
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

    public Boolean getVipToggleGroupValue() {
        String userData = vipToggleGroup.getSelectedToggle().getUserData().toString();
        return Boolean.parseBoolean(userData);
    }

    public ToggleGroup getVipToggleGroup() {
        return vipToggleGroup;
    }

    public RadioButton getYesOption() {
        return yesOption;
    }

    public RadioButton getNoOption() {
        return noOption;
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

    public void toggleValidationMessageClass(Boolean isSuccess) {
        if (isSuccess) {
            validationMessage.getStyleClass().setAll("success-message");
        } else {
            validationMessage.getStyleClass().setAll("error-message");
        }
    }
}
