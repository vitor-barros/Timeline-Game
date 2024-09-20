package controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import model.Player;

public class EndGameController {
    @FXML
    private MediaView backgroundVideoEnd;
    @FXML
    private ImageView imagePergaminho, podiumImageView, firstPlaceAvatar, secondPlaceAvatar, thirdPlaceAvatar;
    @FXML
    private Label firstPlaceLabel, secondPlaceLabel, thirdPlaceLabel;
    @FXML
    private VBox rootVBox;
    @FXML
    private List<Player> players;

    public void setPlayers(List<Player> players) {
        this.players = players;
        initializeAvatars();
    }

    private void initializeAvatars() {
        if (players != null && players.size() >= 3) {
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
        if (rootVBox != null) {
            backgroundVideoEnd.fitWidthProperty().bind(rootVBox.widthProperty());
            backgroundVideoEnd.fitHeightProperty().bind(rootVBox.heightProperty());
        } else {
            System.out.println("rootVBox está nulo. Verifique se ele foi inicializado corretamente.");
        }

        // Carregar o vídeo de fundo
        String videoPath = getClass().getResource("/images/backgroundImage3_animation.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Configurar o MediaPlayer para reproduzir continuamente em loop
        //mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Configurar o MediaView com o MediaPlayer
        backgroundVideoEnd.setMediaPlayer(mediaPlayer);

        // Iniciar a reprodução do vídeo
        mediaPlayer.play();
      
        
        Image pergaminhoImage = new Image(getClass().getResourceAsStream("/images/pergaminho2hd.png"));
        imagePergaminho.setImage(pergaminhoImage);
        
        Image podiumImage = new Image(getClass().getResourceAsStream("/images/podium2hd.png"));
        podiumImageView.setImage(podiumImage);
        
        imagePergaminho.fitHeightProperty().bind(rootVBox.heightProperty());
        imagePergaminho.setFitWidth(700);
        
    }
}
