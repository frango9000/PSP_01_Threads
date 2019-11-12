package threads_08;

public class Leon extends RacePlayer {

    public Leon() {
        super("Leon");
    }

    @Override
    protected int getClassMove(int roll) {
        if (roll < 16)
            return 4;
        else if (roll < 41)
            return 8;
        else if (roll < 56)
            return 2;
        else if (roll < 76)
            return -2;
        else if (roll < 96)
            return -9;
        else if (roll < 101)
            return -20;
        else
            return 0;
    }
}
