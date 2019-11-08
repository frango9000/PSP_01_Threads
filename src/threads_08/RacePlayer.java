package threads_08;

import java.util.Random;

public abstract class RacePlayer extends Thread {

    protected String name;
    protected RaceControl raceControl;

    public RacePlayer(String name) {
        this.name = name;
    }

    public void setRaceControl(RaceControl raceControl) {
        this.raceControl = raceControl;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int roll = new Random().nextInt(99) + 1;
        raceControl.movePlayer(this, getClassMove());

    }

    protected abstract int getClassMove();
}
