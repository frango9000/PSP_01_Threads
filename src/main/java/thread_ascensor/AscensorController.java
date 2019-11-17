package thread_ascensor;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import javafx.scene.control.TextArea;

public class AscensorController {

    private int niveles = 15;
    private Multimap<Integer, Pasajeros> pasajerosEsperando;
    private Multimap<Integer, Pasajeros> pasajerosFinalizados;
    private Ascensor ascensor;
    TextArea display;


    public AscensorController() {
        pasajerosEsperando   = MultimapBuilder.SetMultimapBuilder.hashKeys(niveles).hashSetValues().build();
        pasajerosFinalizados = MultimapBuilder.SetMultimapBuilder.hashKeys(niveles).hashSetValues().build();
        ascensor             = new Ascensor(this, 'A');
        ascensor.start();
    }

    public void setDisplay(TextArea display) {
        this.display = display;
    }

    public synchronized void log(String log) {
        System.out.println(log);
        if (display != null) {
            synchronized (display) {
                display.appendText(log + "\n");
            }
        }
    }

    public synchronized int getNiveles() {
        return niveles;
    }

    public synchronized void addPasajeroEnEspera(Pasajeros pasajeros) {
        pasajerosEsperando.put(pasajeros.getNivelActual(), pasajeros);
        synchronized (ascensor) {
            ascensor.notify();
        }
    }

    public synchronized void removePasajeroEnEspera(Pasajeros pasajeros) {
        pasajerosEsperando.remove(pasajeros.getNivelActual(), pasajeros);
    }

    public synchronized boolean isSolicitando(int nivel) {
        return pasajerosEsperando.containsKey(nivel) && !pasajerosEsperando.get(nivel).isEmpty();
    }

    public synchronized Collection<Pasajeros> getPasajerosEsperando(int nivel) {
        return pasajerosEsperando.get(nivel);
    }

    public synchronized void depositPasajeroFinalizado(Pasajeros pasajeros) {
        pasajerosFinalizados.put(pasajeros.getNivelActual(), pasajeros);
    }

    public synchronized boolean isAscensorDisponible(int nivel) {
        return ascensor.getNivel() == nivel;
    }

    public synchronized Optional<Ascensor> getAscensor(int nivel) {
        if (ascensor.getNivel() == nivel)
            return Optional.of(ascensor);
        else
            return Optional.empty();
    }


    public static void waitFor(int seconds) {
        waitForMs(seconds * 1000);
    }

    public static void waitForMs(int ms) {
        int rand = new Random().nextInt(ms / 10);//possible +10% rand delay
        try {
            Thread.sleep(ms + rand);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public synchronized boolean getContinuarSubiendo(Ascensor ascensor) {
        return pasajerosEsperando.entries().stream().anyMatch(e -> e.getValue().getNivelActual() > ascensor.getNivel());
    }

    public synchronized boolean getContinuarBajando(Ascensor ascensor) {
        return pasajerosEsperando.entries().stream().anyMatch(e -> e.getValue().getNivelActual() < ascensor.getNivel());
    }
}
