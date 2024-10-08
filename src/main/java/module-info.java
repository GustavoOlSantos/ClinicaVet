module sistem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires java.sql;
    opens sistem.vet to javafx.fxml;
    exports sistem.vet;
    exports sistem.vet.controllers;
}