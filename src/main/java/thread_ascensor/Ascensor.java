package thread_ascensor;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

class Ascensor extends Thread {

    private char idName;
    private int nivel = 0;
    private AscensorController controller;

    private boolean subiendo = false;
    private boolean bajando = false;
    private boolean isActive = true;

    private Multimap<Integer, Pasajeros> pasajerosTransladandose;//llave niveldestino

    public Ascensor(AscensorController controller, char idName) {
        this.controller              = controller;
        this.idName                  = idName;
        this.pasajerosTransladandose = MultimapBuilder.SetMultimapBuilder.hashKeys(controller.getNiveles()).hashSetValues().build();
    }

    public char getIdName() {
        return idName;
    }

    public int getNivel() {
        return nivel;
    }

    public synchronized Multimap<Integer, Pasajeros> getPasajerosTransladandose() {
        return pasajerosTransladandose;
    }

    @Override
    public void run() {
        while (true) {
            if (isActive) {
                controller.log("Ascensor " + idString());
                if (subiendo || bajando) {
                    AscensorController.waitFor(2);//delay: tiempo para cambiar de nivel
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
                            }
                            controller.log("Ascensor " + idString() + " awake");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //
//    private int carga(){
//        int in = 0;
//        if(controller.isSolicitando(nivel)){
//            in = controller.getPasajerosEsperando(nivel).size();
//            controller.getPasajerosEsperando(nivel).forEach(pasajero -> {
//                synchronized (pasajero) {
//                    pasajero.notify();
//                }
//            });
//        }
//        return in;
//    }
//
//    private int descarga(){
//        int out = 0;
//        if(!pasajerosTransladandose.get(nivel).isEmpty()) {
//            out = pasajerosTransladandose.get(nivel).size();
//            pasajerosTransladandose.entries().forEach((e) -> {
//                Pasajeros pasajeros = e.getValue();
//                synchronized (pasajeros) {
//                    pasajeros.setNivelActual(nivel);
//                    pasajeros.notify();
//                }
//            });
//        }
//        return out;
//    }
//    private int cargaDescarga() {
//        int in = carga();
//        int out = descarga();
//        if(in+out>0)
//            controller.log("Ascensor " + idString() + " In:" + in + " Out:" + out);
//        return in + out;
//    }
    private int cargaDescarga() {
        int inOut = 0;
        if (controller.isSolicitando(nivel) || !pasajerosTransladandose.get(nivel).isEmpty()) {
            int in = controller.getPasajerosEsperando(nivel).size();
            int out = pasajerosTransladandose.get(nivel).size();
            controller.log("Ascensor " + idString() + " In:" + in + " Out:" + out);

            controller.getPasajerosEsperando(nivel).forEach(pasajero -> {
                synchronized (pasajero) {
                    pasajero.notify();
                }
            });

            pasajerosTransladandose.entries().forEach((e) -> {
                Pasajeros pasajeros = e.getValue();
                synchronized (pasajeros) {
                    pasajeros.setNivelActual(nivel);
                    e.getValue().notify();
                }
            });
            inOut = in + out;
            AscensorController.waitFor(2 + in + out);//delay: 1 sec por cada pasajero entrando y saliendo
        }
        return inOut;
    }

    private boolean subir() {
        boolean necesidadAscendenciaLocal = pasajerosTransladandose.entries().stream().anyMatch((e) -> e.getValue().getNivelDestino() > nivel);
        boolean necesidadAscendenciaGlobal = controller.getContinuarSubiendo(this);
        return subiendo = (necesidadAscendenciaGlobal || necesidadAscendenciaLocal) && nivel < controller.getNiveles();
    }

    private boolean bajar() {
        boolean necesidadDescendenciaLocal = pasajerosTransladandose.entries().stream().anyMatch((e) -> e.getValue().getNivelDestino() < nivel);
        boolean necesidadDescendenciaGlobal = controller.getContinuarBajando(this);
        return bajando = (necesidadDescendenciaLocal || necesidadDescendenciaGlobal) && (nivel > 0);
    }

    private String idString() {
        return "[" + idName + "] (" + nivel + "/" + controller.getNiveles() + ") <" + cantidadPasajeros() + "> " + (subiendo ? "{up}" : bajando ? "{down}" : "{stop}");
    }

    private int cantidadPasajeros() {
        return pasajerosTransladandose.entries().size();
    }

}
