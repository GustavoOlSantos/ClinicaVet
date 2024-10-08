package sistem.vet.controllers;
/*
Put header here


 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {
    
    @FXML
    public Label lblOut;
    @FXML
    public VBox contentBox; // This should be the VBox where you want to load the content
    
    @FXML
    public void btnClickAction(ActionEvent event) {
        lblOut.setText("Hello World!");
    }
    
    @FXML
    public void ClickHome() {
        loadContent("home.fxml");
    }

    @FXML
    public void ClickLista() {
        loadContent("listaClientes.fxml");
    }

    @FXML
    public void ClickHistorico() {
        loadContent("historicoClientes.fxml");
    }

    private void loadContent(String fxmlFile) {
        try {
            // Clear existing content
            contentBox.getChildren().clear();
            
            // Load new content
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            VBox newContent = loader.load();
            contentBox.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	 loadContent("home.fxml");
    }    
}
