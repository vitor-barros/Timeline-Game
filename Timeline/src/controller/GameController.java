package controller;

import javafx.animation.PauseTransition; // Importação para o atraso
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.EventosHistoricos;
import model.Player;
import model.Round;

import java.util.*;

public class GameController {

    @FXML
    private VBox rootVBox;
    @FXML
    private Slider timelineSlider;
    @FXML
    private Label currentYearLabel, currentPlayerLabel, labelDescricaoEvento;
    @FXML
    private ImageView imageViewEvento;
    @FXML
    private ProgressBar progressBarTimer;
    @FXML
    private Label timerLabel;
    @FXML
    private Button incrementButton, incrementButtonDouble, decrementButton, decrementButtonDouble, confirmButton;
    @FXML
    private Label player1NameLabel, player2NameLabel, player3NameLabel, player4NameLabel;
    @FXML
    private Label player1PointsLabel, player2PointsLabel, player3PointsLabel, player4PointsLabel;
    @FXML
    private Label player1GuessLabel, player2GuessLabel, player3GuessLabel, player4GuessLabel, currentRodadaLabel;
    @FXML
    private ImageView player1Avatar, player2Avatar, player3Avatar, player4Avatar;

    private List<Player> players;
    private Round round; // pega a classe Round
    private int currentPlayerIndex = 0;
    private List<EventosHistoricos> eventos;
    private int anoCorreto;
    @FXML
    private TimerController timerController;

