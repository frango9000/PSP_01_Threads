package thread_ascensor;

public class Pasajero extends Thread {

    private final int nivelDestino;
    private char idName;
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
        if (this.ascensorEnUso != null) {
            this.ascensorEnUso.getPasajerosTransladandose().get(getNivelDestino()).remove(this);
            controller.getUiControl().removePasajeroEnAscensor0(this);
        }
        this.ascensorEnUso = ascensorEnUso;
        if (ascensorEnUso != null) {
            ascensorEnUso.getPasajerosTransladandose().put(getNivelDestino(), this);
            controller.getUiControl().addPasajeroEnAscensor0(this);
        }
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
    public void run() {             //ciclo de vida de un pasajero
        controller.log("Pasajero " + idString() + " solicitando viaje");
        controller.getUiControl().getNivelesControl().addPasajeroNivelIn(nivelActual, this);
        controller.addPasajeroEnEspera(this);                  // entra en cola de espera
        while (!controller.isAscensorDisponible(nivelActual)) {         // espera hasta que un ascensor notifique que esta en este nivel
            try {
                synchronized (this) {
                    wait();
                    // fue notificado de que hay un ascensor en el nivel que esta esperando
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.getUiControl().getNivelesControl().removePasajeroNivelIn(nivelActual, this);
        controller.removePasajeroEnEspera(this);                  // deja de esperar para entrar en el ascensor
        setAscensorEnUso(controller.getAscensor(nivelActual).get());       // entra en el ascensor
        controller.log("Pasajero " + idString() + " entrando en el ascensor " + ascensorEnUso.getIdName());
        while (!isDestinoAlcanzado()) {                                    // espera a alcanzar su destino
            try {
                synchronized (this) {
                    wait();
                    // fue notificado de que llegamos a un nuveo piso
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setAscensorEnUso(null);                                             // sale del ascensor
        controller.getUiControl().getNivelesControl().addPasajeroNivelOut(nivelActual, this);
        controller.depositPasajeroFinalizado(this);                 // entra en el mapa de viajes finalizados FIN
    }

    private String idString() {
        return "[" + idName + "]{" + nivelActual + "=>" + nivelDestino + "}";
    }


}
