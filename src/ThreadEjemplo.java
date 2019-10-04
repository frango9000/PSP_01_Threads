public class ThreadEjemplo extends Thread {

    public ThreadEjemplo(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            double lag = 9.9999f / 8.999999;

            System.out.println(i + " " + getName() + " " + lag);

        }
        System.out.println("Termina thread " + getName());
    }

    public static void main(String[] args) {
        new ThreadEjemplo("A").start();
        new ThreadEjemplo("B").start();
        new ThreadEjemplo("C").start();
        new ThreadEjemplo("D").start();
        new ThreadEjemplo("E").start();
        new ThreadEjemplo("F").start();
        System.out.println("Termina thread main");

    }
}
