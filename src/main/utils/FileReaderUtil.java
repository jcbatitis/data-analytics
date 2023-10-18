package main.utils;

import java.io.File;

import main.exceptions.FilePathRequiredException;
import javafx.stage.FileChooser;

public final class FileReaderUtil {

    public static File readFile() throws FilePathRequiredException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Post");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile == null) {
            throw new FilePathRequiredException(
                    String.format("[Warning] No Import File selected"));
        }

        return selectedFile;
    }

    public static File getDirectoryForSaving() throws FilePathRequiredException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Post");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("post.csv");

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile == null) {
            throw new FilePathRequiredException(
                    String.format("[Warning] No Export Directory selected"));
        }

        return selectedFile;
    }
}
