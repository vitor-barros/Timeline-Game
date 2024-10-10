package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Player;
import java.util.List;

public class StatsController {

    @FXML
    private GridPane statsGrid;
    

    // Método para inicializar a grade de estatísticas com base nos jogadores e número de rodadas
    public void initializeStats(List<Player> players, int numRounds) {
    	
        // Limpar o GridPane (caso seja reutilizado)
        statsGrid.getChildren().clear();
        
        // Log para verificar a quantidade de rodadas e jogadores
        System.out.println("Número de jogadores: " + players.size());
        System.out.println("Número de rodadas: " + numRounds);

        // Adicionar títulos das colunas: "Jogador" e "Rodada 1", "Rodada 2", ..., "Total"
        statsGrid.add(new Label("Jogadores"), 0, 0);

        // Criar as colunas dinamicamente de acordo com o número de rodadas
        for (int round = 0; round < numRounds; round++) {
            Label roundLabel = new Label("Rodada " + (round + 1));
            statsGrid.add(roundLabel, round + 1, 0); // Adicionando rodadas na linha 0 (header)
            System.out.println("Coluna criada: Rodada " + (round + 1));
        }

        // Adicionar a coluna "Total"
        statsGrid.add(new Label("Total"), numRounds + 1, 0);
        System.out.println("Coluna 'Total' criada");

        // Preencher o GridPane com os dados dos jogadores
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            
            // Log do jogador atual
            System.out.println("Processando jogador: " + player.getName());

            // Nome do jogador na primeira coluna
            Label playerNameLabel = new Label(player.getName());
            statsGrid.add(playerNameLabel, 0, i + 1);

            // preenche as rodadas (garantir que não tente acessar mais rodadas do que existem)
            for (int j = 0; j < Math.min(numRounds, player.getGuesses().size()); j++) {
                System.out.println("Jogador: " + player.getName() + ", Rodada " + (j + 1) + ": " + player.getGuesses().get(j));
                Label roundScoreLabel = new Label(String.valueOf(player.getGuesses().get(j)));
                statsGrid.add(roundScoreLabel, j + 1, i + 1); // Adicionando os palpites nas colunas corretas
            }

            // Caso o jogador tenha menos rodadas do que o número máximo de rodadas definidas
            for (int j = player.getGuesses().size(); j < numRounds; j++) {
                System.out.println("Jogador: " + player.getName() + ", Rodada " + (j + 1) + ": N/A");
                statsGrid.add(new Label("N/A"), j + 1, i + 1);  // Colocar "N/A" para rodadas não jogadas
            }

            // Adicionar a pontuação total na última coluna
            Label totalScoreLabel = new Label(String.valueOf(player.getPoints()));
            statsGrid.add(totalScoreLabel, numRounds + 1, i + 1);
            System.out.println("Pontuação total de " + player.getName() + ": " + player.getPoints());
        }
    }

}