package caleb.pomodorotimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WorkController {
    @FXML
    private Label timerClock, startLabel;
    @FXML
    private Rectangle startButton, skipButton, statButton, settingButton;
    private Timeline timeline;
    private int time = 1800;

    @FXML
    // Start the timer when the start button is pressed
    private void onStartButton() {
        // If the timer hasn't started, start the timer
        // If the timer is running, pause the timer
        // If the timer is paused, continue the timer
        // Set timelines for timer and predict
        System.out.println("start timer");
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> decrementTime()));
        timeline.setCycleCount(time);
        timeline.play();
    }

    private void decrementTime(){
        timerClock.setText(secondsToHMS(time));
        time--;
    }

    private String secondsToHMS(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}