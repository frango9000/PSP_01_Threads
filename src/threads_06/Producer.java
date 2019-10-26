package threads_06;

import java.util.Random;

public class Producer extends Thread {

    private Caja caja;
    private int saldoIn;


    public Producer(Caja caja, int saldoIn) {
        this.caja    = caja;
        this.saldoIn = saldoIn;
    }

    @Override
    public void run() {
        int wait = new Random().nextInt(401) + 100;
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        caja.ingresoThreadSafe(saldoIn);
//        caja.ingresoNoThreadSafe(saldoIn);
    }
}
