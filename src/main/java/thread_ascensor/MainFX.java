package thread_ascensor;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFX extends Application {


    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);

    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        MainFX.mainStage = mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        mainStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AscensorUI.fxml"));
        BorderPane root = loader.load();
        UIControl control = loader.getController();

        AscensorController ascensorController = new AscensorController();
        ascensorController.setDisplay(control.getTxtarea());

        control.setAscensorController(ascensorController);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));

    }
}
