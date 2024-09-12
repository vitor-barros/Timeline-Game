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

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                int remainingTime = Integer.parseInt(timerLabel.getText()) - 1;
                timerLabel.setText(Integer.toString(remainingTime));

                // atualizar o progresso da barra
                progressBarTimer.setProgress(remainingTime / (double) roundTime);

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
