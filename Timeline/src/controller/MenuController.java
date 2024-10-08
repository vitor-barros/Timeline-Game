package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button playButton;
    @FXML
    private Button instructionsButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button exitButton;

    @FXML
    private TextField player1Name;
    @FXML
    private TextField player2Name;
    @FXML
    private TextField player3Name;
    @FXML
    private TextField player4Name;

    @FXML
    private ImageView player1Avatar;
    @FXML
    private ImageView player2Avatar;
    @FXML
    private ImageView player3Avatar;
    @FXML
    private ImageView player4Avatar;
    @FXML
    private ImageView titleImage;
    
    @FXML
    private Label statusLabel;

    private int selectedRounds = 5; // Valor padrão

    @FXML
    private void initialize() {
        // Carregar a imagem do título
        Image title = new Image(getClass().getResourceAsStream("/images/titulo2removebghd.png"));
        titleImage.setImage(title);
        
        // Carrega as imagens dos avatares
        Image avatar1 = new Image(getClass().getResourceAsStream("/images/avatar1.png"));
        Image avatar2 = new Image(getClass().getResourceAsStream("/images/avatar2.png"));
        Image avatar3 = new Image(getClass().getResourceAsStream("/images/avatar3.png"));
        Image avatar4 = new Image(getClass().getResourceAsStream("/images/avatar4.png"));

        // Define as imagens nos ImageViews
        player1Avatar.setImage(avatar1);
        player2Avatar.setImage(avatar2);
        player3Avatar.setImage(avatar3);
        player4Avatar.setImage(avatar4);
        
        // Configura os eventos dos botões
        playButton.setOnAction(event -> startGame());
        instructionsButton.setOnAction(event -> showInstructions());
        optionsButton.setOnAction(event -> showOptions());
        exitButton.setOnAction(event -> exitGame());
    
        // Listeners para verificar se todos os nomes dos jogadores foram inseridos
        player1Name.textProperty().addListener((observable, oldValue, newValue) -> updateStatus());
        player2Name.textProperty().addListener((observable, oldValue, newValue) -> updateStatus());
        player3Name.textProperty().addListener((observable, oldValue, newValue) -> updateStatus());
        player4Name.textProperty().addListener((observable, oldValue, newValue) -> updateStatus());
    }

    private void startGame() {
        if (allPlayersReady()) {
            try {
                // Carrega o FXML do jogo
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Game.fxml"));
                Parent gameRoot = loader.load();
                
                // Obtém o controlador do GameController
                GameController gameController = loader.getController();
                
                // Passa os nomes dos jogadores para o GameController
                gameController.setPlayerNames(
                    player1Name.getText(),
                    player2Name.getText(),
                    player3Name.getText(),
                    player4Name.getText()
                );
                
                // Passa os avatares dos jogadores
                gameController.setPlayerAvatars(
                    player1Avatar.getImage(),
                    player2Avatar.getImage(),
                    player3Avatar.getImage(),
                    player4Avatar.getImage()
                );

                // Passa o número de rodadas selecionado para o GameController
                gameController.setMaxRounds(selectedRounds);

                // Define a nova cena
                Scene gameScene = new Scene(gameRoot);
                gameScene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
                
                Stage primaryStage = (Stage) playButton.getScene().getWindow();
                primaryStage.setScene(gameScene);
                primaryStage.setTitle("Linha do Tempo - Jogando");
                primaryStage.setFullScreen(true);
                primaryStage.setFullScreenExitHint(""); // Remove a dica de sair do modo tela cheia
                
            } catch (IOException e) {
                e.printStackTrace();
                statusLabel.setText("Erro ao iniciar o jogo.");
            }
        } else {
            statusLabel.setText("Status: Todos os jogadores devem inserir seus nomes.");
        }
    }

    private void showInstructions() {
        System.out.println("Mostrando instruções...");
    }

    private void showOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Options.fxml"));
            Parent root = loader.load();
            
            // Obtém o controlador do OptionsController
            OptionsController optionsController = loader.getController();
            // Passa o número de rodadas atual para o OptionsController
            optionsController.setSelectedRounds(selectedRounds);
            
            // Define uma callback para receber o valor atualizado
            optionsController.setOnRoundsSelected(newRounds -> selectedRounds = newRounds);

            Stage stage = new Stage();
            Scene optionsScene = new Scene(root);
            optionsScene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());

            stage.setScene(optionsScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();   
        }
    }

    private void exitGame() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void updateStatus() {
        if (allPlayersReady()) {
            statusLabel.setText("Status: Todos os jogadores prontos.");
        } else {
            statusLabel.setText("Status: Aguardando nomes dos jogadores...");
        }
    }

    private boolean allPlayersReady() {
        return !player1Name.getText().trim().isEmpty() &&
               !player2Name.getText().trim().isEmpty() &&
               !player3Name.getText().trim().isEmpty() &&
               !player4Name.getText().trim().isEmpty();
    }
}
