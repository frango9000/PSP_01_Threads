package thread_simulacro_01.prob_03;

public class Control {

    int total = 0;

    public Control() {
    }

    public synchronized int getTotal() {
        return total;
    }

    public synchronized void setTotal(int total) {
        this.total = total;
    }

    public synchronized void addTotal(int add) {
        this.total += add;
    }


    public static void main(String[] args) {
        Control control = new Control();

        String line = "lineLINES872976575";
        MultiCounter may = new MultiCounter(0, line, control);
        MultiCounter min = new MultiCounter(1, line, control);
        MultiCounter num = new MultiCounter(2, line, control);

        may.start();
        min.start();
        num.start();
        try {
            may.join();
            min.join();
            num.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(control.getTotal());

    }


}
