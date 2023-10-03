package views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Post;
import utils.GridUtil;
import utils.StageUtil;

public class DashboardView extends Stage {

    public GridPane grid;
    private Scene scene;

    public Text sceneTitle = new Text();

    public Button addButton;
    public Button deleteButton;
    public Button refreshButton;

    public TextField searchField;
    public ToggleGroup searchToggleGroup;
    public Button searchButton;
    public Button searchClearButton;

    public TableView<Post> table;
    public ObservableList<Post> posts = FXCollections.observableArrayList();;

    public MenuItem editProfile = new MenuItem("Edit Profile");
    public MenuItem subscribe = new MenuItem("Subscribe to VIP program");

    public Post selectedPost;

    TableColumn<Post, Void> selectColumn = new TableColumn<>("Select");

    public DashboardView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        refreshButton = new Button("Refresh");
        addButton = new Button("Add New");
        deleteButton = new Button("Delete Selected Posts");
        searchField = new TextField();

        this.setupDefaults();
    }

    private void setupDefaults() {
        BorderPane root = new BorderPane();

        StageUtil.centerStage(this);
        Menu profileMenu = new Menu("Profile");

        profileMenu.getItems().addAll(editProfile, subscribe);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(profileMenu);

        grid = GridUtil.setupCenteredGrid();

        root.setTop(menuBar);
        root.setCenter(grid);

        scene = new Scene(root, 700, 500);
        this.setScene(scene);

        setupHeader();
        setupSearchSection();
        setupButtons();
        setupPostsTable();
    }

    private void setupHeader() {
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0);
    }

    public void setupPosts(ObservableList<Post> posts) {
        this.posts = posts;
    }

    public void setupButtons() {
        HBox hBox = new HBox(10);

        hBox.getChildren().addAll(searchField, addButton, refreshButton, deleteButton);
        grid.add(hBox, 0, 2, 3, 1);

        Separator separator = new Separator();
        grid.add(separator, 0, 3, 3, 1);
    }

    public void setupSearchSection() {
        GridPane searchGrid = GridUtil.setupNoPaddingGrid();
        searchToggleGroup = new ToggleGroup();

        // Header
        Text searchTitle = new Text("Search");
        searchTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        searchGrid.add(searchTitle, 0, 0, 2, 1);

        // Radio Button
        RadioButton searchByTerm = new RadioButton("Term");
        RadioButton searchByPostId = new RadioButton("Post ID");

        Label searchToggleGroupLabel = new Label("Search by:");
        searchByTerm.setSelected(true);

        searchByTerm.setToggleGroup(searchToggleGroup);
        searchByTerm.setUserData("term");
        searchByPostId.setToggleGroup(searchToggleGroup);
        searchByPostId.setUserData("postId");

        searchButton = new Button("Search");
        searchClearButton = new Button("Clear");

        HBox hBox = new HBox(10, searchByTerm, searchByPostId);
        searchGrid.add(searchToggleGroupLabel, 0, 1);
        searchGrid.add(hBox, 1, 1);

        grid.add(searchGrid, 0, 1);
    }

    public void setupPostsTable() {
        table = new TableView<Post>();

        TableColumn<Post, String> id = new TableColumn<>("Post ID");
        TableColumn<Post, String> content = new TableColumn<>("Content");
        TableColumn<Post, String> author = new TableColumn<>("Author");
        TableColumn<Post, Integer> likes = new TableColumn<>("Likes");
        TableColumn<Post, Integer> shares = new TableColumn<>("Shares");
        TableColumn<Post, String> dateTime = new TableColumn<>("Date");
        TableColumn<Post, String> userId = new TableColumn<>("Added By (User Id)");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        likes.setCellValueFactory(new PropertyValueFactory<>("likes"));
        shares.setCellValueFactory(new PropertyValueFactory<>("shares"));
        dateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        table.setEditable(true);

        List<TableColumn<Post, ?>> columns = Arrays.asList(selectColumn, id, content, author, likes, shares, dateTime,
                userId);

        table.getColumns().setAll(columns);

        table.setItems(this.posts);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(table);

        grid.add(layout, 0, 4, 2, 1);
    }

    public void setSelectButtonAction(Consumer<Post> action) {
        selectColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Select");

            {
                btn.setOnAction(event -> {
                    Post post = getTableView().getItems().get(getIndex());
                    action.accept(post);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }
}

// Refresh
// try {
// // refreshButton.setPrefSize(50, 50);
// // FileInputStream input = new
// // FileInputStream("src/resources/images/refresh.png");

// // Image image = new Image(input);
// // ImageView imageView = new ImageView(image);
// // imageView.setPreserveRatio(true);

// // // imageView.fitWidthProperty().bind(refreshButton.widthProperty());
// // // imageView.fitHeightProperty().bind(refreshButton.heightProperty());
// // refreshButton.setGraphic(imageView);

// } catch (FileNotFoundException e) {
// e.printStackTrace();
// }