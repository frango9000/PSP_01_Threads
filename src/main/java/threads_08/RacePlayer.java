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
            Random random = new Random();
            int roll = random.nextInt(99) + 1;
            //System.out.println(getPlayerName() + " rolls " + roll);
            int move = getClassMove(roll);
            raceControl.movePlayer(this, move);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }


    }

    protected abstract int getClassMove(int roll);
}
