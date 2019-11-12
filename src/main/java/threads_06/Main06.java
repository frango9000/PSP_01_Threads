package threads_06;

import java.util.ArrayList;
import java.util.Random;

public class Main06 extends Thread {

    public static void main(String[] args) throws InterruptedException {
        int saldoInicial = 100;
        Caja caja = new Caja(saldoInicial);

        int totalAudit = inOut(caja, 20200, 20000);

        System.out.println();
        System.out.println("Total requerido = " + totalAudit);
        System.out.println("Total procesado = " + caja.toString());

    }

    private static int inOut(Caja caja, int ins, int outs) throws InterruptedException {
        int totalAudit = caja.getSaldo();
        ArrayList<Thread> threads = new ArrayList<>();
        while(ins+outs > 0){
            int maxOut = Math.min(outs, 5);
            for (int i = 0; i < maxOut; i++) {
                int out = new Random().nextInt(301) + 100;
                Consumer c = new Consumer(caja, out);
                c.start();
                threads.add(c);
                totalAudit -= out;
            }
            outs -= maxOut;
            int maxIn = Math.min(ins, 5);
            for (int i = 0; i < maxIn; i++) {
                int in = new Random().nextInt(301) + 100;
                Producer p = new Producer(caja, in);
                p.start();
                threads.add(p);
                totalAudit += in;
            }
            ins -= maxIn;
        }

        for (Thread thread : threads) {
            thread.join();
        }
        return totalAudit;
    }

}
