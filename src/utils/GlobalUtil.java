package utils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import exceptions.CustomDateTimeParseException;
import exceptions.InvalidFormSubmissionException;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class GlobalUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static Boolean validateFormControls(Object[] params) throws InvalidFormSubmissionException {
        for (Object param : params) {
            if (param == null || param instanceof String && ((String) param).trim().isEmpty()) {
                throw new InvalidFormSubmissionException(
                        String.format("[Error] All fields must be filled out."));
            }
        }
        return false;
    }

    public static String validateDateControl(String dateString) throws CustomDateTimeParseException {
        try {
            LocalDateTime.parse(dateString, DATE_FORMATTER);
            return dateString;
        } catch (DateTimeParseException e) {
            throw new CustomDateTimeParseException(
                    String.format("[Error] Invalid date format. Format: %s", "dd/MM/yyyy HH:mm"));
        }
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
