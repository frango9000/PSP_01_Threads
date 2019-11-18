package thread_ascensor.ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import thread_ascensor.Pasajero;

public class PasajeroCellFactory implements Callback<ListView<Pasajero>, ListCell<Pasajero>> {

    @Override
    public ListCell<Pasajero> call(ListView<Pasajero> listview) {
        return new PasajeroCell();
    }
}
