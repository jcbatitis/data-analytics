package views;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Post;
import utils.GlobalUtil;
import utils.GridUtil;

public class DashboardView {

    // Layout
    private GridPane grid;
    private Scene scene;

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
        grid = GridUtil.setupGrid();

        root.setTop(menuBar);
        root.setCenter(grid);

        setupMenu();
        setupHeader();
        setupSearchSection();
        setupSortSection();
        setupPostsTable();

        scene = new Scene(root, 900, 500);
    }

    /**
     * SETUP THE UI CONTROLS
     */
    private void setupMenu() {
        profileMenu.getItems().addAll(editProfile, subscribe, logout);
        menuBar.getMenus().addAll(profileMenu);
    }

    private void setupHeader() {
        header.setFont(GlobalUtil.getHeaderFont());
        grid.add(header, 0, 0, 11, 1);
    }

    private void setupSearchSection() {
        GridPane searchGrid = GridUtil.setupNoPaddingGrid();

        // Header
        Text searchTitle = new Text("Search");
        HBox searchHeader = new HBox(5, toggleSearchSection, searchTitle);

        searchTitle.setFont(GlobalUtil.getSubHeaderFont());
        searchGrid.add(searchHeader, 0, 0, 2, 1);

        toggleSearchSection.setSelected(false);

        // Toggle Group
        RadioButton searchByTerm = new RadioButton("Term");
        RadioButton searchByPostId = new RadioButton("Post ID");

        Label searchToggleGroupLabel = new Label("Search by:");
        searchByTerm.setSelected(true);

        searchByTerm.setToggleGroup(searchToggleGroup);
        searchByTerm.setUserData("term");
        searchByPostId.setToggleGroup(searchToggleGroup);
        searchByPostId.setUserData("postId");

        HBox hBox = new HBox(10, searchByTerm, searchByPostId);
        searchGrid.add(searchToggleGroupLabel, 0, 1);
        searchGrid.add(hBox, 1, 1);

        searchSubmitButton.setMinWidth(80);

        HBox searchControl = new HBox(5, searchField, searchSubmitButton);
        VBox vbox = new VBox(10, searchGrid, searchControl);

        grid.add(vbox, 0, 1);
    }

    private void setupSortSection() {
        GridPane sortGrid = GridUtil.setupNoPaddingGrid();

        Text sortTitle = new Text("Sort");
        HBox sortHeader = new HBox(5, toggleSortSection, sortTitle);

        sortTitle.setFont(GlobalUtil.getSubHeaderFont());
        sortGrid.add(sortHeader, 0, 0, 2, 1);

        toggleSortSection.setSelected(false);

        RadioButton sortByLikes = new RadioButton("Likes");
        RadioButton sortByPosts = new RadioButton("Shares");

        Label sortToggleGroupLabel = new Label("Sort by:");
        sortByLikes.setSelected(true);

        sortByLikes.setToggleGroup(sortToggleGroup);
        sortByLikes.setUserData("likes");
        sortByPosts.setToggleGroup(sortToggleGroup);
        sortByPosts.setUserData("shares");

        sortGrid.add(sortToggleGroupLabel, 0, 1);
        HBox hBox = new HBox(10, sortByLikes, sortByPosts);
        sortGrid.add(hBox, 1, 1);

        sortSubmitButton.setMinWidth(80);

        HBox sortControl = new HBox(5, sortField, sortSubmitButton);
        VBox vbox = new VBox(10, new Separator(), sortGrid, sortControl);

        grid.add(vbox, 0, 2);
    }

    public void setupVipControlSection() {
        GridPane vipControlsGrid = GridUtil.setupNoPaddingGrid();

        Text vipControlsTitle = new Text("VIP Controls");
        vipControlsTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        vipControlsGrid.add(vipControlsTitle, 0, 0);

        addButton.setMinWidth(120);
        refreshButton.setMinWidth(120);
        dataVisualisationButton.setMinWidth(120);

        HBox tableControlsRow1 = new HBox(5, importButton, exportButton);
        HBox tableControlsRow2 = new HBox(5, dataVisualisationButton);

        VBox vbox = new VBox(10, new Separator(), vipControlsGrid, tableControlsRow1, tableControlsRow2);

        grid.add(vbox, 0, 3);
    }

    private void setupPostsTable() {
        GridPane tableControlsGrid = GridUtil.setupNoPaddingGrid();

        Text tableControlsTitle = new Text("DATA ANALYTICS HUB");
        tableControlsTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        tableControlsGrid.add(tableControlsTitle, 0, 0, 2, 1);

        HBox tableControls = new HBox(5, addButton, refreshButton);

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

        VBox tableBox = new VBox(10);
        tableBox.getChildren().addAll(table);

        exportButton.setMinWidth(120);
        importButton.setMinWidth(120);

        HBox validationBox = new HBox(10);
        validationBox.setAlignment(Pos.CENTER_RIGHT);
        validationBox.getChildren().add(validationMessage);

        VBox vbox = new VBox(10, tableControls, tableBox, validationBox);

        grid.add(vbox, 2, 1, 10, 4);
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
}