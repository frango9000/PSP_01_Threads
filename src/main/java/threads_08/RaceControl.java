/*
Implementa unha simulación da fabula que conta a carreira entre a lebre e a tartaruga.
Para facelo máis interesante a carreira será costa arriba por unha pista esvaradía, de modo
que ás veces poderán escorregar e retroceder algunhas posicións. Haberá un thread que
implementará a tartaruga e outro a lebre. Cada un suspenderase durante un segundo e
logo avaliará o que pasou segundo unhas probabilidades:
Animal Suceso Probalidade Movemento
Tartaruga Avance rápido 50% 3 casiñas á dereita
Esvarou 20% 6 casiñas á esquerda
Avance lento 30% 1 casiña á dereita
Lebre Dorme 20%
Gran salto 20% 9 casiñas á dereita
Esvarón grande 10% 12 casiñas á esquerda
Pequeno salto 30% 1 casiña á dereita
Esvarón pequeno 20% 2 casiñas á esquerda
Calcule a probabilidade con random, de 1 a 100 e determine co devandito número que fixo
cada animal. Considere que hai 70 casiñas, da 1 á 70, a 1 de saída e a 70 de chegada.
Se escorrega ao principio volve á 1, nunca por baixo. Tras cada segundo e
despois de calcular a súa nova posición imprima unha liña por cada animal, ca posición e logo
unha letra T para a tartaruga e unha L para a lebre. Imprima ao
comeza da carreira unha mensaxe. Despois de imprimir as liñas determine se algún chegou
a meta e gañou, imprimindo unha mensaxe. Se ambos chegan á vez declare un
empate.
 */
package threads_08;

import java.util.HashSet;
import org.apache.commons.lang3.ArrayUtils;

public class RaceControl {

    private RacePlayer[] raceTrack;
    private int trackLength = 71;
    private boolean gameOver = false;

    private HashSet<RacePlayer> players = new HashSet<>();

    public RaceControl() {
        raceTrack = new RacePlayer[trackLength + 1];
    }

    public RaceControl(int trackLength) {
        this.trackLength = trackLength;
    }

    public void addPlayer(RacePlayer racePlayer) {
        players.add(racePlayer);
        racePlayer.setRaceControl(this);
    }

    public synchronized void movePlayer(RacePlayer movingPlayer, int moveSize) {

        int actualPos = -1;
        if (ArrayUtils.contains(raceTrack, movingPlayer)) {
            actualPos            = ArrayUtils.indexOf(raceTrack, movingPlayer);
            raceTrack[actualPos] = null;
        }
        int newPos = Math.min((actualPos + moveSize), trackLength - 1);
        //System.out.println("Verificando movimiento de " + movingPlayer.getPlayerName() + " a la casilla " + newPos);
        while (newPos > -1 && raceTrack[newPos] != null) {
            newPos--;
            System.out.println("Casilla " + newPos + " ocupada, intentando la casilla anterior...");
        }
        if (newPos > -1) {
            System.out.println(movingPlayer.getPlayerName() + " se mueve (" + moveSize + ") de " + actualPos + " a " + newPos);
            gameOver          = newPos == trackLength - 1;
            raceTrack[newPos] = movingPlayer;
        }
        if (isGameOver()) {
            endRace();
        }
    }

    public synchronized boolean isGameOver() {
        synchronized (this) {
            return gameOver;
        }
    }

    public void endRace() {
        System.out.println("\nGAME OVER!\nFinaliza la carrera!, Ganador: " + raceTrack[trackLength - 1].getPlayerName());
        int rank = 1;
        System.out.println(String.format("%4s -> [%3s]%10s", "Rank", "Pos", "Nombre"));
        for (int i = raceTrack.length - 1; i >= 0; i--) {
            if (raceTrack[i] != null) {
                System.out.println(String.format("%4d -> [%3d]%10s", rank++, i, raceTrack[i].playerName));
            }
        }
        for (RacePlayer racePlayer : players) {
            racePlayer.interrupt();
        }
    }

    public void startRace() {
        System.out.println("Comienza la carrera, " + players.size() + " participantes.");
        for (RacePlayer racePlayer : players) {
            racePlayer.start();
        }
    }
}
