package main.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import main.exceptions.CustomDateTimeParseException;
import main.exceptions.InvalidFormSubmissionException;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

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

    public static ChangeListener<String> validateIntegers(TextField textField) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
    }
}
