package threads_07;

import java.util.ArrayList;
import java.util.Random;

public class Main07 {

    public static void main(String[] args) throws InterruptedException {
        Buzon buzon = new Buzon(3);
        inOut(buzon, 600, 600);
    }


    private static void inOut(Buzon buzon, int ins, int outs) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        while (ins + outs > 0) {
            int maxOut = Math.min(outs, 5);
            for (int i = 0; i < maxOut; i++) {
                int out = new Random().nextInt(301) + 100;
                Consumidor c = new Consumidor(buzon);
                c.start();
                threads.add(c);
            }
            outs -= maxOut;
            int maxIn = Math.min(ins, 5);
            for (int i = 0; i < maxIn; i++) {
                int in = new Random().nextInt(301) + 100;
                Productor p = new Productor(buzon, "Correo randID: " + in);
                p.start();
                threads.add(p);
            }
            ins -= maxIn;
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

}
