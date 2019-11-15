package thread_ascensor;

import java.util.Arrays;

public class AscensorController {

    private int niveles = 14;
    private boolean[] solicitudes;
    boolean subiendo = false;
    boolean bajando = false;

    public AscensorController() {
        solicitudes = new boolean[14];
        Arrays.fill(solicitudes, false);
    }

    public void llamar(int nivel) {
        solicitudes[nivel] = true;
    }


}
