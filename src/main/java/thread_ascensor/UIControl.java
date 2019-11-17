package thread_ascensor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UIControl {


    AscensorController ascensorController;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtarea;

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
    void initialize() {
        assert txtarea != null : "fx:id=\"txtarea\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert lbl != null : "fx:id=\"lbl\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert tfNombre != null : "fx:id=\"tfNombre\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert lbl1 != null : "fx:id=\"lbl1\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert tfOrigen != null : "fx:id=\"tfOrigen\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert lbl11 != null : "fx:id=\"lbl11\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert tfDestino != null : "fx:id=\"tfDestino\" was not injected: check your FXML file 'AscensorUI.fxml'.";
        assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'AscensorUI.fxml'.";

    }

    public TextArea getTxtarea() {
        return txtarea;
    }

    public void setAscensorController(AscensorController ascensorController) {
        this.ascensorController = ascensorController;
    }

    public void nuevoViaje(ActionEvent actionEvent) {
        new Pasajeros(ascensorController, tfNombre.getText().charAt(0), Integer.parseInt(tfOrigen.getText()), Integer.parseInt(tfDestino.getText())).start();
    }
}
