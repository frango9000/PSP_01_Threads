package thread_ascensor;

import java.util.Scanner;

public class MainLift {

    public static void main(String[] args) {

        AscensorController ascensorController = new AscensorController();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce piso origen: ");
            char n = scanner.next().charAt(0);
            int origen = scanner.nextInt();
            int destino = scanner.nextInt();
            Pasajeros pasajeros = new Pasajeros(ascensorController, n, origen, destino);
            pasajeros.start();
        }


    }

}
