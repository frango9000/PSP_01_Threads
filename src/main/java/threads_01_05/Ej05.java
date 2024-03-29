/*
Escribe unha clase que cree dous fíos e force que a escritura do segundo sexa sempre anterior á
escritura por pantalla do primeiro.
 Exemplo de ejecución:
 Ola, son o fío número 2
 Ola, son o fío número 1
a) faino con join
b )faino con prioridades
 */
package threads_01_05;

import java.util.ArrayList;

public class Ej05 extends Thread {

    Thread child;
    String name;

    public Ej05(String name) {
        this(name, null);
    }

    // constructor que crea un thread  con otro thread hijo, el padre esperara al hijo para finalizar su codigo
    public Ej05(String name, Thread child) {
        this.name  = name;
        this.child = child;
    }

    public static void main(String[] args) {
        usandoJoin();
        usandoPrioridades();

    }

    private static void usandoJoin() {
        Ej05 threadB = new Ej05("B");
        Ej05 threadA = new Ej05("A", threadB);//thread con un hijo quien debe terminar primero

        threadA.start();
        threadB.start();
    }

    private static void usandoPrioridades() {
        Ej05 threadB = new Ej05("B");
        Ej05 threadA = new Ej05("A");
        threadB.setPriority(MAX_PRIORITY);

        startByPrio(threadA, threadB);
    }

    private static void startByPrio(Thread... threads) {//Ordenamos los threads en base a su prioridad para ser iniciados en orden de prioridad
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            for (Thread thread : threads) {
                if (thread.getPriority() == i) {
                    list.add(thread);
                }
            }
        }
        for (Thread thread : list) {
            thread.start();
        }
    }

    @Override
    public void run() {
        if (child != null) {
            try {
                child.join();//esperamos al hijo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hola soy el hilo " + name);
    }
}
