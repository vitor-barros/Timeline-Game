package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.EventosHistoricos;

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

    @FXML
    private ImageView imageViewEvento;

    @FXML
    private Label labelDescricaoEvento;
    
    @FXML
    private ProgressBar progressBarTimer;
    
    @FXML
    private Label timerLabel;

    // Injeção do TimerController via FXML
    @FXML
    private TimerController timerController;

    
    public int currentPlayer = 1;
    private Map<Integer, Integer> playerGuesses = new HashMap<>();
    private List<EventosHistoricos> eventos;

    @FXML
    public void initialize() {
    	timerController = new TimerController();
        //  se o timerController foi corretamente injetado
        if (timerController == null) {
            System.out.println("Erro: TimerController não foi injetado!");
            return;
        }

        // Configura o TimerController com a barra de progresso e o label do tempo
        timerController.setProgressBar(progressBarTimer);
        timerController.setTimerLabel(timerLabel);

        // Inicializa o jogo
        initializeGame();

        // Configura ações dos botões
        confirmButton.setOnAction(event -> confirmGuess());
        incrementButton.setOnAction(event -> incrementYear(1));
        incrementButtonDouble.setOnAction(event -> incrementYear(10));
        decrementButton.setOnAction(event -> incrementYear(-1));
        decrementButtonDouble.setOnAction(event -> incrementYear(-10));

        // Atualiza o slider de tempo
        timelineSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentYearLabel.setText("Ano: " + newValue.intValue());
        });

        // Ajusta o layout
        rootVBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            adjustLayout(newValue.doubleValue(), rootVBox.getHeight());
        });

        rootVBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            adjustLayout(rootVBox.getWidth(), newValue.doubleValue());
        });
    }

    private void initializeGame() {
        eventos = new ArrayList<>();
        eventos.add(new EventosHistoricos("Independência do Brasil", 1822, "/images/avatar1.png"));
        eventos.add(new EventosHistoricos("Revolução Francesa", 1789, "/images/avatar2.png"));
        eventos.add(new EventosHistoricos("Primeira Guerra Mundial", 1914, "/images/avatar3.png"));

        iniciarRodadaComEventoAleatorio(); // Inicia a primeira rodada

        // Inicia o temporizador da rodada, garantindo que o timerController não é nulo
        if (timerController != null) {
            timerController.startRoundTimer(15, this::endPlayerTurn);
        } else {
            System.out.println("Erro: TimerController não está disponível para iniciar o temporizador.");
        }
    }

    public void confirmGuess() {
        int guess = (int) timelineSlider.getValue();
        playerGuesses.put(currentPlayer, guess);

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
        endPlayerTurn();
    }

    private void endPlayerTurn() {
        if (currentPlayer == 4) {
            finalizarRodada(); // Todos os jogadores jogaram
        } else {
        	// eu tenho que saber de quem é a vez pra jogar no
        	// label do palpite
            currentPlayer++;
            atualizarTurno();
            timerController.startRoundTimer(15, this::endPlayerTurn);
        }
    }

    public void stopTimer() {
        timerController.stopTimer();
    }

    private void finalizarRodada() {
        currentPlayer = 1;
        playerGuesses.clear();
        player1GuessLabel.setText("Palpite: ");
        player2GuessLabel.setText("Palpite: ");
        player3GuessLabel.setText("Palpite: ");
        player4GuessLabel.setText("Palpite: ");
        timerController.stopTimer();
        iniciarRodadaComEventoAleatorio();

        // Reinicia o temporizador da rodada
        timerController.startRoundTimer(15, this::endPlayerTurn);
    }

    public void incrementYear(int value) {
        double currentValue = timelineSlider.getValue();
        double newValue = currentValue + value;

        if (newValue >= timelineSlider.getMin() && newValue <= timelineSlider.getMax()) {
            timelineSlider.setValue(newValue);
        }
    }

    public void iniciarRodadaComEventoAleatorio() {
        if (!eventos.isEmpty()) {
            int randomIndex = (int) (Math.random() * eventos.size());
            EventosHistoricos eventoSelecionado = eventos.remove(randomIndex);

            iniciarRodadaComEvento(eventoSelecionado.getDescricao(), eventoSelecionado.getAnoCorreto(), eventoSelecionado.getCaminhoImagem());
        } else {
            System.out.println("Todos os eventos foram utilizados!");
        }
    }

    public void iniciarRodadaComEvento(String descricao, int anoCorreto, String caminhoImagem) {
        atualizarImagemEvento(caminhoImagem);
        atualizarDescricaoEvento(descricao);
    }

    public void atualizarTurno() {
        currentPlayerLabel.setText("Vez de: Jogador " + currentPlayer);
        timelineSlider.setValue(1012.0); // valor inicial
    }

    private void adjustLayout(double width, double height) {
        double newSliderWidth = width * 0.8;
        timelineSlider.setPrefWidth(newSliderWidth);
    }

    public void setPlayerNames(String player1Name, String player2Name, String player3Name, String player4Name) {
        player1NameLabel.setText(player1Name);
        player2NameLabel.setText(player2Name);
        player3NameLabel.setText(player3Name);
        player4NameLabel.setText(player4Name);
    }

    public void setPlayerAvatars(Image avatar1, Image avatar2, Image avatar3, Image avatar4) {
        player1Avatar.setImage(avatar1);
        player2Avatar.setImage(avatar2);
        player3Avatar.setImage(avatar3);
        player4Avatar.setImage(avatar4);
    }

    public void atualizarImagemEvento(String caminhoImagem) {
        Image image = new Image(getClass().getResourceAsStream(caminhoImagem));
        imageViewEvento.setImage(image);
    }

    public void atualizarDescricaoEvento(String descricao) {
        labelDescricaoEvento.setText(descricao);
    }
}
