package thread_ascensor.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import thread_ascensor.Pasajero;

public class UINivelControl {

    private SimpleIntegerProperty nivel = new SimpleIntegerProperty();
    private SimpleIntegerProperty cantidadNivel = new SimpleIntegerProperty();
    private ObservableList<Pasajero> pasajerosNivelIn = FXCollections.observableArrayList();
    private ObservableList<Pasajero> pasajerosNivelOut = FXCollections.observableArrayList();
    private IntegerBinding sizeInProperty = Bindings.size(pasajerosNivelIn);
    private IntegerBinding sizeOutProperty = Bindings.size(pasajerosNivelOut);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hboxNivel;

    @FXML
    private Label lblNumNivel;

    @FXML
    private RadioButton radioNivel;

    @FXML
    private Label lblCantidadNivelIn;
    @FXML
    private Label lblCantidadNivelOut;

    @FXML
    private ListView<Pasajero> listViewNivelIn;
    @FXML
    private ListView<Pasajero> listViewNivelOut;

    @FXML
    void initialize() {
        lblNumNivel.textProperty().bind(nivel.asString());
        lblCantidadNivelIn.textProperty().bind(sizeInProperty.asString());
        lblCantidadNivelOut.textProperty().bind(sizeOutProperty.asString());
        listViewNivelIn.setCellFactory(new PasajeroCellFactory());
        listViewNivelIn.setItems(pasajerosNivelIn);
        listViewNivelOut.setCellFactory(new PasajeroCellFactory());
        listViewNivelOut.setItems(pasajerosNivelOut);

    }

    public void setNivel(int nivel) {
        this.nivel.set(nivel);
    }

    public void setNivelCantidad(int nivelCantidad) {
        this.cantidadNivel.set(nivelCantidad);
    }

    public RadioButton getRadioNivel() {
        return radioNivel;
    }

    public ObservableList<Pasajero> getPasajerosNivelIn() {
        return pasajerosNivelIn;
    }

    public ObservableList<Pasajero> getPasajerosNivelOut() {
        return pasajerosNivelOut;
    }
}
