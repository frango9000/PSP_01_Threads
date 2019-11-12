package threads_07;

import java.util.Random;

public class Productor extends Thread {

    private Buzon buzon;
    private String correo;

    public Productor(Buzon buzon, String correo) {
        this.buzon  = buzon;
        this.correo = correo;
    }

    @Override
    public void run() {
        int wait = new Random().nextInt(201) + 100;
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buzon.ingresarCorreo(correo);
    }
}
