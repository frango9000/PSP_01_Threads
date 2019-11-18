package thread_ascensor.ui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import thread_ascensor.Pasajero;

public class UINivelesControl {

    private int numeroDeNiveles = 0;
    private UINivelControl[] nivelControles;

    private VBox root = new VBox();

    private ToggleGroup radios = new ToggleGroup();

    public UINivelesControl(int numeroDeNiveles) {
        this.numeroDeNiveles = numeroDeNiveles;
        nivelControles       = new UINivelControl[numeroDeNiveles + 1];
        root.setPadding(new Insets(5));

        ArrayList<Pane> panes = new ArrayList<>();
        for (int i = 0; i <= numeroDeNiveles; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Nivel.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            panes.add(pane);
            UINivelControl control = loader.getController();
            control.setNivel(i);
            control.getRadioNivel().setToggleGroup(radios);
            control.setNivelCantidad(0);
            nivelControles[i] = control;
        }
        for (int i = numeroDeNiveles; i >= 0; i--) {
            root.getChildren().add(panes.get(i));
        }
    }

    public VBox getPane() {
        return root;
    }

    public void activateRadio(int nivel) {
        nivelControles[nivel].getRadioNivel().setSelected(true);
    }

    public void setNivelCantidad(int nivel, int cantidad) {
        nivelControles[nivel].setNivelCantidad(cantidad);
    }

    public ObservableList<Pasajero> getPasajerosNivelIn(int nivel) {
        return nivelControles[nivel].getPasajerosNivelIn();
    }

    public void addPasajeroNivelIn(int nivel, Pasajero pasajero) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getPasajerosNivelIn(nivel).add(pasajero);
            }
        });
    }

    public void addPasajeroNivelOut(int nivel, Pasajero pasajero) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getPasajerosNivelOut(nivel).add(pasajero);
            }
        });
    }

    public void removePasajeroNivelIn(int nivel, Pasajero pasajero) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getPasajerosNivelIn(nivel).remove(pasajero);
            }
        });
    }

    public void removePasajeroNivelOut(int nivel, Pasajero pasajero) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getPasajerosNivelOut(nivel).remove(pasajero);
            }
        });
    }

    public ObservableList<Pasajero> getPasajerosNivelOut(int nivel) {
        return nivelControles[nivel].getPasajerosNivelOut();
    }


    public UINivelControl getNivelControl(int nivel) {
        return nivelControles[nivel];
    }
}
