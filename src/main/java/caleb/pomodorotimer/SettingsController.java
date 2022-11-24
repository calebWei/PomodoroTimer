package caleb.pomodorotimer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class SettingsController {
    @FXML
    private Rectangle background;



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
