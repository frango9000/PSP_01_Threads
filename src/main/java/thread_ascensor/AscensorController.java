package thread_ascensor;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import javafx.scene.control.TextArea;
import thread_ascensor.ui.UIControl;

public class AscensorController {

    private int niveles = 15;
    private Multimap<Integer, Pasajero> pasajerosEsperando;
    private Multimap<Integer, Pasajero> pasajerosFinalizados;
    private Ascensor ascensor;
    TextArea display;

    UIControl uiControl;


    public AscensorController(int niveles) {
        this.niveles         = niveles;
        pasajerosEsperando   = MultimapBuilder.SetMultimapBuilder.hashKeys(niveles).hashSetValues().build();
        pasajerosFinalizados = MultimapBuilder.SetMultimapBuilder.hashKeys(niveles).hashSetValues().build();
        ascensor             = new Ascensor(this, 'A');
        ascensor.start();
    }

    public AscensorController() {
        this(15);
    }

    public void setDisplay(TextArea display) {
        this.display = display;
    }

    public void setUiControl(UIControl uiControl) {
        this.uiControl = uiControl;
    }

    public synchronized void log(String log) {
        System.out.println(log);
        if (display != null) {
            synchronized (display) {
                display.appendText(log + "\n");
            }
        }
    }

    public UIControl getUiControl() {
        return uiControl;
    }

    public synchronized int getNiveles() {
        return niveles;
    }

    public synchronized void addPasajeroEnEspera(Pasajero pasajeros) {
        pasajerosEsperando.put(pasajeros.getNivelActual(), pasajeros);
        synchronized (ascensor) {
            ascensor.notify();
        }
    }

    public synchronized void removePasajeroEnEspera(Pasajero pasajeros) {
        pasajerosEsperando.remove(pasajeros.getNivelActual(), pasajeros);
    }

    public synchronized boolean isSolicitando(int nivel) {
        return pasajerosEsperando.containsKey(nivel) && !pasajerosEsperando.get(nivel).isEmpty();
    }

    public synchronized Collection<Pasajero> getPasajerosEsperando(int nivel) {
        return pasajerosEsperando.get(nivel);
    }

    public synchronized void depositPasajeroFinalizado(Pasajero pasajeros) {
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
