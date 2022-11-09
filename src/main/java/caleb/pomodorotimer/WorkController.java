package caleb.pomodorotimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkController {
    @FXML
    private Label timerClock, startLabel, statLabel, settingLabel;
    @FXML
    private Rectangle skipButton, statButton, settingButton;
    private Timeline timeline;
    private int studyTime = 1800;
    private int breakTime = 600;
    private int countTime = studyTime;
    private boolean isStudy = true;
    Duration timeStamp;

    enum State {
        STOP,
        RUN,
        PAUSE
    }
    private State state = State.STOP;

    @FXML
    // Start the timer when the start button is pressed
    private void onStartButton() {

        switch(state) {
            case STOP:
                // If the timer hasn't started, start the timer
                System.out.println("start timer");
                timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> decrementTime()));
                timeline.setCycleCount(countTime);
                timeline.setOnFinished(event -> endTimer());
                timeline.playFromStart();
                state = State.RUN;
                // Enable/Disable buttons
                skipButton.setDisable(false);
                statButton.setDisable(true);
                statButton.setVisible(false);
                statLabel.setVisible(false);
                settingButton.setDisable(true);
                settingButton.setVisible(false);
                settingLabel.setVisible(false);
                startLabel.setText("Pause");
                break;
            case RUN:
                // If the timer is running, pause the timer
                System.out.println("pause timer");
                timeline.pause();
                state = State.PAUSE;
                startLabel.setText("Continue");
                break;
            case PAUSE:
                // If the timer is paused, continue the timer
                System.out.println("start timer");
                timeline.play();
                state = State.RUN;
                startLabel.setText("Pause");
                break;
        }
    }

    private void decrementTime(){
        countTime--;
        timerClock.setText(secondsToHMS(countTime));
    }

    @FXML
    private void endTimer() {
        timeline.stop();
        isStudy = !isStudy;
        // Depending on which mode the user is on, load either study time or break time.
        if (isStudy) {
            // Transition to study background
            countTime = studyTime;
        } else {
            // Transition to break background
            countTime = breakTime;
        }
        timerClock.setText(secondsToHMS(countTime));

        // Enable/Disable buttons
        state = State.STOP;
        startLabel.setText("Start");
        skipButton.setDisable(true);
        statButton.setDisable(false);
        statButton.setVisible(true);
        statLabel.setVisible(true);
        settingButton.setDisable(false);
        settingButton.setVisible(true);
        settingLabel.setVisible(true);
    }

    private String secondsToHMS(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}