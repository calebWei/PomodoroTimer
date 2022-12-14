package caleb.pomodorotimer;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class TimerController implements Initializable {
    @FXML
    private Label titleLabel, timerClock, startLabel, statLabel, settingLabel;
    @FXML
    private Rectangle skipButton, statButton, settingButton, background;
    private Timeline timeline;
    private int studyTime, shortBreakTime, longBreakTime, longBreakInterval;
    private int countTime;
    private String studyColor, shortColor, longColor, currentColor;
    private boolean isStudy = true;
    private int breakIntervalCnt = 0;
    private int alarmBeeps;
    private enum State {
        STOP, RUN, PAUSE
    }
    private State state = State.STOP;
    private MediaPlayer alarmPlayer, projectorPlayer;
    private final Properties prop = new Properties();

    /**
     * Runs when scene starts, set up
     *
     * @param url default
     * @param resourceBundle default
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Read config file
        try {
            FileInputStream config = new FileInputStream("src/main/resources/config.properties");
            prop.load(config);
            // Set up time values based on config
            studyTime = Integer.parseInt(prop.getProperty("studyTime"));
            shortBreakTime = Integer.parseInt(prop.getProperty("shortBreakTime"));
            longBreakTime = Integer.parseInt(prop.getProperty("longBreakTime"));
            longBreakInterval = Integer.parseInt(prop.getProperty("longBreakInterval"));
            countTime = studyTime;
            alarmBeeps = Integer.parseInt(prop.getProperty("alarmBeeps"));
            timerClock.setText(secondsToHMS(countTime));
            // read colour theme values
            studyColor = prop.getProperty("studyColor");
            shortColor = prop.getProperty("shortColor");
            longColor = prop.getProperty("longColor");
            currentColor = studyColor;
            // Set up audio
            Media projectorSound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/projector-button-push.mp3")).toExternalForm());
            projectorPlayer = new MediaPlayer(projectorSound);
            Media alarmSound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/" + prop.getProperty("alarmType") + ".mp3")).toExternalForm());
            alarmPlayer = new MediaPlayer(alarmSound);
            // Disable the skip button
            skipButton.setDisable(true);
            skipButton.setOpacity(0.8);
            // Sets the timerClock label and title label at beginning
            timerClock.setText(secondsToHMS(studyTime));
            titleLabel.setText("Welcome, " + prop.getProperty("name").replace("\"", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs when the start button is pressed, depending on the state of the timer, the method will pause or
     * unpause the timer.
     */
    @FXML
    // Start the timer when the start button is pressed
    private void onStartButton() {

        switch (state) {
            case STOP -> {
                // If the timer hasn't started, start the timer
                // Play button sound
                if (projectorPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    projectorPlayer.stop();
                }
                projectorPlayer.play();
                // start timer
                timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> decrementTime()));
                timeline.setCycleCount(countTime);
                timeline.setOnFinished(event -> endTimer());
                timeline.playFromStart();
                state = State.RUN;
                // Enable/Disable buttons
                skipButton.setDisable(false);
                skipButton.setOpacity(1);
                statButton.setDisable(true);
                statButton.setVisible(false);
                statLabel.setVisible(false);
                settingButton.setDisable(true);
                settingButton.setVisible(false);
                settingLabel.setVisible(false);
                startLabel.setText("Pause");
            }
            case RUN -> {
                // If the timer is running, pause the timer
                timeline.pause();
                state = State.PAUSE;
                startLabel.setText("Continue");
            }
            case PAUSE -> {
                // If the timer is paused, continue the timer
                timeline.play();
                state = State.RUN;
                startLabel.setText("Pause");
            }
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
        // sound the alarm
        if (alarmPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            alarmPlayer.stop();
        }
        alarmPlayer.setCycleCount(alarmBeeps);
        alarmPlayer.play();
        // stop the countdown
        timeline.stop();
        isStudy = !isStudy;
        // Depending on which mode the user is on, load either study time or break time.
        if (isStudy) {
            // Transition to study background
            setStudy();
        } else {
            // Transition to break background, based on longBreakInterval (-1 means no long breaks)
            if (longBreakInterval == -1) {
                setShortBreak();
            } else if (breakIntervalCnt == longBreakInterval){
                setLongBreak();
            } else {
                setShortBreak();
            }
        }
        timerClock.setText(secondsToHMS(countTime));

        // Enable/Disable buttons
        state = State.STOP;
        startLabel.setText("Start");
        skipButton.setDisable(true);
        skipButton.setOpacity(0.8);
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
     * @param timeInSeconds integer representing number of seconds
     * @return HH:MM:SS
     */
    public static String secondsToHMS(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * This method runs when skip button is pressed, unique to endTimer() because it plays a sound.
     */
    @FXML
    private void onSkip() {
        // Play sound
        if (projectorPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            projectorPlayer.stop();
        }
        projectorPlayer.play();
        endTimer();
    }

    /**
     * This method transitions the scene to start of study time
     */
    private void setStudy() {
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.web(currentColor), Color.web(studyColor));
        fillTransition.play();
        timerClock.setTextFill(Paint.valueOf("BLACK"));
        countTime = studyTime;
        currentColor = studyColor;
    }

    /**
     * This method transitions the scene to start of short break
     */
    private void setShortBreak() {
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.web(currentColor), Color.web(shortColor));
        fillTransition.play();
        timerClock.setTextFill(Paint.valueOf("WHITE"));
        countTime = shortBreakTime;
        breakIntervalCnt++;
        currentColor = shortColor;
    }

    /**
     * This method transitions the scene to start of long break
     */
    private void setLongBreak() {
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.web(currentColor), Color.web(longColor));
        fillTransition.play();
        timerClock.setTextFill(Paint.valueOf("WHITE"));
        countTime = longBreakTime;
        breakIntervalCnt = 0;
        currentColor = longColor;
    }

    /**
     * switch scene to settings
     *
     * @param event triggered by clicking the rectangle button
     * @throws IOException for load()
     */
    @FXML
    private void onSettings(MouseEvent event) throws IOException {
        // End any sound
        alarmPlayer.stop();
        projectorPlayer.stop();
        // Get class constructor and initiate
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settings.fxml"));
        Parent root = loader.load();
        SettingsController settingsController = loader.getController();
        // set background color for settings
        settingsController.setBackgroundColor(currentColor);

        // Get scene
        Rectangle rectangle = (Rectangle) event.getSource();
        Scene scene = rectangle.getScene();
        // Change scene
        scene.setRoot(root);
    }

}