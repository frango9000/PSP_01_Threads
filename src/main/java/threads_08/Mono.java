package threads_08;

public class Mono extends RacePlayer {

    public Mono() {
        super("Mono");
    }

    @Override
    protected int getClassMove(int roll) {
        if (roll < 36)
            return 3;
        else if (roll < 51)
            return 7;
        else if (roll < 71)
            return 2;
        else if (roll < 96)
            return -2;
        else if (roll < 101)
            return -9;
        else
            return 0;
    }
}
