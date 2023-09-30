package views;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Post;

public class PostView extends Stage {

    private TableView<Post> table;
    private ObservableList<Post> posts;

    public PostView() {
        this.setTitle("Data Analytics Hub");
        this.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        this.setX((primScreenBounds.getWidth() - this.getWidth()) / 2);
        this.setY((primScreenBounds.getHeight() - this.getHeight()) / 2);

        table = new TableView<Post>();
        TableColumn<Post, Integer> id = new TableColumn<>("ID");
        TableColumn<Post, String> content = new TableColumn<>("Content");
        TableColumn<Post, String> author = new TableColumn<>("Author");
        TableColumn<Post, Integer> likes = new TableColumn<>("Likes");
        TableColumn<Post, Integer> shares = new TableColumn<>("Shares");
        TableColumn<Post, String> date = new TableColumn<>("Date");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        likes.setCellValueFactory(new PropertyValueFactory<>("likes"));
        shares.setCellValueFactory(new PropertyValueFactory<>("shares"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        List<TableColumn<Post, ?>> columns = Arrays.asList(id, content, author, likes, shares, date);
        table.getColumns().setAll(columns);
        table.setItems(posts);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(table);

        Scene scene = new Scene(layout, 600, 400);
        this.setScene(scene);

    }

    public void setPosts(ObservableList<Post> posts) {
        this.posts = posts;
        table.setItems(this.posts);
    }
}