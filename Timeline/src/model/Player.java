package model;

import javafx.scene.image.Image;

public class Player {

    private static int idCounter = 0;  // Contador estático para gerar IDs únicos
    private int id;
    private String name;
    private Image avatar;
    private int points;

    public Player(String name, Image avatar) {
        this.id = idCounter++;
        this.name = name;
        this.avatar = avatar;
        this.points = 0;  // Inicializa os pontos com zero
    }

    public int getId() {
        return id;
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

    // Adiciona pontos ao jogador
    public void addPoints(int points) {
        this.points += points;
    }

    // Reseta os pontos do jogador
    public void resetPoints() {
        this.points = 0;
    }
}
