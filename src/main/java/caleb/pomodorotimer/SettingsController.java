package caleb.pomodorotimer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SettingsController implements Initializable {
    @FXML
    private Rectangle background;
    @FXML
    private Label studyLabel, shortLabel, longLabel, intervalLabel, alarmLabel, beepsLabel, themeLabel;
    private final Properties prop = new Properties();
    private MediaPlayer tickPlayer;
    private ArrayList<String> alarmType = new ArrayList<String>(Arrays.asList("Alarm1", "Alarm2"));

    /**
     * Display current settings on start of scene
     *
     * @param url default
     * @param resourceBundle default
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // load current settings
        try {
            FileInputStream config = new FileInputStream("src/main/resources/config.properties");
            prop.load(config);
            studyLabel.setText(TimerController.secondsToHMS(Integer.parseInt(prop.getProperty("studyTime"))));
            shortLabel.setText(TimerController.secondsToHMS(Integer.parseInt(prop.getProperty("shortBreakTime"))));
            longLabel.setText(TimerController.secondsToHMS(Integer.parseInt(prop.getProperty("longBreakTime"))));
            intervalLabel.setText(String.valueOf(Integer.parseInt(prop.getProperty("longBreakInterval"))));
            alarmLabel.setText(prop.getProperty("alarmType").replaceAll("\"", ""));
            beepsLabel.setText(String.valueOf(Integer.parseInt(prop.getProperty("alarmBeeps"))));
            themeLabel.setText(prop.getProperty("theme").replaceAll("\"", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Setup audio
        Media tickSound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/button-tick.mp3")).toExternalForm());
        tickPlayer = new MediaPlayer(tickSound);
    }

    /**
     * Change alarm type
     */
    @FXML
    private void incAlarm() throws IOException {
        // changes alarm type
        playTickSound();
        String alarm = prop.getProperty("alarmType");
        int index = alarmType.indexOf(alarm);
        if (index == alarmType.size()-1) {
            index = 0;
        } else {
            index++;
        }
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("alarmType", alarmType.get(index));
        prop.store(saveFile, null);
        alarmLabel.setText(alarmType.get(index));
    }

    /**
     * Change alarm type
     */
    @FXML
    private void decAlarm() throws IOException {
        //changes alarm type
        playTickSound();
        String alarm = prop.getProperty("alarmType");
        int index = alarmType.indexOf(alarm);
        if (index == 0) {
            index = alarmType.size()-1;
        } else {
            index--;
        }
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("alarmType", alarmType.get(index));
        prop.store(saveFile, null);
        alarmLabel.setText(alarmType.get(index));
    }

    /**
     * Change theme
     */
    @FXML
    private void incTheme() {
        //TODO changes theme
        playTickSound();
    }

    /**
     * Change alarm type
     */
    @FXML
    private void decTheme() {
        //TODO changes Theme
        playTickSound();
    }

    /**
     * Increment the study timer by 1 minute.
     */
    @FXML
    private void incStudy() throws IOException {
        playTickSound();
        int time = Integer.parseInt(prop.getProperty("studyTime"));
        time += 60;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("studyTime", String.valueOf(time));
        prop.store(saveFile, null);
        studyLabel.setText(TimerController.secondsToHMS(time));
    }

    /**
     * Decrement the study timer by 1 minute.
     */
    @FXML
    private void decStudy() throws IOException {
        playTickSound();
        int time = Integer.parseInt(prop.getProperty("studyTime"));
        if (time < 60) {
            return;
        }
        time -= 60;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("studyTime", String.valueOf(time));
        prop.store(saveFile, null);
        studyLabel.setText(TimerController.secondsToHMS(time));
    }

    /**
     * Increment the short break timer by 1 minute.
     */
    @FXML
    private void incShort() throws IOException {
        playTickSound();
        int time = Integer.parseInt(prop.getProperty("shortBreakTime"));
        time += 60;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("shortBreakTime", String.valueOf(time));
        prop.store(saveFile, null);
        shortLabel.setText(TimerController.secondsToHMS(time));
    }

    /**
     * Decrement the short break timer by 1 minute.
     */
    @FXML
    private void decShort() throws IOException {
        playTickSound();
        int time = Integer.parseInt(prop.getProperty("shortBreakTime"));
        if (time < 60) {
            return;
        }
        time -= 60;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("shortBreakTime", String.valueOf(time));
        prop.store(saveFile, null);
        shortLabel.setText(TimerController.secondsToHMS(time));
    }

    /**
     * Increment the long break timer by 1 minute.
     */
    @FXML
    private void incLong() throws IOException {
        playTickSound();
        int time = Integer.parseInt(prop.getProperty("longBreakTime"));
        time += 60;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("longBreakTime", String.valueOf(time));
        prop.store(saveFile, null);
        longLabel.setText(TimerController.secondsToHMS(time));
    }

    /**
     * Decrement the long break timer by 1 minute.
     */
    @FXML
    private void decLong() throws IOException {
        playTickSound();
        int time = Integer.parseInt(prop.getProperty("longBreakTime"));
        if (time < 60) {
            return;
        }
        time -= 60;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("longBreakTime", String.valueOf(time));
        prop.store(saveFile, null);
        longLabel.setText(TimerController.secondsToHMS(time));
    }

    /**
     * Increment the long break interval by 1.
     */
    @FXML
    private void incInterval() throws IOException {
        playTickSound();
        int interval = Integer.parseInt(prop.getProperty("longBreakInterval"));
        interval++;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("longBreakInterval", String.valueOf(interval));
        prop.store(saveFile, null);
        intervalLabel.setText(String.valueOf(interval));
    }

    /**
     * Decrement the long break interval by 1.
     */
    @FXML
    private void decInterval() throws IOException {
        playTickSound();
        int interval = Integer.parseInt(prop.getProperty("longBreakInterval"));
        if (interval == -1) {
            return;
        }
        interval--;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("longBreakInterval", String.valueOf(interval));
        prop.store(saveFile, null);
        intervalLabel.setText(String.valueOf(interval));
    }

    /**
     * Increment the alarm beeps by 1.
     */
    @FXML
    private void incBeep() throws IOException {
        playTickSound();
        int beeps = Integer.parseInt(prop.getProperty("alarmBeeps"));
        beeps++;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("alarmBeeps", String.valueOf(beeps));
        prop.store(saveFile, null);
        beepsLabel.setText(String.valueOf(beeps));
    }

    /**
     * Decrement the alarm beeps by 1.
     */
    @FXML
    private void decBeep() throws IOException {
        playTickSound();
        int beeps = Integer.parseInt(prop.getProperty("alarmBeeps"));
        if (beeps == 0) {
            return;
        }
        beeps--;
        FileOutputStream saveFile = new FileOutputStream("src/main/resources/config.properties");
        prop.setProperty("alarmBeeps", String.valueOf(beeps));
        prop.store(saveFile, null);
        beepsLabel.setText(String.valueOf(beeps));
    }

    /**
     * switch scene to timer
     *
     * @param event triggered by clicking the rectangle button
     * @throws IOException for load()
     */
    @FXML
    private void onReturn(MouseEvent event) throws IOException {
        // Get scene
        Rectangle rectangle = (Rectangle) event.getSource();
        Scene scene = rectangle.getScene();
        // Change scene
        scene.setRoot(new FXMLLoader(App.class.getResource("/fxml/timer.fxml")).load());
    }

    /**
     * Set color for settings background
     *
     * @param color for settings
     */
    public void setBackgroundColor(String color) {
        background.setFill(Color.valueOf(color));
    }

    /**
     * Play tick sound once for setting buttons.
     */
    private void playTickSound() {
        if (tickPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            tickPlayer.stop();
        }
        tickPlayer.play();
    }
}
