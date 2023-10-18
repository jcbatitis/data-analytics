package utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class StyleUtil {
    public static void setupButtonIcon(Button button, String iconPath) {
        Image icon = new Image(iconPath);
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(15);
        iconView.setFitWidth(15);
        button.setGraphic(iconView);
    }

    public static void setLabelStyleClass(Label... labels) {
        for (Label label : labels) {
            label.getStyleClass().setAll("label");
        }
    }

    public static void setTextFieldStyleClass(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.getStyleClass().setAll("text-field");
        }
    }

    public static void setStyleClass(Node node, String... styleClasses) {
        node.getStyleClass().setAll(styleClasses);
    }

    public static void setStyleClasses(Node[] nodes, String styleClasses) {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].getStyleClass().setAll(styleClasses);
        }
    }

    public static void setStyleClasses(Node[] nodes, String[] styleClasses) {
        if (nodes.length != styleClasses.length) {
            throw new IllegalArgumentException("Nodes and style classes arrays must have the same length");
        }
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].getStyleClass().setAll(styleClasses[i]);
        }
    }

    public static void setMinWidthAndAlignment(double minWidth, Pos alignment, Button... buttons) {
        for (Button button : buttons) {
            button.setMinWidth(minWidth);
            button.setAlignment(alignment);
        }
    }
}
