package sistem.vet.controllers;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import sistem.entities.Animal;
import sistem.entities.Servicos;
import sistem.entities.dto.animalDTO;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;
import sistem.interfaces.dao.DaoFactory;
import sistem.interfaces.dao.ServicosDAO;

public class menuDataSaver {
	
	//====> Controle de Conteúdo (Controlam a sidebar e o conteúdo)
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
    @FXML
    public VBox leftMenu;
    public Class classe;	//=> Registro da Classe Main
    
    public Scene scene;
    private Deque<String> pageHistory;
    
    //====> DAOs para acesso ao banco de dados (Centralizar as DAOs)
    public ClienteDAO clienteDAO;
    public AnimalDAO animalDAO;
    public ServicosDAO servicoDAO;
    
    //====> Armazena dados que serão usados em múltiplas páginas
    public Object[] serv = new Servicos[18]; //=> Array de Serviços		 (Para exibir e consultar)
    
    
    private Integer sharedId;				//=> Id do cliente 			 (Para buscas e operacões)
    private Integer sharedIdPet;		   //=> Id do animal			 (Para buscas e consultas)
    private Animal sharedAnimal;
    private List<animalDTO> sharedAnimais;
    
	//=> Construtor (Inicialização do programa)
	public menuDataSaver() {
		pageHistory = new ArrayDeque<>();
		
		// Cria as DAOs usando a DaoFactory
		this.clienteDAO = DaoFactory.createClienteDao();
		this.animalDAO = DaoFactory.createAnimalDao();
		this.servicoDAO = DaoFactory.createServicosDao();
		
		try {
			serv = servicoDAO.findAll().stream().toArray();
			
//			for(Object s : serv) {
//				((Servicos) s).Exibir();
//			}
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
    			
    
	//=> Construtor (Inicialização do programa)
	public menuDataSaver(Scene scene) {
		this.scene = scene;
		
		// Cria as DAOs usando a DaoFactory
		this.clienteDAO = DaoFactory.createClienteDao();
		this.animalDAO = DaoFactory.createAnimalDao();
		this.servicoDAO = DaoFactory.createServicosDao();
		
		try {
			serv = servicoDAO.findAll().stream().toArray();
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
		
	public void menuDataSaverFiller(VBox contentBox, HBox pI, HBox lC, HBox pC, HBox iT, HBox menuBox, VBox leftMenu, Class classe) {
		this.contentBox = contentBox;
		this.PI = pI;
		this.LC = lC;
		this.PC = pC;
		this.IT = iT;
		this.MenuBox = menuBox;
		this.leftMenu = leftMenu;
		this.classe = classe;
		
		pageHistory.push("home.fxml");
	}
	
	//=> Carrega o Conteúdo na divisão setada
    public void loadContent(String fxmlFile, Class classe) {
        try {
        	
        	if (scene != null) {
	        	String currentPage = (String) scene.getUserData();
	            if (currentPage != null) {
	                pageHistory.push(currentPage);
	                
	                // Convert to a List
	                List<String> list = pageHistory.stream().collect(Collectors.toList());

	                // Retain only elements with index <= 3
	                list = list.subList(0, Math.min(4, list.size()));

	                // Reconstruct the Deque
	                pageHistory = new ArrayDeque<>(list);
	            }
        	}
            
            // Clear existing content
            contentBox.getChildren().clear();
   
            // Load new content
            FXMLLoader loader = new FXMLLoader(classe.getResource("/fxml/" + fxmlFile));
            VBox newContent = loader.load();
            contentBox.getChildren().add(newContent);
            
            if (scene != null) {
            	scene.setUserData(fxmlFile);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void goBack(){
        if (!pageHistory.isEmpty()) {
            String previousPage = pageHistory.pop();
            loadContent(previousPage, classe);
        } else {
            dialogAvisos("Não há Página anterior para voltar.");
        }
    }
	
	//=> Estiliza os Botões do Menu
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
	
	public Boolean dialogConfirmar(String operacaoNome, String Objeto) {
		
		Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Confirmar Operação");
        dialog.setHeaderText("Tem certeza que deseja "+ operacaoNome +" esse "+ Objeto +"?");

        Button confirmButton = new Button("Confirmar");
        Button closeButton = new Button("Retornar");
        
        confirmButton.setOnAction(e -> dialog.setResult(true));
        closeButton.setOnAction(e -> dialog.setResult(false));

        HBox buttonBox = new HBox(10, confirmButton, closeButton); // Spacing between buttons
        buttonBox.setSpacing(50);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER); // Center buttons
        dialog.getDialogPane().setContent(buttonBox);
               
        dialog.showAndWait(); // This will block until the dialog is closed
        return dialog.getResult();
	}
	
	public void setTableWidth(TableView tableView, double widthRemover) {
		tableView.setMinWidth(getScreenWidth() - 85 - widthRemover);
	}

	public double getScreenWidth() {
		 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Get the screen width and height
        double screenWidth = screenBounds.getWidth();
        return screenWidth;
        //double screenHeight = screenBounds.getHeight();
	}
	
	public Boolean dialogAvisos(String Mensagem) {
		
		Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Aviso Importante");
        dialog.setHeaderText(Mensagem);

        Button closeButton = new Button("OK");
        
        closeButton.setOnAction(e -> dialog.setResult(false));

        HBox buttonBox = new HBox(10, closeButton); // Spacing between buttons
        buttonBox.setSpacing(50);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER); // Center buttons
        dialog.getDialogPane().setContent(buttonBox);
               
        dialog.showAndWait(); // This will block until the dialog is closed
        return dialog.getResult();
	}
	
	//=> Obtém o Id e limpa
	public int getSharedId() {	
		int id = sharedId;
		sharedId = null;
		
		return id;
	}
	
	//=> Define o Id
	public void setSharedId(Integer id) {
		this.sharedId = id;
	} 
	
	//=> Obtém o Id e limpa
	public int getSharedIdPet() {	
		int id = sharedIdPet;
		sharedIdPet = null;
		
		return id;
	}
	
	//=> Define o Id
	public void setSharedIdPet(Integer id) {
		this.sharedIdPet = id;
	} 
	
	
	public Animal getSharedAnimal() {
    	Animal id = sharedAnimal;
		sharedAnimal = null;
    	return id;
	}
	
	public void setSharedAnimal(Animal sharedAnimal) {
		this.sharedAnimal = sharedAnimal;
	}


	public List<animalDTO> getSharedAnimais() {
		return sharedAnimais;
	}


	public void setSharedAnimais(List<animalDTO> sharedAnimais) {
		this.sharedAnimais = sharedAnimais;
	}	
}