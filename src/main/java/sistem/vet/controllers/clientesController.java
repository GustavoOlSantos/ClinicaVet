package sistem.vet.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class clientesController {
	
	menuDataSaver menu = MainController.menu;
	
	@FXML
    public void ClickHistorico() {
    	menu.loadContent("historicoClientes.fxml", menu.classe); 
    }
}
