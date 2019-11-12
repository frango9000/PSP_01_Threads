package threads_00;

public class ThreadEjemploB extends Thread {

    public ThreadEjemploB(String name) {
        super(name);
    }

    public static void main(String[] args) {
        ThreadEjemploB tA = new ThreadEjemploB("A");
        ThreadEjemploB tB = new ThreadEjemploB("B");
        ThreadEjemploB tC = new ThreadEjemploB("C");
        tA.start();
        tB.setPriority(MIN_PRIORITY);
        tB.start();
        tC.start();

        System.out.println("Termina thread main");

    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            double lag = 9.9999f / 8.999999;

            System.out.println(i + " " + getName() + " " + lag);

        }
        System.out.println("Termina thread " + getName());
    }
}
