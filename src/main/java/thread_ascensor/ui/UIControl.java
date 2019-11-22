package thread_ascensor.ui;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import thread_ascensor.Ascensor;
import thread_ascensor.AscensorController;
import thread_ascensor.Pasajero;

public class UIControl {


    @FXML
    public Button loopGo;
    @FXML
    public TextField loopNum;
    SimpleStringProperty propAscNombre = new SimpleStringProperty();
    SimpleIntegerProperty propAscNivel = new SimpleIntegerProperty(0);
    SimpleStringProperty propAscDireccion = new SimpleStringProperty();
    SimpleIntegerProperty propAscCantidadPasajeros = new SimpleIntegerProperty();
    UINivelesControl nivelesControl;
    AscensorController ascensorController;
    private ObservableList<Pasajero> pasajerosEnAscensor0 = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label lbl;
    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbl1;
    @FXML
    private TextField tfOrigen;
    @FXML
    private Label lbl11;
    @FXML
    private TextField tfDestino;
    @FXML
    private Button btn;
    @FXML
    private TextArea txtarea;
    @FXML
    private VBox mainPane;
    @FXML
    private GridPane gridAscensores;
    @FXML
    private Label numAscensor;
    @FXML
    private Label nombreAscensor;
    @FXML
    private Label nivelActual;
    @FXML
    private Label numPasajeros;
    @FXML
    private ListView<Pasajero> listViewAscensor;
    @FXML
    private Label direccionAscensor;

    @FXML
    void initialize() {
        assert lbl != null : "fx:id=\"lbl\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert tfNombre != null : "fx:id=\"tfNombre\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert lbl1 != null : "fx:id=\"lbl1\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert tfOrigen != null : "fx:id=\"tfOrigen\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert lbl11 != null : "fx:id=\"lbl11\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert tfDestino != null : "fx:id=\"tfDestino\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert txtarea != null : "fx:id=\"txtarea\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert gridAscensores != null : "fx:id=\"gridAscensores\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert numAscensor != null : "fx:id=\"numAscensor\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert nombreAscensor != null : "fx:id=\"nombreAscensor\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert nivelActual != null : "fx:id=\"nivelActual\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert numPasajeros != null : "fx:id=\"numPasajeros\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert listViewAscensor != null : "fx:id=\"listViewAscensor\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert direccionAscensor != null : "fx:id=\"direccionAscensor\" was not injected: check your FXML file 'AscensorUI.fxml'.";

        listViewAscensor.setCellFactory(new PasajeroCellFactory());
        listViewAscensor.setItems(pasajerosEnAscensor0);

        nombreAscensor.textProperty().bind(propAscNombre);
        nivelActual.textProperty().bind(propAscNivel.asString());
        numPasajeros.textProperty().bind(propAscCantidadPasajeros.asString());
        direccionAscensor.textProperty().bind(propAscDireccion);

    }

    public TextArea getTxtarea() {
        return txtarea;
    }


    public void setAscensorController(AscensorController ascensorController) {
        this.ascensorController = ascensorController;
        nivelesControl          = new UINivelesControl(ascensorController.getNivelTop());
        mainPane.getChildren().add(nivelesControl.getPane());
        ascensorController.setDisplay(getTxtarea());
        ascensorController.setUiControl(this);
    }

    public void setAscensorData(Ascensor ascensor) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setPropAscNombre(ascensor.getIdName() + "");
                setPropAscCantidadPasajeros(ascensor.getPasajerosTransladandose().keySet().size());
                setPropAscDireccion((ascensor.isSubiendo() ? "{up}" : ascensor.isBajando() ? "{down}" : "{stop}"));
                setPropAscNivel(ascensor.getNivel());
                nivelesControl.activateRadio(ascensor.getNivel());
            }
        });

    }

    public void nuevoViaje(ActionEvent actionEvent) {
        new Pasajero(ascensorController, tfNombre.getText().charAt(0), Integer.parseInt(tfOrigen.getText()), Integer.parseInt(tfDestino.getText())).start();
    }

    public void setPropAscNombre(String propAscNombre) {
        this.propAscNombre.set(propAscNombre);
    }

    public void setPropAscNivel(int propAscNivel) {
        this.propAscNivel.set(propAscNivel);
    }

    public void setPropAscDireccion(String propAscDireccion) {
        this.propAscDireccion.set(propAscDireccion);
    }

    public void setPropAscCantidadPasajeros(int propAscCantidadPasajeros) {
        this.propAscCantidadPasajeros.set(propAscCantidadPasajeros);
    }

    public ObservableList<Pasajero> getPasajerosEnAscensor0() {
        return pasajerosEnAscensor0;
    }

    public void addPasajeroEnAscensor0(Pasajero pasajero) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getPasajerosEnAscensor0().add(pasajero);
            }
        });
    }

    public void removePasajeroEnAscensor0(Pasajero pasajero) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getPasajerosEnAscensor0().remove(pasajero);
            }
        });
    }

    public UINivelesControl getNivelesControl() {
        return nivelesControl;
    }

    @FXML
    public void onActionLoopGo(ActionEvent actionEvent) {
        String numstr = loopNum.getText();
        if (StaticHelpers.isInteger(numstr)) {
            int num = Integer.parseInt(numstr);
            num = Math.min(num, 'Z' - 'A' + 1);
            for (int i = 0; i < num; i++) {
                int niveles = ascensorController.getNivelTop();
                Random random = new Random();
                int origen = random.nextInt(niveles + 1);
                int destino = random.nextInt(niveles + 1);
                Pasajero pasajero = new Pasajero(ascensorController, (char) (i + 'A'), origen, destino);
                pasajero.start();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
