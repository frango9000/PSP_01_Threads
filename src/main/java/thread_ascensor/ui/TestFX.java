package thread_ascensor.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestFX extends Application {

    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);

    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        TestFX.mainStage = mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        mainStage = primaryStage;
        UINivelesControl nivelesControl = new UINivelesControl(15);

//        nivelesControl.activateRadio(5);
//        nivelesControl.getPasajerosNivel(10).add(new Pasajero(null, 'a', 10, 5));
//        nivelesControl.getPasajerosNivel(10).add(new Pasajero(null, 'a', 10, 3));
//        nivelesControl.getPasajerosNivel(10).add(new Pasajero(null, 'a', 10, 10));

        primaryStage.setScene(new Scene(nivelesControl.getPane()));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));

    }
}
