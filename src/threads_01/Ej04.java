/*
Realiza un programa en java que execute tres fíos de forma concurrente. Un de eles debe sumar os
números pares del 1 ao 1000, outro os impares, e outro, os que terminen en dous ou en tres
 */
package threads_01;


public class Ej04 extends Thread {

    //sw = 0 suma pares
    //sw = 1 suma inpares
    //sw = 2 suma terminaciones 2 y 3
    private int sw = 0;

    //constructor que determina la suma a realizar
    public Ej04(int sw) {
        this.sw = sw;
    }

    @Override
    public void run() {
        int sum = 0;
        switch (sw) {
            case 0:
                for (int i = 1; i < 1000; i++) {
                    if (i % 2 == 0)
                        sum += i;
                }
                break;
            case 1:
                for (int i = 1; i < 1000; i++) {
                    if (i % 2 != 0)
                        sum += i;
                }
                break;
            case 2:
                for (int i = 1; i < 1000; i++) {
                    String parsed = Integer.toString(i);
                    if (parsed.endsWith("2") || parsed.endsWith("3")) {
                        sum += i;
                    }
                }
                break;
        }
        System.out.println("Thread " + sw + " sum: " + sum);
    }

    public static void main(String[] args) {
        Ej04 pares = new Ej04(0);
        Ej04 inpares = new Ej04(1);
        Ej04 dosytres = new Ej04(2);

        pares.start();
        inpares.start();
        dosytres.start();
    }
}
