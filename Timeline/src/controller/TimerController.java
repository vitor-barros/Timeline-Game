package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class TimerController {

    private Timeline timeline;
    private ProgressBar progressBarTimer;
    private Label timerLabel;
    private Runnable onTimerComplete;

    public void startRoundTimer(int roundTime, Runnable onTimerComplete) {
        if (timeline != null) {
            timeline.stop();
        }

        this.onTimerComplete = onTimerComplete;

        // Definir o progresso inicial e o texto do tempo
        progressBarTimer.setProgress(1.0);
        timerLabel.setText(Integer.toString(roundTime));

        // Atualizar a cor da barra de progresso e do texto no início (progresso = 1.0)
        updateProgressBarAndTextColor(1.0);

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                int remainingTime = Integer.parseInt(timerLabel.getText()) - 1;
                timerLabel.setText(Integer.toString(remainingTime));

                double progress = remainingTime / (double) roundTime;

                // Atualizar o progresso da barra e a cor
                progressBarTimer.setProgress(progress);
                updateProgressBarAndTextColor(progress);

                if (remainingTime <= 0) {
                    timeline.stop();
                    if (onTimerComplete != null) {
                        onTimerComplete.run();
                    }
                }
            })
        );

        timeline.setCycleCount(roundTime);
        timeline.play();
    }

    // Função para alterar a cor da barra de progresso e do texto de acordo com o progresso restante
    public void updateProgressBarAndTextColor(double progress) {
        String color = getGradientColor(progress);

        // Alterar a cor da barra de progresso
        progressBarTimer.setStyle("-fx-accent: " + color + ";");

        // Alterar a cor do texto do cronômetro
        timerLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    private String getGradientColor(double progress) {
        int red, green;

        if (progress > 0.5) {
            red = (int) (255 * (1 - progress) * 2);
            green = 255;
        } else {
            red = 255;
            green = (int) (255 * progress * 2);
        }

        return String.format("#%02X%02X00", red, green);
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
