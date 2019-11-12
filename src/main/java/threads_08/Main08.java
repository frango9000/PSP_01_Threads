package threads_08;

public class Main08 {
    public static void main(String[] args) {
        RaceControl raceControl = new RaceControl();
        Tortuga tortuga = new Tortuga();
        Liebre liebre = new Liebre();
        Mono mono = new Mono();
        Leon leon = new Leon();
        Zebra zebra = new Zebra();


        raceControl.addPlayer(tortuga);
        raceControl.addPlayer(liebre);
        raceControl.addPlayer(mono);
        raceControl.addPlayer(leon);
        raceControl.addPlayer(zebra);

        raceControl.startRace();
    }
}
