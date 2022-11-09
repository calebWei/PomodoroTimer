package caleb.pomodorotimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class WorkController {
    @FXML
    private Label timerClock, startLabel, statLabel, settingLabel;
    @FXML
    private Rectangle skipButton, statButton, settingButton;
    private Timeline timeline;
    private int time = 1800;
    enum State {
        STOP,
        RUN,
        PAUSE
    }
    private State state = State.STOP;

    @FXML
    // Start the timer when the start button is pressed
    private void onStartButton() {
        if (state == State.STOP){
            // If the timer hasn't started, start the timer
            System.out.println("start timer");
            timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> decrementTime()));
            timeline.setCycleCount(time);
            timeline.play();
            state = State.RUN;
            // Enable/ Disable buttons
            skipButton.setDisable(false);
            statButton.setDisable(true);
            statButton.setVisible(false);
            statLabel.setVisible(false);
            settingButton.setDisable(true);
            settingButton.setVisible(false);
            settingLabel.setVisible(false);
            startLabel.setText("Pause");
        } else if (state == State.RUN) {
            // If the timer is running, pause the timer
            System.out.println("pause timer");
            timeline.pause();
            state = State.PAUSE;
            startLabel.setText("Continue");
        } else if (state == State.PAUSE) {
            // If the timer is paused, continue the timer
            System.out.println("start timer");
            timeline.play();
            state = State.RUN;
            startLabel.setText("Pause");
        }
    }

    private void decrementTime(){
        time--;
        timerClock.setText(secondsToHMS(time));
    }

    private String secondsToHMS(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}