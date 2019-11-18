/*
Realiza un programa en java que execute tres fíos de forma concurrente. Un de eles debe sumar os
números pares del 1 ao 1000, outro os impares, e outro, os que terminen en dous ou en tres
 */
package thread_simulacro_01.prob_03;


public class MultiCounter extends Thread {

    //sw = 0 suma pares
    //sw = 1 suma inpares
    //sw = 2 suma terminaciones 2 y 3
    private int sw = 0;

    private String text;

    private Control control;


    //constructor que determina la suma a realizar
    public MultiCounter(int sw, String text, Control control) {
        this.sw      = sw;
        this.text    = text;
        this.control = control;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (sw) {
            case 0:
                for (char c : text.toCharArray()) {
                    if (Character.isUpperCase(c))
                        control.addTotal(1);
                }
                break;
            case 1:
                for (char c : text.toCharArray()) {
                    if (Character.isLowerCase(c))
                        control.addTotal(1);
                }
                break;
            case 2:
                for (char c : text.toCharArray()) {
                    if (Character.isDigit(c))
                        control.addTotal(1);
                }
                break;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
