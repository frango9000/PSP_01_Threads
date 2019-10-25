package threads_06;

import java.util.ArrayList;
import java.util.Random;

public class Main06 extends Thread {

    public static void main(String[] args) throws InterruptedException {
        int saldoInicial = 100;
        Caja caja = new Caja(saldoInicial);

        int totalInOut = inOut(caja, 1000, 500);

        System.out.println(caja.toString());
        System.out.println("Total requerido = " + totalInOut);

    }

    private static int inOut(Caja caja, int ins, int outs) throws InterruptedException {
        int totalInOut = caja.getSaldo();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < outs; i++) {
            int out = new Random().nextInt(401) + 100;
            Consumer c = new Consumer(caja, out);
            c.start();
            threads.add(c);
            totalInOut -= out;
        }
        for (int i = 0; i < ins; i++) {
            int in = new Random().nextInt(401) + 100;
            Producer p = new Producer(caja, in);
            p.start();
            threads.add(p);
            totalInOut += in;
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return totalInOut;
    }

}
