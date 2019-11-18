package thread_ascensor.ui;

import javafx.scene.control.ListCell;
import thread_ascensor.Pasajero;

public class PasajeroCell extends ListCell<Pasajero> {

    @Override
    public void updateItem(Pasajero item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null)
            this.setText(Integer.toString(item.getNivelDestino()));
        setGraphic(null);
    }
}
