package controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Player;

public class EndGameController {
	@FXML
    private ImageView podiumImageView, backgroundImageEnd,firstPlaceAvatar, secondPlaceAvatar, thirdPlaceAvatar;
    @FXML
    private Button restartButton;
	@FXML
	private Label firstPlaceLabel, secondPlaceLabel, thirdPlaceLabel;
    
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
            
            firstPlaceLabel.setText(players.get(0).getName());
            secondPlaceLabel.setText(players.get(1).getName());
            thirdPlaceLabel.setText(players.get(2).getName());
     
        }
    }
    
    public void initialize() {
        initializeAvatars();
        
//        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/pergaminho2.jpeg"));
//        backgroundImageEnd.setImage(backgroundImage);
//        
//        Image podiumImage = new Image(getClass().getResourceAsStream("/images/pergaminho2.jpeg"));
//        podiumImageView.setImage(podiumImage);
//     // Carrega a imagem do pódio
        
        
        


        
    }
    
}
