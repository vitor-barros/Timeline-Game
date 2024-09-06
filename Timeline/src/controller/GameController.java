package controller;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameController {

    @FXML
    private VBox rootVBox;
    
    @FXML
    private Slider timelineSlider;
    
    @FXML
    private Label currentYearLabel;
    
    @FXML
    private Label player1NameLabel;
    
    @FXML
    private Label player2NameLabel;
    
    @FXML
    private Label player3NameLabel;
    
    @FXML
    private Label player4NameLabel;
    
    @FXML
    private ImageView player1Avatar;
    
    @FXML
    private ImageView player2Avatar;
    
    @FXML
    private ImageView player3Avatar;
    
    @FXML
    private ImageView player4Avatar;
    
    @FXML
    private Label player1GuessLabel;
    @FXML
    private Label player2GuessLabel;
    @FXML
    private Label player3GuessLabel;
    @FXML
    private Label player4GuessLabel;
    
    @FXML
    private Button incrementButton;
    @FXML
    private Button incrementButtonDouble;
    @FXML
    private Button decrementButton;
    @FXML
    private Button decrementButtonDouble;
    @FXML
    private Button confirmButton;
    @FXML
    private Label currentPlayerLabel;

    private int currentPlayer = 1;
    
    // os palpites serão aramazenados aqui
    private Map<Integer, Integer> playerGuesses = new HashMap<>();
    
    @FXML
    public void initialize() {
    	// Inicializa o jogo com o primeiro jogador
        atualizarTurno();
    	
    	// confirmar o palpite
    	confirmButton.setOnAction(event -> confirmarPalpite());
    	
    	// Configura os eventos dos botões
        incrementButton.setOnAction(event -> incrementYear(1));
        incrementButtonDouble.setOnAction(event -> incrementYear(10));
        decrementButton.setOnAction(event -> incrementYear(-1));
        decrementButtonDouble.setOnAction(event -> incrementYear(-10));
    	
        // Configuração da label do ano
        timelineSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentYearLabel.setText("Ano: " + newValue.intValue());
        });
        
        // Ajuste de layout conforme o tamanho da tela
        rootVBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            adjustLayout(newValue.doubleValue(), rootVBox.getHeight());
        });
    
        rootVBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            adjustLayout(rootVBox.getWidth(), newValue.doubleValue());
        });
    }
    private void confirmarPalpite() {
        // Obtém o valor do slider (o palpite do jogador atual)
        int guess = (int) timelineSlider.getValue();

        // Armazena o palpite no mapa de palpites
        playerGuesses.put(currentPlayer, guess);

        // Atualiza o Label de palpite para o jogador atual
        switch (currentPlayer) {
            case 1:
                player1GuessLabel.setText("Palpite: " + guess);
                break;
            case 2:
                player2GuessLabel.setText("Palpite: " + guess);
                break;
            case 3:
                player3GuessLabel.setText("Palpite: " + guess);
                break;
            case 4:
                player4GuessLabel.setText("Palpite: " + guess);
                break;
        }

        // Após o palpite ser exibido para o último jogador (Jogador 4), finalizar a rodada
        if (currentPlayer == 4) {
        	PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> finalizarRodada());
            pause.play();
        } else {
            // Passa para o próximo jogador
            currentPlayer++;
            atualizarTurno();
        }
    }

    private void atualizarTurno() {
        // Atualiza o Label para mostrar quem é o jogador atual
        currentPlayerLabel.setText("Jogador atual: Jogador " + currentPlayer);

        // Reseta o Slider para um valor padrão, se necessário
        timelineSlider.setValue(1012.0); // ou outro valor inicial
    }

    private void finalizarRodada() {
        // Aqui você pode implementar a lógica de finalização da rodada, como calcular os pontos
        System.out.println("Todos os jogadores fizeram suas jogadas.");

        // Reinicializar para a próxima rodada, começando pelo jogador 1 novamente
        currentPlayer = 1;
        atualizarTurno();

        // Limpar os palpites dos jogadores, se necessário
        playerGuesses.clear();

        // Também pode limpar os Labels de palpite se for apropriado
        player1GuessLabel.setText("Palpite: ");
        player2GuessLabel.setText("Palpite: ");
        player3GuessLabel.setText("Palpite: ");
        player4GuessLabel.setText("Palpite: ");
    }
    
    // Método para diminuir o ano do Slider
    private void incrementYear(int value) {
        double currentValue = timelineSlider.getValue();
        double newValue = currentValue + value;
        
        // Garante que o valor permaneça dentro do limite do Slider
        if (newValue >= timelineSlider.getMin() && newValue <= timelineSlider.getMax()) {
        	timelineSlider.setValue(newValue);
        }
    }
    
    
    
    private void adjustLayout(double width, double height) {
        double newSliderWidth = width * 0.8; // O Slider ocupa 80% da largura disponível
        timelineSlider.setPrefWidth(newSliderWidth);
    }
    
    // Método para receber e definir os nomes dos jogadores
    public void setPlayerNames(String player1Name, String player2Name, String player3Name, String player4Name) {
        player1NameLabel.setText(player1Name);
        player2NameLabel.setText(player2Name);
        player3NameLabel.setText(player3Name);
        player4NameLabel.setText(player4Name);
    }
    
    // Método opcional para definir os avatares dos jogadores, se necessário
    public void setPlayerAvatars(Image avatar1, Image avatar2, Image avatar3, Image avatar4) {
        player1Avatar.setImage(avatar1);
        player2Avatar.setImage(avatar2);
        player3Avatar.setImage(avatar3);
        player4Avatar.setImage(avatar4);
    }
}
