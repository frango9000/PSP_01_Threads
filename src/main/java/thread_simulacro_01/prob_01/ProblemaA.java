package thread_simulacro_01.prob_01;

import java.util.Random;

public class ProblemaA {

    public static void main(String[] args) {
        System.out.println("Main Start");
        Hilo a = new Hilo("A");
        Hilo b = new Hilo("B");
        Hilo c = new Hilo("C");
        a.start();
        b.start();
        c.start();

        try {
            a.join();
            b.join();
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main End");

    }

    public static class Hilo extends Thread {

        private String nombre;

        public Hilo(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            for (int i = 0; i < 8; i++) {
                System.out.println("Hilo " + nombre + " v: " + i);
                try {
                    Thread.sleep(new Random().nextInt(3) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
