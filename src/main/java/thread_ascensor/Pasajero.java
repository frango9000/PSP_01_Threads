package thread_ascensor;

public class Pasajero extends Thread {

    private final int nivelDestino;
    char idName;
    private AscensorController controller;
    private Ascensor ascensorEnUso;
    private int nivelActual;


    public Pasajero(AscensorController controller, char idName, int nivelActual, int nivelDestino) {
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
        controller.getUiControl().getNivelesControl().addPasajeroNivelIn(nivelActual, this);
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
        controller.getUiControl().getNivelesControl().removePasajeroNivelIn(nivelActual, this);
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

        setAscensorEnUso(null);
        controller.getUiControl().getNivelesControl().addPasajeroNivelOut(nivelActual, this);
        controller.depositPasajeroFinalizado(this);
    }

    private String idString() {
        return "[" + idName + "]{" + nivelActual + "=>" + nivelDestino + "}";
    }


}
