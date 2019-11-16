package threads_07;

import java.util.Stack;

public class Buzon {
    //Contiene el contenedor de los correos y controla que no se ingresen correos si esta lleno,
    // ni solicitudes de correos cuando esta vacio, dejandolos en espera y reactivandolos respectivamente

    //el contenedor de correos
    private Stack<String> buzon;
    //la capacidad maxima
    private int size;

    public Buzon(int size) {
        this.size  = size;
        this.buzon = new Stack<>();
    }

    public synchronized void ingresarCorreo(String correo) {
        boolean hold = false;
        while (buzon.size() >= size) {
            try {
                System.out.println("Buzon lleno, esperando plazas libres...");
                hold = true;
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Correo ingresado" + (hold ? " tras espera " : " directo "));
        buzon.push(correo);
        notify();
    }

    public synchronized void leerCorreo() {
        boolean hold = false;
        while (buzon.isEmpty()) {
            try {
                System.out.println("Buzon vacio, esperando correos nuevos...");
                hold = true;
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Correo leido" + (hold ? " tras espera " : " directo ") + buzon.pop());
        notify();
    }
}
