package sistem.vet.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalInternado;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;



public class internadosController implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	//=====> Refência da Tabela de Pets e suas Colunas
	@FXML
    public TableView<Animal> tableView;
	@FXML
    public TableColumn<Animal, String> Id;
    @FXML
    public TableColumn<Animal, String> Nome;
    @FXML
    public TableColumn<Animal, AnimalSexo> Sexo;
    @FXML
    public TableColumn<Animal, AnimalTipo> Tipo;
    @FXML
    public TableColumn<Animal, AnimalEmergencia> Emergencia;
    // @FXML
    //public TableColumn<Animal, AnimalInternado> Internado;
    @FXML
    public TableColumn<Animal, String> Orcamento;
    @FXML
    public TableColumn<Animal, String> Servicos;
	
	@FXML
	Button close;
	
	private Stage modal;
        
    @FXML
    public TextField buscaField;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	 List<Animal> aniList = new ArrayList<>();

         try {
 			aniList = animalDAO.findInternados();
 		 } catch (DomainException e) {
 			e.printStackTrace();
 		 }
         
         renderTable(aniList);
         
         buscaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                buscarAnimal(); 
            }
        });
	
    }  
    
    public void renderTable(List<Animal> animais) {
    
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("OrcamentoStr"));
        
        for(Animal animal : animais) {
        	animal.transformServicos(menu.serv);
        }
    
        Servicos.setCellValueFactory(new PropertyValueFactory<>("StringServicos"));
        
        tableView.getItems().addAll(animais);
    }
    
    public void setStage(Stage stage) {
    	modal = stage;
    }
    
    public void buscarAnimal() {
        
        String nome = buscaField.getText();
        
        List<Animal> aniList = new ArrayList<>();
		
            try {
                aniList = animalDAO.findByName(nome);
            } catch (DomainException e) {
                e.printStackTrace();
            }

            tableView.getItems().clear();
            renderTable(aniList);  

        
    }
}
