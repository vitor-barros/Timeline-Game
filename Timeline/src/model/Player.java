package model;

import javafx.scene.image.Image;

public class Player {
    private String name;
    private Image avatar;
    private int points;
    private int guess;

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
    }
}
