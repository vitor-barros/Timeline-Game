package controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import model.Player;

public class EndGameController {
    @FXML
    private MediaView backgroundVideoEnd;
    @FXML
    private ImageView imagePergaminho, podiumImageView, firstPlaceAvatar, secondPlaceAvatar, thirdPlaceAvatar;
    @FXML
    private Label winnerLabel, firstPlaceLabel, secondPlaceLabel, thirdPlaceLabel;
    @FXML
    private Button exitButton, menuButton;
    @FXML
    private VBox rootVBox;
    private List<Player> players;

    // Este método será chamado para definir a lista de jogadores
    public void setPlayers(List<Player> players) {
        this.players = players;
        initializeAvatars();  // Inicializa os avatares com a lista atualizada de jogadores
        initializeWinner();    // Atualiza o rótulo do vencedor
    }

    // Inicializa os avatares e rótulos de cada jogador
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

    // Atualiza o rótulo do vencedor
    private void initializeWinner() {
        if (players != null && !players.isEmpty()) {
            winnerLabel.setText("O vencedor é: " + players.get(0).getName());
        }
    }

    // Método chamado automaticamente após o carregamento do FXML
    public void initialize() {
    	// Configura os eventos dos botões
    	 exitButton.setOnAction(event -> exitGame());
    	 
    	
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
        backgroundVideoEnd.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();  // Iniciar a reprodução do vídeo

        // Carregar as imagens
        Image pergaminhoImage = new Image(getClass().getResourceAsStream("/images/pergaminho2hd.png"));
        imagePergaminho.setImage(pergaminhoImage);

        Image podiumImage = new Image(getClass().getResourceAsStream("/images/podium2hd.png"));
        podiumImageView.setImage(podiumImage);

        // Ajustar o tamanho da imagem do pergaminho
        imagePergaminho.fitHeightProperty().bind(rootVBox.heightProperty());
        imagePergaminho.setFitWidth(700);
    }

    private void exitGame() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
