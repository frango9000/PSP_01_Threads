package threads_08;

import java.util.Random;

public abstract class RacePlayer extends Thread {

    protected String playerName;
    protected RaceControl raceControl;

    public RacePlayer(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setRaceControl(RaceControl raceControl) {
        this.raceControl = raceControl;
    }

    @Override
    public void run() {
        while (!raceControl.isGameOver()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int roll = new Random().nextInt(99) + 1;
            int move = getClassMove(roll);
            raceControl.movePlayer(this, move);
        }


    }

    protected abstract int getClassMove(int roll);
}
