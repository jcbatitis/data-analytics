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

    private void setupLayout() {
        grid = GridUtil.setupCenteredGrid();
        scene = new Scene(grid, 600, 600);

        setupHeader();
        setupPersonalDetailControls();
        setupAccountDetailControls();
        setupButtons();
    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupHeader() {
        header.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        grid.add(header, 0, 0, 2, 1);
    }

    private void setupPersonalDetailControls() {
        Text personalDetailsTitle = new Text("Personal Details");
        personalDetailsTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        grid.add(personalDetailsTitle, 0, 1, 2, 1);

        Label firstNameLabel = new Label("First Name: ");

        grid.add(firstNameLabel, 0, 2);
        grid.add(firstNameField, 1, 2);

        Label lastNameLabel = new Label("Last Name: ");

        grid.add(lastNameLabel, 0, 3);
        grid.add(lastNameField, 1, 3);

        Separator separator = new Separator();
        grid.add(separator, 0, 4, 2, 1);
    }

    private void setupAccountDetailControls() {
        Text accountDetailsTitle = new Text("Account Details");
        accountDetailsTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        grid.add(accountDetailsTitle, 0, 5, 2, 1);
        Label usernameLabel = new Label("Username: ");

        grid.add(usernameLabel, 0, 6);
        grid.add(usernameField, 1, 6);

        Label passwordLabel = new Label("Password: ");

        grid.add(passwordLabel, 0, 7);
        grid.add(passwordField, 1, 7);

        Label confirmPasswordLabel = new Label("Confirm Password: ");

        grid.add(confirmPasswordLabel, 0, 8);
        grid.add(confirmPasswordField, 1, 8);

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

        grid.add(vBox, 0, 9, 2, 1);
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
