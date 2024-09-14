package model;

import java.util.List;
import java.util.Comparator;

public class Round {
    private List<Player> players;
    private int currentRound;
    private final int maxRounds;

    public Round(List<Player> players, int maxRounds) {
        this.players = players;
        this.currentRound = 1;
        this.maxRounds = maxRounds;
    }

    public boolean isGameOver() {
        return currentRound > maxRounds;
    }

    public void nextRound() {
        if (!isGameOver()) {
            currentRound++;
            sortPlayersByPoints(); // Reorganiza a ordem dos turnos com base nos pontos acumulados
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void sortPlayersByPoints() {
        players.sort(Comparator.comparingInt(Player::getPoints).reversed());
    }

    public List<Player> getPlayers() {
        return players;
    }
    public int getMaxRounds() {
        return maxRounds;
    }
}
