package main.views;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.models.Post;
import main.utils.GridUtil;
import main.utils.StyleUtil;

public class DashboardView {

    // Layout
    private Scene scene;
    private VBox parentVBox = new VBox(10);

    private VBox userDetailVBox = new VBox(10);
    private VBox searchVbox = new VBox(10);
    private VBox sortVBox = new VBox(10);
    private VBox vipVBox = new VBox(10);

    private GridPane controlGrid = new GridPane();

    private VBox postTableBox = new VBox(10);

    private final BorderPane root = new BorderPane();

    private final Text title = new Text();
    private final Text header = new Text();

    // Table Controls
    private final Button addButton = new Button("Add New");
    private final Button refreshButton = new Button("Refresh");

    // VIP Controls
    private final Button dataVisualisationButton = new Button("Reports");
    private final Button exportButton = new Button("Export");
    private final Button importButton = new Button("Import");
    private final Button subscribeButton = new Button("Subscribe");

    // Table Query Controls
    private final ToggleGroup searchToggleGroup = new ToggleGroup();
    private final TextField searchField = new TextField();
    private final Button searchSubmitButton = new Button("Search");
    private final CheckBox toggleSearchSection = new CheckBox();

    private final ToggleGroup sortToggleGroup = new ToggleGroup();
    private final TextField sortField = new TextField();
    private final Button sortSubmitButton = new Button("Sort");
    private final CheckBox toggleSortSection = new CheckBox();

    // Menu and Profile Menu Items
    private final MenuBar menuBar = new MenuBar();
    private final Menu profileMenu = new Menu("Profile");
    private final MenuItem editProfile = new MenuItem("Edit Profile");
    private final MenuItem subscribe = new MenuItem("Subscriptions");
    private final MenuItem logout = new MenuItem("Log out");

    // Table and Action Column
    private final TableView<Post> table = new TableView<Post>();;
    private final TableColumn<Post, Void> selectColumn = new TableColumn<>("Action");

    private final Text validationMessage = new Text();

    /**
     * INITIALISES VIEW
     */
    public DashboardView() {
        setupLayout();
    }

