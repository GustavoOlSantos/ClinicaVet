package sistem.vet.controllers;

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
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainController implements Initializable {
    
	@FXML
    public ImageView LOGO;
    @FXML
    public VBox contentBox; //=> Div que deve receber os conteúdos atualizáveis
    @FXML
    public HBox PI;			//=> Pagina Inicial
    @FXML
    public HBox LC;			//=> Lista de Clientes
    @FXML
    public HBox PC;			//=> Pacientes (Pets) 
    @FXML
    public HBox IT;			//=> Internações
    @FXML
    public HBox MenuBox;	//=> Container do Menu
    @FXML
    public VBox leftMenu; 	//=> Menu Lateral
    private boolean isDisplay = true; //=> Estado do menu lateral
    
	public static menuDataSaver menu;	//=> Classe que transporta os Dados
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	menu = new menuDataSaver(contentBox, PI, LC, PC, IT, MenuBox, getClass());	//=> Carrega os Dados na classe
    	menu.loadContent("home.fxml", menu.classe); 								//=> Carrega o Conteúdo da Página Inicial
    							  //=> Path, Width, Height, preserveRatio, Smooth
    	LOGO.setImage(new Image("file:src/main/resources/imgs/app-icon.png", 30, 30, true, true));
    	toggleMenu();
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
       
        isDisplay = !isDisplay; // Update the menu state
    }
    
    @FXML
    public void ClickHome() {
        menu.loadContent("home.fxml", menu.classe);
        
        leftMenu.getChildren().forEach(children -> menu.style(children, PI));
    }
    
    @FXML
    public void ClickLista() {
    	
    	menu.loadContent("listaClientes.fxml", menu.classe);
    	leftMenu.getChildren().forEach(children -> menu.style(children, LC));
    }
      
    @FXML
    public void ClickPets() {
    	
    	menu.loadContent("listaPacientes.fxml", menu.classe);
    	leftMenu.getChildren().forEach(children -> menu.style(children, PC));
    }
       
    @FXML
    public void ClickInternados() {
    	
    	menu.loadContent("listaInternados.fxml", menu.classe);
    	leftMenu.getChildren().forEach(children -> menu.style(children, IT));
    }
    
    private void toggleMenu() {    
    	
    	leftMenu.getChildren().forEach(children -> {
    		((Pane) children).getChildren().forEach(node -> isLabel(node, isDisplay));
    	});
        
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
    	
    	else if((node instanceof VBox || node instanceof HBox) && isDisplay) {
    		((Pane) node).getChildren().forEach(children -> isLabel(children, isDisplay));
    	}
    	
    	else if((node instanceof VBox || node instanceof HBox) && !isDisplay) {
    		((Pane) node).getChildren().forEach(children -> isLabel(children, isDisplay));
    	}
    	
    	else {
    		return;
    	}
    }
}