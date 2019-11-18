package thread_simulacro_01.Prob_02;


import java.util.Scanner;

public class Counter {

    static int vocales = 0;
    static int consonantes = 0;
    static int total = 0;

    public static void main(String[] args) {
        System.out.print("Introduce una palabra: ");
        String string = new Scanner(System.in).next();
        VCounter vocales = new VCounter(string);
        CCounter consonantes = new CCounter(string);
        ThreadAdder sumador = new ThreadAdder(vocales, consonantes);
        sumador.start();
        try {
            sumador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Total : " + total);
    }

    public static class CCounter extends Thread {

        protected String text;
        protected String valid = "bcdfghjklmnñpqrstvwxyz";
        protected String nombre = "Consonantes";

        public CCounter(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            int count = 0;
            for (char c : text.toCharArray()) {
                for (char d : valid.toCharArray()) {
                    if ((c + "").equalsIgnoreCase(d + "")) {
                        count++;
                    }
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            consonantes = count;
            System.out.println(nombre + " : " + count);
        }
    }

    public static class VCounter extends Thread {

        protected String text;
        protected String valid = "aeiou";
        protected String nombre = "Vocales";

        public VCounter(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            int count = 0;
            for (char c : text.toCharArray()) {
                for (char d : valid.toCharArray()) {
                    if ((c + "").equalsIgnoreCase(d + "")) {
                        count++;
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vocales = count;
            System.out.println(nombre + " : " + count);
        }
    }

    public static class ThreadAdder extends Thread {

        Thread c, v;

        public ThreadAdder(Thread c, Thread v) {
            this.c = c;
            this.v = v;
        }

        @Override
        public void run() {
            c.start();
            v.start();
            try {
                c.join();
                v.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            total = vocales + consonantes;
        }
    }

}
