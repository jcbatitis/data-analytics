package utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public final class GridUtil {

    public static GridPane setupGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        return grid;
    }

    public static GridPane setupGrid(Boolean centered) {
        GridPane grid = new GridPane();

        if (centered) {
            grid.setAlignment(Pos.CENTER);
        }

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        return grid;

    }
}
