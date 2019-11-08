package threads_07;

import java.util.Random;

public class Consumidor extends Thread {

    private Buzon buzon;

    public Consumidor(Buzon buzon) {
        this.buzon = buzon;
    }

    @Override
    public void run() {
        int wait = new Random().nextInt(201) + 100;
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buzon.leerCorreo();
    }
}
