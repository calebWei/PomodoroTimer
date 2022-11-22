package caleb.pomodorotimer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Get font
        Font.loadFont(getClass().getResourceAsStream("/fonts/Dosis-Regular.ttf"), 0);
        // Load first scene
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/timer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 970, 650);
        stage.setTitle("Pomodoro Timer");
        // Load css stylesheet
        stage.setScene(scene);
        stage.show();

        // Ends all thread operations when the program is terminated
        stage.setOnCloseRequest(
                e -> {
                    Platform.exit();
                    System.exit(0);
                });
    }
    public static void main(String[] args) {
        launch();
    }
}