package thread_simulacro_01.Prob_02;

public class Counter {

    public static void main(String[] args) {
        String string = "murcielago";
        TextCounter vocales = new TextCounter(string, "aeiou", "Vocales");
        TextCounter consonantes = new TextCounter(string, "bcdfghjklmnpqrstvwxyz", "Consonantes");
        vocales.start();
        consonantes.start();
    }

    public static class TextCounter extends Thread {

        protected String text;
        protected String valid;
        protected String nombre;

        public TextCounter(String text, String valid, String nombre) {
            this.text   = text;
            this.valid  = valid;
            this.nombre = nombre;
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
            System.out.println(nombre + " : " + count);
        }
    }


}
