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
    opens sistem.vet to javafx.fxml;
    opens sistem.entities to javafx.base; // Isso permite que javafx.base acesse o pacote sistem.entities
    exports sistem.vet;
    exports sistem.vet.controllers;
}