package threads_06;

import java.util.Random;

public class Consumer extends Thread {

    private Caja caja;
    private int saldoOut;


    public Consumer(Caja caja, int saldoOut) {
        this.caja     = caja;
        this.saldoOut = saldoOut;
    }

    @Override
    public void run() {
        int wait = new Random().nextInt(401) + 100;
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        caja.egresoThreadSafe(saldoOut);
//        caja.egresoNoThreadSafe(saldoOut);
        if (caja.getSaldo() < 0)
            System.out.println(caja.getSaldo());

    }
}
