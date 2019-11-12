package threads_08;

public class Liebre extends RacePlayer {

    public Liebre() {
        super("Liebre");

    }

    @Override
    protected int getClassMove(int roll) {
        if (roll < 31)
            return 1;
        else if (roll < 51)
            return 9;
        else if (roll < 71)
            return 0;
        else if (roll < 91)
            return -2;
        else
            return -12;
    }
}