    /**
     * SETUP THE LAYOUT FOR THE VIEW
     */
    private void setupLayout() {
        root.setTop(menuBar);
        root.setCenter(parentVBox);

        parentVBox.setPadding(new Insets(25, 25, 25, 25));

        setupMenu();
        setupHeader();
        setupSearchSection();
        setupSortSection();
        setupUserDetailSection();
        setupPostsTable();

        scene = new Scene(root, 950, 800);
        scene.getStylesheets().add(getClass().getResource("/main/resources/style.css").toExternalForm());

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column2.setPercentWidth(50);

        controlGrid.getColumnConstraints().addAll(column1, column2);

        HBox.setHgrow(sortVBox, Priority.ALWAYS);
        HBox.setHgrow(vipVBox, Priority.ALWAYS);

        controlGrid.add(userDetailVBox, 0, 0);
        controlGrid.add(searchVbox, 1, 0);
        controlGrid.add(sortVBox, 0, 1);
        controlGrid.add(vipVBox, 1, 1);

        controlGrid.setHgap(10);
        controlGrid.setVgap(10);

        parentVBox.getChildren().addAll(postTableBox, controlGrid);
        setupStyleClasses();

    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupMenu() {
        profileMenu.getItems().addAll(editProfile, subscribe, logout);
        menuBar.getMenus().addAll(profileMenu);
    }

    private void setupHeader() {
        header.setFont(StyleUtil.getHeaderFont());
    }

    private void setupSortSection() {
        // Header
        Text sortTitle = new Text("FILTER");
        HBox sortHeader = new HBox(5, toggleSortSection, sortTitle);
        sortTitle.setFont(StyleUtil.getSubHeaderFont());

        // Description
        String description = "To sort, select an option below then enter the number of entries.";
        Label filterDescription = new Label(description);
        filterDescription.setWrapText(true);
        HBox descriptionBox = new HBox(5, filterDescription);

        // Toggle Buttons
        GridPane sortToggleGrid = GridUtil.setupNoPaddingGrid();
        GridPane sortControlGrid = GridUtil.setupNoPaddingGrid();

        Label sortToggleGroupLabel = new Label("Sort by:");
        Label entryLabel = new Label("Entries:");

        RadioButton sortByLikes = new RadioButton("Likes");
        RadioButton sortByPosts = new RadioButton("Shares");
        HBox sortOptionsBox = new HBox(10, sortByLikes, sortByPosts);

        toggleSortSection.setSelected(false);
        sortByLikes.setSelected(true);
        sortByLikes.setToggleGroup(sortToggleGroup);
        sortByLikes.setUserData("likes");
        sortByPosts.setToggleGroup(sortToggleGroup);
        sortByPosts.setUserData("shares");

        HBox sortControl = new HBox(5, sortField);
        sortField.setMinWidth(180);
        sortToggleGrid.add(sortToggleGroupLabel, 0, 0);
        sortToggleGrid.add(sortOptionsBox, 1, 0);

        sortControlGrid.add(entryLabel, 0, 0);
        sortControlGrid.add(sortControl, 1, 0);

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(15);
        column2.setPercentWidth(85);

        sortToggleGrid.getColumnConstraints().addAll(column1, column2);
        sortControlGrid.getColumnConstraints().addAll(column1, column2);

        sortSubmitButton.setMinWidth(80);

        HBox tableControlsRow1 = new HBox(5, sortSubmitButton);
        tableControlsRow1.setAlignment(Pos.CENTER_RIGHT);

        sortVBox.getChildren().addAll(sortHeader, descriptionBox, sortToggleGrid, sortControlGrid, tableControlsRow1);
        sortVBox.getStyleClass().add("sort-container");
    }

    private void setupSearchSection() {
        // Header
        Text tableTitle = new Text("TABLE");
        HBox tableHeader = new HBox(5, tableTitle);
        tableTitle.setFont(StyleUtil.getSubHeaderFont());

        // Description
        String description = "To search, just type in a term and it will give you its results.";
        Label tableDescription = new Label(description);
        tableDescription.setWrapText(true);
        HBox descriptionBox = new HBox(5, tableDescription);
        Label searchLabel = new Label("Search: ");

        GridPane searchControlGrid = GridUtil.setupNoPaddingGrid();

        searchField.setMinWidth(225);
        searchControlGrid.add(searchLabel, 0, 0);
        searchControlGrid.add(searchField, 1, 0);

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(15);
        column2.setPercentWidth(85);

        searchControlGrid.getColumnConstraints().addAll(column1, column2);

        HBox tableControlsRow1 = new HBox(5, addButton, refreshButton);
        tableControlsRow1.setAlignment(Pos.CENTER_RIGHT);

        searchVbox.getChildren().addAll(tableHeader, descriptionBox, searchControlGrid, tableControlsRow1);
        searchVbox.getStyleClass().add("sort-container");
    }

    private void setupUserDetailSection() {
        // Description
        String description = "Hi there, welcome to dashboard analytics hub!\nAdvaced Further Programming: Assignment #2\nStudent ID: S3856624\nJejomar Batitis";
        Label detailDescription = new Label(description);
        detailDescription.setWrapText(true);
        HBox descriptionBox = new HBox(5, detailDescription);

        userDetailVBox.getChildren().addAll(header, descriptionBox);
        userDetailVBox.getStyleClass().add("user-container");
    }

    public void setupVipControlSection(Boolean enabled) {
        Text vipControlsTitle = new Text("MANAGEMENT");
        vipControlsTitle.setFont(StyleUtil.getSubHeaderFont());

        String description = !enabled
                ? "To gain access to import/export tools, and the comprehensive report viewer. Subscribe to our VIP subscription."
                : "You can import and export data seamlessly, as well as view comprehensive reports for analysis.";
        Label vipDescription = new Label(description);
        vipDescription.setWrapText(true);

        importButton.setMinWidth(40);
        exportButton.setMinWidth(40);
        dataVisualisationButton.setMinWidth(40);

        HBox tableControlsRow1 = new HBox(5, vipControlsTitle);
        HBox tableControlsRow2 = new HBox(5, vipDescription);
        HBox tableControlsRow3 = new HBox(5, importButton, exportButton, dataVisualisationButton);
        HBox tableControlsRow4 = new HBox(5, subscribeButton);

        tableControlsRow3.setAlignment(Pos.CENTER_RIGHT);
        tableControlsRow4.setAlignment(Pos.CENTER_RIGHT);

        if (enabled) {
            vipVBox.getChildren().addAll(tableControlsRow1, tableControlsRow2, tableControlsRow3);
        } else {
            vipVBox.getChildren().addAll(tableControlsRow1, tableControlsRow2, tableControlsRow4);
        }
        vipVBox.getStyleClass().add("management-container");
    }

    private void setupPostsTable() {
        Text tableControlsTitle = new Text("DATA ANALYTICS HUB");
        tableControlsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TableColumn<Post, String> id = new TableColumn<>("Post ID");
        TableColumn<Post, String> content = new TableColumn<>("Content");
        TableColumn<Post, String> author = new TableColumn<>("Author");
        TableColumn<Post, Integer> likes = new TableColumn<>("Likes");
        TableColumn<Post, Integer> shares = new TableColumn<>("Shares");
        TableColumn<Post, String> dateTime = new TableColumn<>("Date");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        likes.setCellValueFactory(new PropertyValueFactory<>("likes"));
        shares.setCellValueFactory(new PropertyValueFactory<>("shares"));
        dateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        table.setEditable(true);

        List<TableColumn<Post, ?>> columns = Arrays.asList(selectColumn, id, content,
                author, likes, shares, dateTime);

        table.getColumns().setAll(columns);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        id.prefWidthProperty().bind(table.widthProperty().divide(7));
        content.prefWidthProperty().bind(table.widthProperty().divide(7));
        author.prefWidthProperty().bind(table.widthProperty().divide(7));
        likes.prefWidthProperty().bind(table.widthProperty().divide(7));
        shares.prefWidthProperty().bind(table.widthProperty().divide(7));
        dateTime.prefWidthProperty().bind(table.widthProperty().divide(7));

        for (TableColumn<Post, ?> column : columns) {
            column.setResizable(false);
        }

        VBox tableBox = new VBox(10);
        tableBox.getChildren().addAll(table);

        addButton.setMinWidth(120);
        refreshButton.setMinWidth(120);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        Text postTitle = new Text("DASHBOARD");
        postTitle.setFont(StyleUtil.getSubHeaderFont());

        HBox tableControls = new HBox(5, postTitle, region, validationMessage);
        tableControls.setAlignment((Pos.CENTER_RIGHT));

        validationMessage.getStyleClass().add("error-message");
        postTableBox.getChildren().addAll(tableControls, tableBox);
    }

    /**
     * SET UPS THE SELECT BUTTON FOR TABLE ROW
     */
    public void setupSelectButtonEventHandler(EventHandler<ActionEvent> event) {
        selectColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Select");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Post currentPost = getTableView().getItems().get(getIndex());
                    btn.setUserData(currentPost);
                    btn.setOnAction(event);
                    setGraphic(btn);
                }
            }
        });
    }

    private void setupStyleClasses() {
        scene.getStylesheets().add(getClass().getResource("/main/resources/style.css").toExternalForm());

        StyleUtil.setStyleClass(validationMessage, "error-message");
        StyleUtil.setupButtonIcon(refreshButton, "/main/resources/images/refresh.png");
        StyleUtil.setupButtonIcon(addButton, "/main/resources/images/plus.png");
        StyleUtil.setupButtonIcon(sortSubmitButton, "/main/resources/images/sort.png");
        StyleUtil.setupButtonIcon(exportButton, "/main/resources/images/download.png");
        StyleUtil.setupButtonIcon(importButton, "/main/resources/images/import.png");
        StyleUtil.setupButtonIcon(dataVisualisationButton, "/main/resources/images/chart.png");
        StyleUtil.setupButtonIcon(subscribeButton, "/main/resources/images/subscribe.png");

        Button[] buttons = new Button[] { refreshButton, addButton, sortSubmitButton, exportButton, importButton,
                dataVisualisationButton, subscribeButton };

        StyleUtil.setStyleClasses(buttons, "action-button");
        StyleUtil.setMinWidthAndAlignment(100, Pos.CENTER, buttons);
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

    public String getSearchToggleGroupValue() {
        return searchToggleGroup.getSelectedToggle().getUserData().toString();
    }

    public String getSearchTerm() {
        return searchField.getText();
    }

    public TextField getSearchField() {
        return searchField;
    }

    public TextField getSortField() {
        return sortField;
    }

    public CheckBox getToggleSearchSection() {
        return toggleSearchSection;
    }

    public Button getRefreshButton() {
        return refreshButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getSortButton() {
        return sortSubmitButton;
    }

    public Button getSearchButton() {
        return searchSubmitButton;
    }

    public Button getDataVisualisationButton() {
        return dataVisualisationButton;
    }

    public Button getExportButton() {
        return exportButton;
    }

    public Button getImportButton() {
        return importButton;
    }

    public Button getSubscribeButton() {
        return subscribeButton;
    }

    public MenuItem getEditProfileMenu() {
        return editProfile;
    }

    public MenuItem getLogoutMenu() {
        return logout;
    }

    public MenuItem getSubscriptionMenu() {
        return subscribe;
    }

    public String getSortToggleGroupValue() {
        return sortToggleGroup.getSelectedToggle().getUserData().toString();
    }

    public CheckBox getToggleSortSection() {
        return toggleSortSection;
    }

    public Integer getSortValue() {
        try {
            return Integer.parseInt(sortField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public ToggleGroup getSearchToggleGroup() {
        return searchToggleGroup;
    }

    public ToggleGroup getSortToggleGroup() {
        return sortToggleGroup;
    }

    /*
     * SETTERS
     */
    public void setTableItems(ObservableList<Post> posts) {
        table.setItems(posts);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setHeader(String header) {
        this.header.setText(header);
    }

    public void setValidationMessage(String message) {
        this.validationMessage.setText(message);
    }

    public void toggleValidationMessageClass(Boolean isSuccess) {
        Boolean isErrorMessage = isSuccess ? false : true;

        if (isErrorMessage) {
            validationMessage.getStyleClass().remove("success-message");
            validationMessage.getStyleClass().add("error-message");

        } else {
            validationMessage.getStyleClass().remove("error-message");
            validationMessage.getStyleClass().add("success-message");
        }
    }
}