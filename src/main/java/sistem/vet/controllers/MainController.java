package sistem.vet.controllers;
/*
Put header here


 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainController implements Initializable {
    
	@FXML
    public ImageView LOGO;
    @FXML
    public VBox contentBox; //=> Divisão que deve receber os conteúdos atualizáveis
    @FXML
    public HBox PI;			//=> Pagina Inicial
    @FXML
    public HBox LC;			//=> Lista de Clientes
    @FXML
    public HBox HC;			//=> Histórico de Clientes
    @FXML
    public HBox MenuBox;	//=> Container do Menu
    @FXML
    public VBox leftMenu; //=> Menu Lateral
    private boolean isDisplay = true; //=> Estado do menu
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	loadContent("home.fxml"); //=> Carrega o Conteúdo da Página Inicial
    							  //=> Path, Width, Height, preserveRatio, Smooth
    	LOGO.setImage(new Image("file:src/main/resources/imgs/app-icon.png", 30, 30, true, true));
    	toggleMenu();
    } 
    
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
    
    @FXML
    public void ClickMenu() {
    	
    	// Define a largura de destino com base no estado atual
        double targetWidth = isDisplay ? 65 : 240;
        
        // Crie um Timeline para animar a mudança de largura
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(leftMenu.prefWidthProperty(), targetWidth, Interpolator.EASE_BOTH);
        KeyValue keyValue2 = new KeyValue(leftMenu.minWidthProperty(), targetWidth, Interpolator.EASE_BOTH);
        KeyValue keyValue3 = new KeyValue(leftMenu.maxWidthProperty(), targetWidth, Interpolator.EASE_BOTH);
        
        List<KeyFrame> keys = new ArrayList<KeyFrame>();
        
        keys.add(new KeyFrame(Duration.millis(300), keyValue)); // Duração da animação
        keys.add(new KeyFrame(Duration.millis(300), keyValue2)); // Duração da animação
        keys.add(new KeyFrame(Duration.millis(300), keyValue3)); // Duração da animação
        
        timeline.getKeyFrames().addAll(keys);
        timeline.play(); // Inicie a animação
        toggleMenu();
        
//        // Toggle visibility
//        if (isDisplay) {
//            leftMenu.setPrefWidth(65); 
//            leftMenu.setMinWidth(65);
//            leftMenu.setMaxWidth(65);      
//            
//            toggleMenu();
//        } 
//        else {
//            leftMenu.setPrefWidth(350); // Show the menu by setting it back to its width
//            leftMenu.setMinWidth(250);
//            leftMenu.setMaxWidth(450);
//            
//            toggleMenu();
//        }
        
        isDisplay = !isDisplay; // Update the menu state
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

    private void toggleMenu() {
    	MenuBox.getChildren().forEach(node -> isLabel(node, isDisplay));
        PI.getChildren().forEach(node -> isLabel(node, isDisplay));
        LC.getChildren().forEach(node -> isLabel(node, isDisplay));
        HC.getChildren().forEach(node -> isLabel(node, isDisplay));
        
        Rectangle clip = new Rectangle(leftMenu.getPrefWidth(), leftMenu.getHeight());
        leftMenu.setClip(clip);

        //=> Ajuste dinâmico do tamanho do clip
        leftMenu.widthProperty().addListener((obs, oldVal, newVal) -> clip.setWidth(newVal.doubleValue()));
        leftMenu.heightProperty().addListener((obs, oldVal, newVal) -> clip.setHeight(newVal.doubleValue()));
    }

   private void isLabel(Node node, boolean isDisplay) {
    	
    	if (node instanceof Label && isDisplay) {
            node.setVisible(false); 
            node.setOpacity(0);
            node.prefWidth(0);
        }
    	
    	else if (node instanceof Label && !isDisplay) {
            node.setVisible(true);  
            node.setOpacity(1);
            node.prefWidth(200);
        }
    	
    	else {
    		return;
    	}
    }
}
