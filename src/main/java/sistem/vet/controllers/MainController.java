package sistem.vet.controllers;
/*
Put header here


 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {
    
    @FXML
    public VBox contentBox; // This should be the VBox where you want to load the content
    @FXML
    public Button PI;
    @FXML
    public Button LC;
    @FXML
    public Button HC;
    
    @FXML
    public void ClickHome() {
        loadContent("home.fxml");
        
        if(!PI.getStyleClass().contains("menu-itemss")) {
        	PI.getStyleClass().remove("menu-items"); 
	        PI.getStyleClass().add("menu-itemss");   
	        
	        if(LC.getStyleClass().contains("menu-itemss")) {
	        	LC.getStyleClass().remove("menu-itemss"); 
	        	LC.getStyleClass().add("menu-items");   
	        }
	        
	        if(HC.getStyleClass().contains("menu-itemss")) {
	        	HC.getStyleClass().remove("menu-itemss"); 
	        	HC.getStyleClass().add("menu-items");   
	        }
        }
    }

    @FXML
    public void ClickLista() {
        loadContent("listaClientes.fxml");
        
        if(!LC.getStyleClass().contains("menu-itemss")) {
        	LC.getStyleClass().remove("menu-items"); 
	        LC.getStyleClass().add("menu-itemss");   
	        
	        if(PI.getStyleClass().contains("menu-itemss")) {
	        	PI.getStyleClass().remove("menu-itemss"); 
	        	PI.getStyleClass().add("menu-items");   
	        }
	        
	        if(HC.getStyleClass().contains("menu-itemss")) {
	        	HC.getStyleClass().remove("menu-itemss"); 
	        	HC.getStyleClass().add("menu-items");   
	        }
        }
    }

    @FXML
    public void ClickHistorico() {
        loadContent("historicoClientes.fxml");
        
        if(!HC.getStyleClass().contains("menu-itemss")) {
        	HC.getStyleClass().remove("menu-items"); 
	        HC.getStyleClass().add("menu-itemss");   
	        
	        if(PI.getStyleClass().contains("menu-itemss")) {
	        	PI.getStyleClass().remove("menu-itemss"); 
	        	PI.getStyleClass().add("menu-items");   
	        }
	        
	        if(LC.getStyleClass().contains("menu-itemss")) {
	        	LC.getStyleClass().remove("menu-itemss"); 
	        	LC.getStyleClass().add("menu-items");   
	        }
        }
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
