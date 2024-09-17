package controller;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Player;

public class EndGameController {
	@FXML
    private ImageView podiumImageView, firstPlaceAvatar, secondPlaceAvatar, thirdPlaceAvatar;
    @FXML
    private Button restartButton;
	
    @FXML
    private AnchorPane backgroundPane;

    private List<Player> players;
    
    public void setPlayers(List<Player> players) {
    	this.players = players;
    	initializeAvatars();
    }
    private void initializeAvatars() {
        if (players != null && players.size() >= 3) {
            // Supondo que os jogadores estão ordenados pela pontuação
            firstPlaceAvatar.setImage(players.get(0).getAvatar());
            secondPlaceAvatar.setImage(players.get(1).getAvatar());
            thirdPlaceAvatar.setImage(players.get(2).getAvatar());
        }
    }
    
    public void initialize() {
        initializeAvatars();
     // Carrega a imagem do pódio
        //Image podiumImage = new Image(getClass().getResourceAsStream("/images/podium.jpeg"));
        //podiumImageView.setImage(podiumImage);


        
    }
    
}
