package thread_exa;

import java.util.Random;

public class Prob03 {

    public static void main(String[] args) {

        System.out.println("Inicia Hilo Principal");
        Threader hilo1 = new Threader();
        hilo1.start();
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
            }
            System.out.println("Termina Hilo " + nombre);
        }
    }

    private static class Threader extends Thread {

        public Threader() {

        }

        @Override
        public void run() {
            System.out.println("Inicia Threader");
            Hilo hiloA = new Hilo("A");
            Hilo hiloB = new Hilo("B");
            Hilo hiloC = new Hilo("C");
            hiloA.start();
            hiloB.start();
            hiloC.start();
            try {
                hiloA.join();
                hiloB.join();
                hiloC.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Termina Threader");
        }
    }


}
