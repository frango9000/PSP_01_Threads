package thread_ascensor;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

public class Ascensor extends Thread {

    private char idName;
    private int nivel = 0;
    private AscensorController controller;

    private boolean subiendo = false;
    private boolean bajando = false;

    private Multimap<Integer, Pasajero> pasajerosTransladandose;//llave niveldestino

    public Ascensor(AscensorController controller, char idName) {
        this.controller              = controller;
        this.idName                  = idName;
        this.pasajerosTransladandose = MultimapBuilder.SetMultimapBuilder.hashKeys(controller.getNivelTop()).hashSetValues().build();
    }

    public char getIdName() {
        return idName;
    }

    public int getNivel() {
        return nivel;
    }

    public boolean isSubiendo() {
        return subiendo;
    }

    public boolean isBajando() {
        return bajando;
    }

    public synchronized Multimap<Integer, Pasajero> getPasajerosTransladandose() {
        return pasajerosTransladandose;
    }


    @Override
    public void run() {
        while (true) {
            sendDataToUI();
            controller.log("Ascensor " + idString());
            if (subiendo || bajando) {
                AscensorController.waitFor(1);//delay: tiempo para cambiar de nivel
                if (subiendo) {
                    nivel++;
                    subir();
                }
                if (bajando) {
                    nivel--;
                    bajar();
                }
                cargaDescarga();
            } else {
                try {
                    if (!subir() && !bajar() && cargaDescarga() == 0) {
                        controller.log("Ascensor " + idString() + " sleep");
                        synchronized (this) {
                            wait();
                            controller.log("Ascensor " + idString() + " In:" + cantidadPasajeros() + " Out:" + "0");
                            sendDataToUI();
                        }
                        controller.log("Ascensor " + idString() + " awake");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void sendDataToUI() {
        if (controller.getUiControl() != null)
            controller.getUiControl().setAscensorData(this);
    }

    /**
     * el ascensor llega a un nivel y carga los pasajeros esperando en ese nivel y descarga los pasajeros que se dirigen
     * a ese nivel y esperamos 2 segundos + 1 segundo por cada movimiento de pasajero
     *
     * @return la cantidad de pasajeros que bajan + pasajeros que suben
     */
    private int cargaDescarga() {
        int inOut = 0;
        if (controller.isSolicitando(nivel) || !getPasajerosTransladandose().get(nivel).isEmpty()) {
            int in = controller.getPasajerosEsperando(nivel).size();
            int out = getPasajerosTransladandose().get(nivel).size();
            controller.log("Ascensor " + idString() + " In:" + in + " Out:" + out);

            controller.getPasajerosEsperando(nivel).forEach(pasajero -> {
                synchronized (pasajero) {
                    pasajero.notify();
                }
            });

            getPasajerosTransladandose().entries().forEach((e) -> {
                Pasajero pasajeros = e.getValue();
                synchronized (pasajeros) {
                    pasajeros.setNivelActual(nivel);
                    e.getValue().notify();
                }
            });
            inOut = in + out;
            sendDataToUI();
            AscensorController.waitFor(1);
            AscensorController.waitFor(in + out);//delay: 1 sec por cada pasajero entrando y saliendo
            sendDataToUI();
        }
        return inOut;
    }

    /**
     *
     * @return true si el ascensor necesita subir ( hay pasajeros esperando en los niveles superiores o
     *          hay pasajeros dentro que se dirijan a los niveles superiores
     */
    private boolean subir() {
        boolean necesidadAscendenciaLocal = getPasajerosTransladandose().entries().stream().anyMatch((e) -> e.getValue().getNivelDestino() > nivel);
        boolean necesidadAscendenciaGlobal = controller.getContinuarSubiendo(this);
        return subiendo = (necesidadAscendenciaGlobal || necesidadAscendenciaLocal) && nivel < controller.getNivelTop();
    }

    /**
     *
     * @return true si el ascensor necesita bajar ( hay pasajeros esperando en los niveles inferiores o
     *          hay pasajeros dentro que se dirijan a los niveles inferiores
     */
    private boolean bajar() {
        boolean necesidadDescendenciaLocal = getPasajerosTransladandose().entries().stream().anyMatch((e) -> e.getValue().getNivelDestino() < nivel);
        boolean necesidadDescendenciaGlobal = controller.getContinuarBajando(this);
        return bajando = (necesidadDescendenciaLocal || necesidadDescendenciaGlobal) && (nivel > 0);
    }

    private String idString() {
        return "[" + idName + "] (" + nivel + "/" + controller.getNivelTop() + ") <" + cantidadPasajeros() + "> " + (subiendo ? "{up}" : bajando ? "{down}" : "{stop}");
    }

    /**
     *
     * @return int, cantidad de pasajeros dentro del ascensor
     */
    private int cantidadPasajeros() {
        return getPasajerosTransladandose().entries().size();
    }

}
