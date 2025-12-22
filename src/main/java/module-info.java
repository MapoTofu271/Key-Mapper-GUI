module org.example.model {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

    opens org.example.key_mapper_gui to javafx.fxml;
    exports org.example.key_mapper_gui;
    exports org.example.model;
    exports org.example.key_mapper_gui.viewModel;

}

