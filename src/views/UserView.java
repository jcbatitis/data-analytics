package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.GridUtil;

public class UserView {

    // Layout
    private Scene scene;
    private GridPane grid;

    // Controls
    Text personalDetailsTitle = new Text("Personal Details");
    Text accountDetailsTitle = new Text("Account Details");

    Label firstNameLabel = new Label("First Name");
    Label lastNameLabel = new Label("Last Name");
    Label usernameLabel = new Label("Username");
    Label passwordLabel = new Label("Password");
    Label confirmPasswordLabel = new Label("Confirm Password");

    private final Text title = new Text();
    private final Text header = new Text();
    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final PasswordField confirmPasswordField = new PasswordField();

    private final GridPane vipGrid = GridUtil.setupNoPaddingGrid();

    private final CheckBox isVipField = new CheckBox();
    private final Button backButton = new Button("Back");
    private final Button submitButton = new Button("Submit");
    private final Text validationMessage = new Text();

    private Boolean showVipSection = false;

    /**
     * INITIALISES VIEW
     */
    public UserView() {
        setupLayout();
    }

    /**
     * SETUP THE LAYOUT FOR THE VIEW
     */
    private void setupLayout() {
        grid = GridUtil.setupCenteredGridSingleColumn();
        scene = new Scene(grid, 600, 600);

        setupHeader();
        setupPersonalDetailControls();
        setupAccountDetailControls();
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

    private void setupPersonalDetailControls() {
        personalDetailsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        grid.add(personalDetailsTitle, 0, 1);

        grid.add(firstNameLabel, 0, 2);
        grid.add(firstNameField, 0, 3);

        grid.add(lastNameLabel, 0, 4);
        grid.add(lastNameField, 0, 5);

        Separator separator = new Separator();
        grid.add(separator, 0, 6);
    }

    private void setupAccountDetailControls() {
        accountDetailsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        grid.add(accountDetailsTitle, 0, 7);

        grid.add(usernameLabel, 0, 8);
        grid.add(usernameField, 0, 9);

        grid.add(passwordLabel, 0, 10);
        grid.add(passwordField, 0, 11);

        grid.add(confirmPasswordLabel, 0, 12);
        grid.add(confirmPasswordField, 0, 13);

    }

    private void setupButtons() {
        HBox validationBox = new HBox(10);
        validationBox.setAlignment(Pos.CENTER_RIGHT);
        validationBox.getChildren().add(validationMessage);

        HBox buttonBox = new HBox(10);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        buttonBox.getChildren().addAll(backButton, region, submitButton);

        VBox vBox = new VBox(10, validationBox, buttonBox);

        grid.add(vBox, 0, 14);
    }

    private void setupStyleClasses() {
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());

        firstNameLabel.getStyleClass().add("label");
        lastNameLabel.getStyleClass().add("label");
        usernameLabel.getStyleClass().add("label");
        passwordLabel.getStyleClass().add("label");
        confirmPasswordLabel.getStyleClass().add("label");

        firstNameField.getStyleClass().add("text-field");
        lastNameField.getStyleClass().add("text-field");
        usernameField.getStyleClass().add("text-field");
        passwordField.getStyleClass().add("text-field");
        confirmPasswordField.getStyleClass().add("text-field");

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
    public String getTitle() {
        return title.getText();
    }

    public Scene getScene() {
        return scene;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getConfirmPassword() {
        return confirmPasswordField.getText();
    }

    public Boolean getVipField() {
        return isVipField.isSelected();
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public GridPane getVipGrid() {
        return vipGrid;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Boolean getShowVipSectionProperty() {
        return showVipSection;
    }

    /*
     * SETTERS
     */

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public void setValidationMessage(String message) {
        this.validationMessage.setText(message);
    }

    public void setFirstName(String firstName) {
        firstNameField.setText(firstName);
    }

    public void setLastName(String lastName) {
        lastNameField.setText(lastName);
    }

    public void setUsername(String username) {
        usernameField.setText(username);
    }

    public void setPassword(String password) {
        passwordField.setText(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        confirmPasswordField.setText(confirmPassword);
    }

    public void setVipField(Boolean isVip) {
        isVipField.setSelected(isVip);
    }
}
