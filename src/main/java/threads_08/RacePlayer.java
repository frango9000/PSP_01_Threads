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

    /*
    verificamos si la carrera aun esta activa, creamos in random del 1 al 100 representando un 100% de probabilidad
    cada implementacion de un race player debe procesar el random para retornar el valor del movimiento a realizar en el tablero
    con el valor de movimiento le pedimos al controlador que nos mueva a la casilla correcta
     */
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

    //metodo que debe ser implementado en las herederas
    protected abstract int getClassMove(int roll);
}
