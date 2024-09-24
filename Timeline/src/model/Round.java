package model;

import java.util.List;
import java.util.Comparator;

public class Round {
    private List<Player> players;
    public int currentRound;
    private int maxRounds;

    public Round(List<Player> players, int maxRounds) {
        this.players = players;
        this.currentRound = 1;
        this.maxRounds = maxRounds;
    }

    public boolean isGameOver() {
        return currentRound == maxRounds;
    }

    public void nextRound() {
        System.out.println("Tentando avançar para a próxima rodada. Rodada atual: " + currentRound);
        if (!isGameOver()) {
        	currentRound++;
            System.out.println("Rodada incrementada para: " + currentRound);
            sortPlayersByPoints();
        } else {
            System.out.println("O jogo já terminou. Não é possível avançar para a próxima rodada.");
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
    public void setMaxRounds(int maxRounds) {
    	 this.maxRounds = maxRounds;
    }
}
