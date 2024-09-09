package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GameController {

    @FXML
    private VBox rootVBox;
    
    @FXML
    public Slider timelineSlider;
    
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
    public Label player1GuessLabel;
    @FXML
    public Label player2GuessLabel;
    @FXML
    public Label player3GuessLabel;
    @FXML
    public Label player4GuessLabel;
    
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
    public  ImageView imageViewEvento;  // Adicionado para o evento de imagem
    @FXML
    public Label labelDescricaoEvento;

    private GameLogicController gameLogicController;

    @FXML
    public void initialize() {
        // Instanciação do GameLogicController sem parâmetros
        gameLogicController = new GameLogicController();
        // Configuração do GameController no GameLogicController
        gameLogicController.setGameController(this);

        atualizarTurno();

        confirmButton.setOnAction(event -> gameLogicController.confirmGuess());
        incrementButton.setOnAction(event -> gameLogicController.incrementYear(1));
        incrementButtonDouble.setOnAction(event -> gameLogicController.incrementYear(10));
        decrementButton.setOnAction(event -> gameLogicController.incrementYear(-1));
        decrementButtonDouble.setOnAction(event -> gameLogicController.incrementYear(-10));

        timelineSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentYearLabel.setText("Ano: " + newValue.intValue());
        });

        rootVBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            adjustLayout(newValue.doubleValue(), rootVBox.getHeight());
        });

        rootVBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            adjustLayout(rootVBox.getWidth(), newValue.doubleValue());
        });
    }

    public void atualizarTurno() {
        currentPlayerLabel.setText("Jogador atual: Jogador " + gameLogicController.getCurrentPlayer());
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
        imageViewEvento.setImage(image);  // Atualiza a imagem do evento
    }
    public void atualizarDescricaoEvento(String descricao) {
    	labelDescricaoEvento.setText(descricao);
    }
    
}
