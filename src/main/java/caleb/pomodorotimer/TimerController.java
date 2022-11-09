package caleb.pomodorotimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TimerController {
    @FXML
    private Label timerClock, startLabel, statLabel, settingLabel;
    @FXML
    private Rectangle skipButton, statButton, settingButton;
    private Timeline timeline;
    private int studyTime = 1800, breakTime = 600;
    private int countTime = studyTime;
    private boolean isStudy = true;
    enum State {
        STOP, RUN, PAUSE
    }
    private State state = State.STOP;

    /**
     * This method runs when the start button is pressed, depending on the state of the timer, the method will pause or
     * unpause the timer.
     */
    @FXML
    // Start the timer when the start button is pressed
    private void onStartButton() {

        switch(state) {
            case STOP:
                // If the timer hasn't started, start the timer
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
                timeline.pause();
                state = State.PAUSE;
                startLabel.setText("Continue");
                break;
            case PAUSE:
                // If the timer is paused, continue the timer
                timeline.play();
                state = State.RUN;
                startLabel.setText("Pause");
                break;
        }
    }

    /**
     * This method decrements the time and updates the timerClock
     */
    private void decrementTime(){
        countTime--;
        timerClock.setText(secondsToHMS(countTime));
    }

    /**
     * This method is called when the timerClock reaches 0, or when the skip button is pressed, it will stop the timer,
     * and transition the mode to break or study mode by resetting the timer and changing the background.
     */
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

    /**
     * This method takes an integer representing the total amount of seconds, and returns a string format of HH:MM:SS
     * that represents the time.
     *
     * @param timeInSeconds
     * @return HH:MM:SS
     */
    private String secondsToHMS(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}