package threads_01_05;

import java.util.Random;

public class Ej03 extends Thread {

    private int threadNum;


    public Ej03(int threadNum) {
        this.threadNum = threadNum;
    }

    public static void main(String[] args) {
        System.out.println("Main Start");
        Ej03 mainthread = new Ej03(4);
        mainthread.start();
        try {
            mainthread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main End");
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadNum + " start");
        Ej03 child = null;
        if (threadNum > 0) {
            child = new Ej03(threadNum - 1);
            child.start();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("SubThread " + threadNum + "." + i + " start");
            int wait = new Random().nextInt(501) + 100;
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("SubThread " + threadNum + "." + i + " end (" + wait + ")");
        }
        if (threadNum > 0) {
            try {
                child.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread " + threadNum + " end");
    }
}
