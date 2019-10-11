package threads_01;

import java.util.Random;

public class Ej01 extends Thread {

    private String nombre;

    public Ej01(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            int wait = new Random().nextInt(1000) + 1;
            System.out.println(nombre + " " + i + " " + wait);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Ej01 ta = new Ej01("A");
        Ej01 tb = new Ej01("B");
        Ej01 tc = new Ej01("C");
        Ej01 td = new Ej01("D");
        ta.start();
        tb.start();
        tc.start();
        td.start();
        try {
            ta.join();
            tb.join();
            tc.join();
            td.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
