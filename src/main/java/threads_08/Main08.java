package threads_08;

public class Main08 {

    public static void main(String[] args) {
        RaceControl raceControl = new RaceControl();
        Tortuga tortuga = new Tortuga();
        Liebre liebre = new Liebre();

        raceControl.addPlayer(tortuga);
        raceControl.addPlayer(liebre);

        raceControl.startRace();
    }

}
