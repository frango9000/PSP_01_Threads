/*
Programa que crea 4 fíos, os executa e espera a que estes terminen. Os fíos escriben 5 veces o
número de iteración do bucle e o seu nome. En cada iteración, despois de escribir o seu nome,
bloquéanse durante un segundo e volven a estar dispoñibles para a súa execución.
 */
package threads_01_05;

public class Ej02 extends Thread {

    private String nombre;

    public Ej02(String nombre) {
        this.nombre = nombre;
    }

    public static void main(String[] args) {
        Ej02 ta = new Ej02("A");
        Ej02 tb = new Ej02("B");
        Ej02 tc = new Ej02("C");
        Ej02 td = new Ej02("D");
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
        for (int i = 0; i < 5; i++) {
            int wait = 1000;
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
