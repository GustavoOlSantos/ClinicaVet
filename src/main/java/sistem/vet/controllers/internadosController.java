package sistem.vet.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistem.entities.Animal;
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
    public TableColumn<Animal, Double> Orcamento;
    @FXML
    public TableColumn<Animal, String> Servicos;
	
	@FXML
	Button close;
	
	private Stage modal;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	 List<Animal> cliList = new ArrayList<>();

         try {
 			cliList = animalDAO.findAll();
 		 } catch (DomainException e) {
 			e.printStackTrace();
 		 }
         
         renderTable(cliList);
	
    }  
    
    public void renderTable(List<Animal> animal) {
    
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
       // Internado.setCellValueFactory(new PropertyValueFactory<>("Internado"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("Orcamento"));
        
     //   ((Animal) animal).transformServicos(menu.serv);
     //   Servicos.setCellValueFactory(new PropertyValueFactory<>("StringServicos"));
        
        tableView.getItems().addAll(animal);
    }
    
    public void setStage(Stage stage) {
    	modal = stage;
    }
}
