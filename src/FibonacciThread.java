public class FibonacciThread extends Thread {

    private int n;

    public FibonacciThread(int n) {
        super("Fibonacci thread");
        this.n = n;
    }

    @Override
    public void run() {
        long f = 1;
        long j = 1;
        for (int i = 1; i <= n; i++) {
            long temp = j;
            j = f;
            f += temp;
            System.out.print(i + " Fibonacci:");
            System.out.println(f);

        }
    }

    public static void main(String[] args) {
        new FibonacciThread(100).start();
    }
}
