module sistem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires java.sql;
	requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.devicons;
	requires javafx.graphics;   
    opens sistem.app to javafx.fxml;
    opens sistem.controller to javafx.fxml;
    opens sistem.model.entities to javafx.base; // Isso permite que javafx.base acesse o pacote sistem.model.entities
    opens sistem.model.entities.dto to javafx.base; // Isso permite que javafx.base acesse o pacote sistem.model.entities
    exports sistem.app;
    exports sistem.controller;
}