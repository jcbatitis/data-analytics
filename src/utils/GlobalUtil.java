package utils;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class GlobalUtil {
    public static boolean hasEmptyField(Object[] params) {
        for (Object param : params) {
            if (param == null) {
                return true;
            }
            if (param instanceof String && ((String) param).trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static Font getHeaderFont() {
        return Font.font("Calibri", FontWeight.BOLD, 20);
    }

    public static Font getSubHeaderFont() {
        return Font.font("Calibri", FontWeight.BOLD, 16);
    }

    public static ChangeListener<String> numericHandler(TextField textField) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
    }
}
