module caleb.pomodorotimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.media;

    opens caleb.pomodorotimer to javafx.fxml;
    exports caleb.pomodorotimer;
}