package thread_exa;

import java.util.Random;

public class Prob02 {

    public static void main(String[] args) {

        System.out.println("Inicia Hilo Principal");
        Hilo hiloA = new Hilo("A");
        Hilo hiloB = new Hilo("B");
        Hilo hiloC = new Hilo("C");
        try {
            hiloA.start();
            hiloA.join();
            hiloB.start();
            hiloB.join();
            hiloC.start();
            hiloC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Termina Hilo Principal");

    }

    private static class Hilo extends Thread {

        private String nombre = "";

        public Hilo(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            System.out.println("Inicia Hilo " + nombre);
            for (int i = 0; i < 25; i++) {
                int rand = new Random().nextInt(500) + 500;
                System.out.println("Hilo " + nombre + ". It: " + i);
                try {
                    Thread.sleep(rand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.yield();
            }
            System.out.println("Termina Hilo " + nombre);
        }
    }

}
