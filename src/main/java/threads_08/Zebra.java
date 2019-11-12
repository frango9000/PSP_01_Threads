package threads_08;

public class Zebra extends RacePlayer {

    public Zebra() {
        super("Zebra");
    }

    @Override
    protected int getClassMove(int roll) {
        if (roll < 26)
            return 3;
        else if (roll < 31)
            return 7;
        else if (roll < 56)
            return -2;
        else if (roll < 66)
            return -4;
        else if (roll < 76)
            return -8;
        else if (roll < 101)
            return -15;
        else
            return 0;
    }
}
