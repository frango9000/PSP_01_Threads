package threads_08;

import java.util.ArrayList;
import java.util.HashSet;

public class RaceControl {

    private ArrayList<RacePlayer> raceTrack = new ArrayList<>();
    private int trackLength = 70;

    private HashSet<RacePlayer> players = new HashSet<>();

    public RaceControl(int trackLength) {
        this.trackLength = trackLength;
    }

    public void addPlayer(RacePlayer racePlayer) {
        players.add(racePlayer);
        racePlayer.setRaceControl(this);
    }

    public synchronized void movePlayer(RacePlayer movingPlayer, int moveSize) {
        if (raceTrack.contains(movingPlayer)) {
            int actualPos = raceTrack.indexOf(movingPlayer);
            int newPos = actualPos + moveSize;
            if (newPos < 0) {
                raceTrack.remove(movingPlayer);
            }
        }

    }

    public void startRace() {
        System.out.println("Comienza la carrera, " + players.size() + " participantes.");
        for (RacePlayer racePlayer : players) {
            racePlayer.start();
        }
    }
}
