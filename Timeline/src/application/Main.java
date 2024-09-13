package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega o FXML do menu e o controlador associado
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
            Parent root = loader.load();
            
            // Define a cena e a exibe
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Jogo Linha do Tempo");
            
            // Ativa o modo tela cheia
            primaryStage.setFullScreen(true); 
            primaryStage.setFullScreenExitHint(""); // Remove a dica de sair do modo tela cheia

            // Obtém a resolução da tela
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            
            // Define valores mínimos ajustados conforme a resolução da tela
            double minWidth = Math.min(800, screenWidth);
            double minHeight = Math.min(800, screenHeight);
      
            primaryStage.setMinWidth(minWidth);
            primaryStage.setMinHeight(minHeight);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}