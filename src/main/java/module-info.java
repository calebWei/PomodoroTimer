module caleb.pomodorotimer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens caleb.pomodorotimer to javafx.fxml;
    exports caleb.pomodorotimer;
}