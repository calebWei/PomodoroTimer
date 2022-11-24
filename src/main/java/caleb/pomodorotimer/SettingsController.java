package caleb.pomodorotimer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;

public class SettingsController implements Initializable {
    @FXML
    private Rectangle background;
    @FXML
    private Label studyLabel, shortLabel, longLabel, intervalLabel, alarmLabel, beepsLabel, themeLabel;
    private final Properties prop = new Properties();

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

}
