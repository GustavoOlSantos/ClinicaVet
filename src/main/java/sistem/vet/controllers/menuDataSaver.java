package sistem.vet.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;
import sistem.interfaces.dao.DaoFactory;

public class menuDataSaver {
	
	@FXML
    public VBox contentBox; //=> Divisão que deve receber os conteúdos atualizáveis
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
    public Class classe;	//=> Registro da Classe Main
    
    public ClienteDAO clienteDAO;
    public AnimalDAO animalDAO;
    
    //=> Construtor
	public menuDataSaver(VBox contentBox, HBox pI, HBox lC, HBox pC, HBox iT, HBox menuBox, Class classe) {
		this.contentBox = contentBox;
		this.PI = pI;
		this.LC = lC;
		this.PC = pC;
		this.IT = iT;
		this.MenuBox = menuBox;
		this.classe = classe;
				
		this.clienteDAO = DaoFactory.createClienteDao();
		this.animalDAO = DaoFactory.createAnimalDao();
	}
	
	public void style(Node el, HBox target) {
		if(el instanceof HBox) {
			
			if(el == target) {
				if(!target.getStyleClass().contains("menu-itemss")) {
		        	target.getStyleClass().remove("menu-items"); 
			        target.getStyleClass().add("menu-itemss");   
				}
			}
			
			else {
				if(el.getStyleClass().contains("menu-itemss")){		
		        	el.getStyleClass().remove("menu-itemss"); 
		        	el.getStyleClass().add("menu-items");   
				}
			}
		}
		
		
	}
	
	
	//=> Classe que carrega o conteúdo dentro da VBox
    public void loadContent(String fxmlFile, Class classe) {
        try {
            // Clear existing content
            contentBox.getChildren().clear();
   
            // Load new content
            FXMLLoader loader = new FXMLLoader(classe.getResource("/fxml/" + fxmlFile));
            VBox newContent = loader.load();
            contentBox.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    	
    
}
