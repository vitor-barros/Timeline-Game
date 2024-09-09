package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.EventosHistoricos;

public class GameLogicController {


    @FXML
    private Label labelDescricaoEvento;

    private int currentPlayer = 1;
    private Map<Integer, Integer> playerGuesses = new HashMap<>();
    private List<EventosHistoricos> eventos;
    private GameController gameController;

    public GameLogicController() {
        // O construtor agora não tem parâmetros
    }

    // Método para inicializar a referência ao GameController
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        initializeGame(); // Inicializar o jogo após definir o GameController
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void initializeGame() {
        eventos = new ArrayList<>();
        eventos.add(new EventosHistoricos("Independência do Brasil", 1822, "/images/avatar1.png"));
        eventos.add(new EventosHistoricos("Revolução Francesa", 1789, "/images/avatar2.png"));
        eventos.add(new EventosHistoricos("Primeira Guerra Mundial", 1914, "/images/avatar3.png"));
        iniciarRodadaComEventoAleatorio(); // Inicia a primeira rodada
    }

    public void confirmGuess() {
        int guess = (int) gameController.timelineSlider.getValue();
        playerGuesses.put(currentPlayer, guess);

        // Atualize o Label de palpite para o jogador atual
        switch (currentPlayer) {
            case 1:
                gameController.player1GuessLabel.setText("Palpite: " + guess);
                break;
            case 2:
                gameController.player2GuessLabel.setText("Palpite: " + guess);
                break;
            case 3:
                gameController.player3GuessLabel.setText("Palpite: " + guess);
                break;
            case 4:
                gameController.player4GuessLabel.setText("Palpite: " + guess);
                break;
        }

        if (currentPlayer == 4) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> finalizarRodada());
            pause.play();
        } else {
            currentPlayer++;
            gameController.atualizarTurno();
        }
    }

    private void finalizarRodada() {
        currentPlayer = 1;
        playerGuesses.clear();
        gameController.player1GuessLabel.setText("Palpite: ");
        gameController.player2GuessLabel.setText("Palpite: ");
        gameController.player3GuessLabel.setText("Palpite: ");
        gameController.player4GuessLabel.setText("Palpite: ");
        iniciarRodadaComEventoAleatorio();
    }

    public void incrementYear(int value) {
        double currentValue = gameController.timelineSlider.getValue();
        double newValue = currentValue + value;

        if (newValue >= gameController.timelineSlider.getMin() && newValue <= gameController.timelineSlider.getMax()) {
            gameController.timelineSlider.setValue(newValue);
        }
    }

    public void iniciarRodadaComEventoAleatorio() {
        if (!eventos.isEmpty()) {
            int randomIndex = (int) (Math.random() * eventos.size()); // Seleciona um evento aleatório
            EventosHistoricos eventoSelecionado = eventos.remove(randomIndex); // Remove o evento para não se repetir

            iniciarRodadaComEvento(eventoSelecionado.getDescricao(), eventoSelecionado.getAnoCorreto(), eventoSelecionado.getCaminhoImagem());
        } else {
            System.out.println("Todos os eventos foram utilizados!"); // Você pode exibir uma mensagem ou encerrar o jogo
        }
    }
    
    public void iniciarRodadaComEvento(String descricao, int anoCorreto, String caminhoImagem) {
       gameController.atualizarImagemEvento(caminhoImagem);
       gameController.atualizarDescricaoEvento(descricao);
    }
}
