package model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Player {
    private String name;
    private Image avatar;
    private int points;
    private int guess;
    private List<Integer> guesses = new ArrayList<>(); // lista de palpites

    public Player(String name, Image avatar) {
        this.name = name;
        this.avatar = avatar;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
        addGuess(guess); // Adiciona o palpite atual à lista de palpites
    }

    // Método para adicionar um novo palpite
    public void addGuess(int guess) {
        guesses.add(guess);  // Adiciona o novo palpite à lista
    }

    // Método para retornar todos os palpites
    public List<Integer> getGuesses() {
        return guesses;  // Retorna a lista de palpites
    }

    // Método para retornar o último palpite
    public int getLastGuess() {
        if (guesses.isEmpty()) {
            return 0;  // Retorna 0 se não houver palpites ainda
        }
        return guesses.get(guesses.size() - 1);  // Retorna o último palpite da lista
    }
}
