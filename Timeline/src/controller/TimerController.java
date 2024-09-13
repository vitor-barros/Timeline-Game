package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class TimerController {

    private Timeline timeline;  // Objeto para controlar o tempo
    private ProgressBar progressBarTimer; // barra de progresso que vai atualizar 
    private Label timerLabel; // vai exibir o tempo restante
    private Runnable onTimerComplete; // um callback que vai executar quando acabar o tempo

    public void startRoundTimer(int roundTime, Runnable onTimerComplete) {
        // Primeiro, pare qualquer timer anterior que possa estar rodando
        if (timeline != null) {
            timeline.stop();
        }

        this.onTimerComplete = onTimerComplete;

        // Definir o progresso inicial e o texto do tempo
        progressBarTimer.setProgress(1.0); // 100% de tempo restante
        timerLabel.setText(Integer.toString(roundTime));

        // Atualizar a cor da barra de progresso no início (progresso = 1.0)
        updateProgressBarColor(1.0);

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                int remainingTime = Integer.parseInt(timerLabel.getText()) - 1;
                timerLabel.setText(Integer.toString(remainingTime));

                // Calcular o progresso restante (de 1.0 a 0.0)
                double progress = remainingTime / (double) roundTime;

                // Atualizar o progresso da barra
                progressBarTimer.setProgress(progress);

                // Atualizar a cor da barra de progresso com base no tempo restante
                updateProgressBarColor(progress);

                // Se o tempo acabar
                if (remainingTime <= 0) {
                    timeline.stop();
                    if (onTimerComplete != null) {
                        onTimerComplete.run(); // Executa o callback quando o tempo acaba
                    }
                }
            })
        );

        timeline.setCycleCount(roundTime); // Rodar durante o tempo definido
        timeline.play();
    }

    // Função para alterar a cor da barra de progresso de acordo com o progresso restante
    public void updateProgressBarColor(double progress) {
        // Converter o progresso (0.0 a 1.0) para uma cor interpolada entre verde, amarelo e vermelho
        String color = getGradientColor(progress);
        progressBarTimer.setStyle("-fx-accent: " + color + ";");
    }

    // Função auxiliar para gerar a cor com base no progresso
    private String getGradientColor(double progress) {
        int red, green;
        
        if (progress > 0.5) {
            // Interpolar de verde para amarelo (0.5 a 1.0) -> mais verde, menos vermelho
            red = (int) (255 * (1 - progress) * 2);  // vermelho vai de 0 a 255
            green = 255;  // verde fica em 255 até metade do tempo
        } else {
            // Interpolar de amarelo para vermelho (0.0 a 0.5) -> mais vermelho, menos verde
            red = 255;  // vermelho vai até 255
            green = (int) (255 * progress * 2);  // verde vai diminuindo conforme o tempo passa
        }

        // Retornar a cor no formato RGB hexadecimal
        return String.format("#%02X%02X00", red, green);  // azul = 00 porque não usamos azul
    }

    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBarTimer = progressBar;
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }
}
