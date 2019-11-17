package thread_ascensor;

public class Pasajeros extends Thread {

    private AscensorController controller;
    private Ascensor ascensorEnUso;
    private int nivelActual;
    private final int nivelDestino;


    char idName;


    public Pasajeros(AscensorController controller, char idName, int nivelActual, int nivelDestino) {
        this.idName       = idName;
        this.controller   = controller;
        this.nivelActual  = nivelActual;
        this.nivelDestino = nivelDestino;

    }

    public boolean isDestinoAlcanzado() {
        return nivelActual == nivelDestino;
    }

    public Ascensor getAscensorEnUso() {
        return ascensorEnUso;
    }

    public void setAscensorEnUso(Ascensor ascensorEnUso) {
        if (this.ascensorEnUso != null)
            this.ascensorEnUso.getPasajerosTransladandose().get(getNivelDestino()).remove(this);
        this.ascensorEnUso = ascensorEnUso;
        if (ascensorEnUso != null)
            ascensorEnUso.getPasajerosTransladandose().put(getNivelDestino(), this);
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public int getNivelDestino() {
        return nivelDestino;
    }

    public char getIdName() {
        return idName;
    }

    @Override
    public void run() {
        controller.log("Pasajero " + idString() + " solicitando viaje");
        controller.addPasajeroEnEspera(this);
        while (!controller.isAscensorDisponible(nivelActual)) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.removePasajeroEnEspera(this);
        setAscensorEnUso(controller.getAscensor(nivelActual).get());
        controller.log("Pasajero " + idString() + " entrando en el ascensor " + ascensorEnUso.getIdName());
        while (!isDestinoAlcanzado()) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.log("Pasajero " + idString() + " saliendo en el piso " + nivelActual + "/" + nivelDestino);
        setAscensorEnUso(null);
        controller.depositPasajeroFinalizado(this);
    }

    private String idString() {
        return "[" + idName + "]{" + nivelActual + "=>" + nivelDestino + "}";
    }


}
