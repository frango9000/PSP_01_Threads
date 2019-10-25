/*
Programa en Java que cree dous fíos e os executa. Os fíos escriben dez veces un número de
iteración do bucle e seu nome. En cada iteración, despois de escribir o seu nome, se bloquean
durante un tempo aleatorio de segundos e después volven a estar dispoñibles para a súa
execución.
 */
package threads_01_05;

import java.util.Random;

public class Ej01 extends Thread {

    private String nombre;

    public Ej01(String nombre) {
        this.nombre = nombre;
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

    @Override
    public void run() {
        System.out.println("Thread " + nombre + " start");
        for (int i = 0; i < 10; i++) {
            int wait = new Random().nextInt(3000) + 1001;
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(nombre + " " + i + " " + wait);
        }
        System.out.println("Thread " + nombre + " end");
    }
}
