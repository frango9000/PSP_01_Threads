package thread_ascensor.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;


public class FxDialogs {

    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String OK = "OK";
    public static final String CANCEL = "Cancel";

    public static void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showMessage(String message) {
        showMessage("Info", message);
    }

    public static void showInfo(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showInfo(String header, String message) {
        showInfo("Info", header, message);
    }

    public static void showWarning(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showWarning(String header, String message) {
        showWarning("Warning", header, message);
    }

    public static void showError(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showError(String header, String message) {
        showWarning("Error", header, message);
    }

    public static void showException(String title, String header, String message, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Details:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static void showException(String header, String message, Exception exception) {
        showException("Exception", header, message, exception);
    }


    public static String showTextInput(String title, String header, String message, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    public static String showTextInput(String header, String message, String defaultValue) {
        return showTextInput("Input", header, message, defaultValue);
    }

    public static String showTextInput(String header, String message) {
        return showTextInput("Input", header, message, "");
    }

    public static <T> T showChoices(String title, String header, String message, T defaultChoice,
                                    Collection<T> choices) {
        ChoiceDialog<T> dialog = new ChoiceDialog<>(defaultChoice, choices);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(message);

        Optional<T> result = dialog.showAndWait();
        if (!result.isPresent()) {
            return null;
        } else {
            return result.get();
        }
    }

    public static <T> T showChoices(String header, String message, T defaultChoice, Collection<T> choices) {
        return showChoices("Chooose", header, message, defaultChoice, choices);
    }

    @SafeVarargs
    public static <T> T showChoices(String title, String header, String message, T defaultChoice, T... choices) {
        return showChoices(title, header, message, defaultChoice, Arrays.asList(choices));
    }

    @SafeVarargs
    public static <T> T showChoices(String header, String message, T defaultChoice, T... choices) {
        return showChoices("Chooose", header, message, defaultChoice, Arrays.asList(choices));
    }
}