    @FXML
    public void initialize() {
        timerController = new TimerController();
        if (timerController == null) {
            System.out.println("Erro: TimerController não foi injetado!");
            return;
        }
        timerController.setProgressBar(progressBarTimer);
        timerController.setTimerLabel(timerLabel);

        initializeGame();
        confirmButton.setOnAction(event -> confirmGuess());
        incrementButton.setOnAction(event -> incrementYear(1));
        incrementButtonDouble.setOnAction(event -> incrementYear(10));
        decrementButton.setOnAction(event -> incrementYear(-1));
        decrementButtonDouble.setOnAction(event -> incrementYear(-10));

        timelineSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentYearLabel.setText("Ano: " + newValue.intValue());
        });

        rootVBox.widthProperty().addListener((observable, oldValue, newValue) -> adjustLayout(newValue.doubleValue(), rootVBox.getHeight()));
        rootVBox.heightProperty().addListener((observable, oldValue, newValue) -> adjustLayout(rootVBox.getWidth(), newValue.doubleValue()));
    }

    private void initializeGame() {
        players = new ArrayList<>();
        players.add(new Player("Jogador 1", new Image("/images/avatar1.png")));
        players.add(new Player("Jogador 2", new Image("/images/avatar2.png")));
        players.add(new Player("Jogador 3", new Image("/images/avatar3.png")));
        players.add(new Player("Jogador 4", new Image("/images/avatar4.png")));
        eventos = new ArrayList<>();
        eventos.add(new EventosHistoricos("Independência do Brasil", 1822, "/images/avatar1.png"));
        eventos.add(new EventosHistoricos("Revolução Francesa", 1789, "/images/avatar2.png"));
        eventos.add(new EventosHistoricos("Primeira Guerra Mundial", 1914, "/images/avatar3.png"));
        eventos.add(new EventosHistoricos("Publicação de \"A Origem das Espécies\" por Charles Darwin.", 1859, "/images/evento01.jpeg"));
        eventos.add(new EventosHistoricos("Descobrimento do Brasil", 1500, "/images/evento02.jpeg"));
        
        round = new Round(players, 5);  // Define o número máximo de rodadas como 5
        iniciarRodadaComEventoAleatorio();
        atualizarLabels();
        atualizarTurno(); // Adiciona chamada para atualizar o nome do jogador na primeira rodada

        if (timerController != null) {
            timerController.startRoundTimer(15, this::endPlayerTurn);
        }
    }

    public void confirmGuess() {
        int guess = (int) timelineSlider.getValue(); // Obtem o palpite do jogador atual
        Player currentPlayer = players.get(currentPlayerIndex); // Obtem o jogador atual
        currentPlayer.addGuess(guess);  // Adiciona o palpite do jogador
        atualizarPalpiteLabel(); // Atualiza o label do palpite

        if (currentPlayerIndex == players.size() - 1) {
            finalizarRodada();
        } else {
            currentPlayerIndex++;
            atualizarTurno();
            timerController.startRoundTimer(15, this::endPlayerTurn);
        }
    }

    private void endPlayerTurn() {
        if (currentPlayerIndex == players.size() - 1) {
            finalizarRodada();
        } else {
            currentPlayerIndex++;
            atualizarTurno();
            timerController.startRoundTimer(15, this::endPlayerTurn);
        }
    }

    private void finalizarRodada() {
        // Limpa o índice do jogador atual e atribui pontuações
        currentPlayerIndex = 0;
        atribuirPontuacoes();
        atualizarLabels();

        // Exibe a lista completa de palpites no console
        System.out.println("Palpites de cada jogador: ");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getGuesses());
        }
            if (round.isGameOver()) {
                // Finalizar o jogo
                System.out.println("Fim do jogo! Pontuações finais: ");
                for (Player player : players) {
                    System.out.println(player.getName() + ": " + player.getPoints() + " pontos");
                }
            } else {
                round.nextRound();
                iniciarRodadaComEventoAleatorio();
                atualizarTurno();
                timerController.startRoundTimer(15, this::endPlayerTurn);
            }
    }

    private void atribuirPontuacoes() {
        // Exemplo de lógica: o jogador que acertar o ano exato recebe 10 pontos, os demais recebem 1 ponto
        for (Player player : players) {
            int guess = player.getLastGuess();
            int diferenca = Math.abs(guess - anoCorreto);
            System.out.println(diferenca);
            if (diferenca == 0) {
                player.addPoints(10);
            } else if (diferenca <= 5) {
                player.addPoints(7);
            } else if (diferenca <= 10) {
                player.addPoints(5);
            } else if (diferenca <= 20) {
                player.addPoints(3);
            } else {
                player.addPoints(1);
            }
        }
    }

    private void iniciarRodadaComEventoAleatorio() {
        if (!eventos.isEmpty()) {
            int randomIndex = (int) (Math.random() * eventos.size());
            EventosHistoricos eventoSelecionado = eventos.remove(randomIndex);
            atualizarImagemEvento(eventoSelecionado.getCaminhoImagem());
            atualizarDescricaoEvento(eventoSelecionado.getDescricao());
            anoCorreto = eventoSelecionado.getAnoCorreto();
        }
    }

    public void atualizarTurno() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayerLabel.setText("Vez de: " + currentPlayer.getName());
        currentRodadaLabel.setText("RODADA: " + round.getCurrentRound() + " / " + round.getMaxRounds());
        timelineSlider.setValue(1012.0); // valor inicial
    }

    public void atualizarImagemEvento(String caminhoImagem) {
        Image image = new Image(getClass().getResourceAsStream(caminhoImagem));
        imageViewEvento.setImage(image);
    }

    public void atualizarDescricaoEvento(String descricao) {
        labelDescricaoEvento.setText(descricao);
    }

    private void adjustLayout(double width, double height) {
        double newSliderWidth = width * 0.8;
        timelineSlider.setPrefWidth(newSliderWidth);
    }

    public void incrementYear(int value) {
        double currentValue = timelineSlider.getValue();
        double newValue = currentValue + value;
        if (newValue >= timelineSlider.getMin() && newValue <= timelineSlider.getMax()) {
            timelineSlider.setValue(newValue);
        }
    }

    public void setPlayerAvatars(Image avatar1, Image avatar2, Image avatar3, Image avatar4) {
        players.get(0).setAvatar(avatar1);
        players.get(1).setAvatar(avatar2);
        players.get(2).setAvatar(avatar3);
        players.get(3).setAvatar(avatar4);

        // Atualize a exibição dos avatares na interface gráfica
        player1Avatar.setImage(avatar1);
        player2Avatar.setImage(avatar2);
        player3Avatar.setImage(avatar3);
        player4Avatar.setImage(avatar4);
    }

    public void setPlayerNames(String name1, String name2, String name3, String name4) {
        players.get(0).setName(name1);
        players.get(1).setName(name2);
        players.get(2).setName(name3);
        players.get(3).setName(name4);
        atualizarLabels();  // Atualiza os labels após definir os nomes
    }

    private void atualizarLabels() {
        // Atualiza os nomes dos jogadores
        player1NameLabel.setText(players.get(0).getName());
        player2NameLabel.setText(players.get(1).getName());
        player3NameLabel.setText(players.get(2).getName());
        player4NameLabel.setText(players.get(3).getName());

        // Atualiza os avatares dos jogadores
        player1Avatar.setImage(players.get(0).getAvatar());
        player2Avatar.setImage(players.get(1).getAvatar());
        player3Avatar.setImage(players.get(2).getAvatar());
        player4Avatar.setImage(players.get(3).getAvatar());

        // Atualiza os pontos dos jogadores
        player1PointsLabel.setText("Pontos: " + players.get(0).getPoints());
        player2PointsLabel.setText("Pontos: " + players.get(1).getPoints());
        player3PointsLabel.setText("Pontos: " + players.get(2).getPoints());
        player4PointsLabel.setText("Pontos: " + players.get(3).getPoints());

        // Atualiza os palpites dos jogadores
        player1GuessLabel.setText("Palpite: " + players.get(0).getLastGuess());
        player2GuessLabel.setText("Palpite: " + players.get(1).getLastGuess());
        player3GuessLabel.setText("Palpite: " + players.get(2).getLastGuess());
        player4GuessLabel.setText("Palpite: " + players.get(3).getLastGuess());
    }

    private void atualizarPalpiteLabel() {
        Player currentPlayer = players.get(currentPlayerIndex);
        String guessLabelId = "player" + (currentPlayerIndex + 1) + "GuessLabel";
        Label guessLabel = (Label) rootVBox.lookup("#" + guessLabelId);
        if (guessLabel != null) {
            guessLabel.setText("Palpite: " + currentPlayer.getLastGuess());
        }
    }
}
