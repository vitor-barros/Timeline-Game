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
    private Button incrementButton;
    @FXML
    private Button incrementButtonDouble;
    @FXML
    private Button decrementButton;
    @FXML
    private Button decrementButtonDouble;
    
    @FXML
    public void initialize() {
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
