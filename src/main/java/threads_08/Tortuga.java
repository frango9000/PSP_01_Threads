package threads_08;

public class Tortuga extends RacePlayer {

    public Tortuga() {
        super("Tortuga");
    }

    @Override
    protected int getClassMove(int roll) {
        if (roll < 51)
            return 3;
        else if (roll < 81)
            return 1;
        else if (roll < 101)
            return -6;
        else
            return 0;
    }
}
