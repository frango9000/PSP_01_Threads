package thread_ascensor;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import javafx.scene.control.TextArea;
import thread_ascensor.ui.UIControl;

/**
 * clase que controla el sistema de ascensores, niveles y pasajeros, representa la interfaz con la que el usuario
 * interactua cuando va a iniciar un viaje y llama al ascensor desde un nivel con la intencion de ir a otro nivel
 */
public class AscensorController {


    private UIControl uiControl;                            //referencia a la interfaz para enviar los cambios
    private int nivelTop = 15;                              //nivel maximo del ascensor ( 0 = PB ) total =  nivelTop + 1
    private Multimap<Integer, Pasajero> pasajerosEsperando;     //mapa de pasajeros esperando por piso
    private Multimap<Integer, Pasajero> pasajerosFinalizados;   //mapa de pasajeros que finalizaron el viaje
    private Ascensor ascensor;                              //ascensor unico TODO: multiples ascensores


    public AscensorController(UIControl uiControl, int nivelTop) {
        this.uiControl       = uiControl;
        this.nivelTop        = nivelTop;
        pasajerosEsperando   = MultimapBuilder.SetMultimapBuilder.hashKeys(nivelTop).hashSetValues().build();
        pasajerosFinalizados = MultimapBuilder.SetMultimapBuilder.hashKeys(nivelTop).hashSetValues().build();
        ascensor             = new Ascensor(this, 'A');
    }

    public void init() {
        ascensor.start();
    }

    public AscensorController(UIControl uiControl) {
        this(uiControl, 15);
    }


    public synchronized UIControl getUiControl() {
        return uiControl;
    }

    public void setUiControl(UIControl uiControl) {
        this.uiControl = uiControl;
    }

    public synchronized int getNivelTop() {
        return nivelTop;
    }

    /**
     * enviar mensajes a consola y, si existe, al area text de la UI
     *
     * @param log
     */
    public synchronized void log(String log) {
        System.out.println(log);
        TextArea display = uiControl.getTxtarea();
        if (display != null) {
            synchronized (display) {
                display.appendText(log + "\n");
            }
        }
    }

    /**
     * un pasajero llama al ascensor desde un nivel de origen
     * lo agregamos a la cola de espera de su nivel y notificamos
     * al ascensor para que se active si esta en wait()
     * @param pasajeros
     */
    public synchronized void addPasajeroEnEspera(Pasajero pasajeros) {
        getPasajerosEsperando().put(pasajeros.getNivelActual(), pasajeros);
        synchronized (ascensor) {
            ascensor.notify();
        }
    }

    /**
     *
     * @param pasajeros el pasajero que sale de la cola de espera ( luego entrara al ascensor por su cuenta)
     */
    public synchronized void removePasajeroEnEspera(Pasajero pasajeros) {
        getPasajerosEsperando().remove(pasajeros.getNivelActual(), pasajeros);
    }


    /**
     * verifica si hay algun pasajero solicitando desde el nivel indicado
     * @param nivel
     * @return
     */
    public synchronized boolean isSolicitando(int nivel) {
        return getPasajerosEsperando().containsKey(nivel) && !pasajerosEsperando.get(nivel).isEmpty();
    }

    /**
     * obtiene la lista de pasajeros esperando en in nivel
     * @param nivel
     * @return
     */
    public synchronized Collection<Pasajero> getPasajerosEsperando(int nivel) {
        return getPasajerosEsperando().get(nivel);
    }

    /**
     * entregar un pasajero a el mapa de viajes finalizados
     * @param pasajeros
     */
    public synchronized void depositPasajeroFinalizado(Pasajero pasajeros) {
        getPasajerosFinalizados().put(pasajeros.getNivelActual(), pasajeros);
    }

    /**
     * verifica si hay algun ascensor en el nivel indicado
     * @param nivel
     * @return
     */
    public synchronized boolean isAscensorDisponible(int nivel) {
        return ascensor.getNivel() == nivel;
    }

    /**
     * retorna el ascensor que se encuentra en el nivel indicado
     * @param nivel
     * @return
     */
    public synchronized Optional<Ascensor> getAscensor(int nivel) {
        if (ascensor.getNivel() == nivel)
            return Optional.of(ascensor);
        else
            return Optional.empty();
    }

    /**
     * true si hay pasajeros esperando en los niveles superiores al ascensor
     * @param ascensor
     * @return
     */
    public synchronized boolean getContinuarSubiendo(Ascensor ascensor) {
        return getPasajerosEsperando().entries().stream().anyMatch(e -> e.getValue().getNivelActual() > ascensor.getNivel());
    }

    /**
     * true si hay pasajeros esperando en los niveles inferiores al ascensor
     * @param ascensor
     * @return
     */
    public synchronized boolean getContinuarBajando(Ascensor ascensor) {
        return getPasajerosEsperando().entries().stream().anyMatch(e -> e.getValue().getNivelActual() < ascensor.getNivel());
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

    public synchronized Multimap<Integer, Pasajero> getPasajerosEsperando() {
        return pasajerosEsperando;
    }

    public synchronized Multimap<Integer, Pasajero> getPasajerosFinalizados() {
        return pasajerosFinalizados;
    }

    public synchronized Ascensor getAscensor() {
        return ascensor;
    }
}
