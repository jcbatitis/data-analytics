package views;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import utils.GridUtil;

public class DashboardView {

    // Layout
    private GridPane grid;
    private Scene scene;

    private final BorderPane root = new BorderPane();
    private final Text title = new Text();
    private final Text header = new Text();
    private final Button clearButton = new Button("Clear");
    private final Button addButton = new Button("Add New");
    private final Button refreshButton = new Button("Refresh");
    private final Button dataVisualisationButton = new Button("Data Visualisation");
    private final Button exportButton = new Button("Export");
    private final Button importButton = new Button("Import");

    private final Button searchSubmitButton = new Button("Search");
    private final Button sortSubmitButton = new Button("Sort");
    private final MenuItem editProfile = new MenuItem("Edit Profile");
    private final MenuItem subscribe = new MenuItem("Subscriptions");

    private final MenuItem logout = new MenuItem("Log out");

    private final MenuBar menuBar = new MenuBar();
    private final Menu profileMenu = new Menu("Profile");

    private final TextField searchField = new TextField();
    private final TextField sortField = new TextField();
    private final ToggleGroup searchToggleGroup = new ToggleGroup();
    private final ToggleGroup sortToggleGroup = new ToggleGroup();

    private final TableView<Post> table = new TableView<Post>();;
    private final TableColumn<Post, Void> selectColumn = new TableColumn<>("Select");

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
        setupTableControlsSection();
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
        header.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(header, 0, 0, 11, 1);
    }

    public void setupSearchSection() {
        GridPane searchGrid = GridUtil.setupNoPaddingGrid();

        Text searchTitle = new Text("Search");
        searchTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        searchGrid.add(searchTitle, 0, 0, 2, 1);

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
        VBox vbox = new VBox(10, searchGrid, searchControl, new Separator());

        grid.add(vbox, 0, 1);
    }

    public void setupSortSection() {
        GridPane sortGrid = GridUtil.setupNoPaddingGrid();

        Text sortTitle = new Text("Sort");
        sortTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        sortGrid.add(sortTitle, 0, 0, 2, 1);

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
        VBox vbox = new VBox(10, sortGrid, sortControl, new Separator());

        grid.add(vbox, 0, 2);
    }

    public void setupTableControlsSection() {
        GridPane tableControlsGrid = GridUtil.setupNoPaddingGrid();

        Text tableControlsTitle = new Text("Controls");
        tableControlsTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        tableControlsGrid.add(tableControlsTitle, 0, 0, 2, 1);

        addButton.setMinWidth(120);
        refreshButton.setMinWidth(120);
        dataVisualisationButton.setMinWidth(120);
        exportButton.setMinWidth(120);
        importButton.setMinWidth(120);

        HBox tableControlsRow1 = new HBox(5, addButton, refreshButton);
        HBox tableControlsRow2 = new HBox(5, dataVisualisationButton, exportButton);
        HBox tableControlsRow3 = new HBox(5, importButton);

        VBox vbox = new VBox(10, tableControlsGrid, tableControlsRow1, tableControlsRow2, tableControlsRow3,
                new Separator());

        grid.add(vbox, 0, 3);
    }

    public void setupPostsTable() {

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

        VBox layout = new VBox(10);
        layout.getChildren().addAll(table);

        grid.add(layout, 2, 1, 10, 4);
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

    public String getSearchToggleGroup() {
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

    public String getSortToggleGroup() {
        return sortToggleGroup.getSelectedToggle().getUserData().toString();
    }

    public Integer getSortValue() {
        try {
            return Integer.parseInt(sortField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
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
}