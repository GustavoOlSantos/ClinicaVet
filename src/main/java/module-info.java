module sistem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires javafx.graphics;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.devicons;

    // Pacotes abertos para FXML
    //opens sistem.model.entities to javafx.base;
    opens sistem.model.entities.dto to javafx.base;


    opens sistem.app to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    opens sistem.controller to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    opens sistem.model.dao.interfaces to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    opens sistem.model.dao.impl to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    opens sistem.model.db to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    opens sistem.model.entities to javafx.base, javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    opens sistem.model.enums to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    opens sistem.model.exceptions to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;

    exports sistem.app;
    exports sistem.controller;
    exports sistem.model.dao.impl;
    exports sistem.model.dao.interfaces;
    exports sistem.model.db;
    exports sistem.model.entities;
    exports sistem.model.enums;
    exports sistem.model.exceptions;
}
