package thread_ascensor;

public class Solicitante extends Thread {

    private AscensorController ascensorController;
    private int nivelActual;
    private int nivelDestino;


    public Solicitante(AscensorController ascensorController, int nivelActual, int nivelDestino) {
        this.ascensorController = ascensorController;
        this.nivelActual        = nivelActual;
        this.nivelDestino       = nivelDestino;
    }


    @Override
    public void run() {
        ascensorController.llamar(nivelActual);
    }
}
