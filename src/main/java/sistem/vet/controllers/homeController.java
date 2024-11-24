package sistem.vet.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.entities.dto.animalDTO;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalSexo;
import sistem.enums.SituacaoPet;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;



public class homeController implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	//=====> Refência da Tabela e suas Colunas
	@FXML
    public TableView<animalDTO> tableView;
    @FXML
    public TableColumn<animalDTO, String> NomePet;
    @FXML
    public TableColumn<animalDTO, AnimalSexo> Sexo;
    @FXML
    public TableColumn<animalDTO, AnimalEmergencia> Emergencia;
    @FXML
    public TableColumn<animalDTO, SituacaoPet> Status;
    @FXML
    public TableColumn<animalDTO, String> NomeTutor;
	
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//menu.setTableWidth(tableView, 0);
    	
    	try {
			List<Cliente> clientes = clienteDAO.findAll(5);
			List<animalDTO> animais = new ArrayList<animalDTO>();
			
			for(Cliente cli : clientes) {
				for(int i = 0; i < cli.qtdAnimal; i++) {
					
					if(cli.animal[i] != null) {
						animalDTO pet = new animalDTO(cli.animal[i].getNome(),
								cli.animal[i].getSexo(),
								cli.animal[i].getSituacao(), 
								cli.animal[i].getEmergencia(),
								cli.getNome());
						
						animais.add(pet);
					}					
				}
			}
			
			renderTable(animais);
			
		} catch (DomainException e) {
			e.printStackTrace();
		}
    		
    }
    

    
    @FXML
    public void renderTable(List<animalDTO> animais) {
    	
        //Status.getStyleClass().add("centered");
        
        NomePet.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
        NomeTutor.setCellValueFactory(new PropertyValueFactory<>("NomeTutor"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getItems().addAll(animais);
    }
    	 
	@FXML
    public void clickClientes() {
		
		menu.loadContent("listaClientes.fxml", menu.classe); 
		menu.leftMenu.getChildren().forEach(children -> menu.style(children, menu.LC));
    }
	
	@FXML
    public void cadCliente() {
    	menu.loadContent("cadastro.fxml", menu.classe); 
    	menu.leftMenu.getChildren().forEach(children -> menu.style(children, null));
    }
	
	public void clickPacientes() {
		menu.loadContent("listaPacientes.fxml", menu.classe); 
		menu.leftMenu.getChildren().forEach(children -> menu.style(children, menu.PC));
	}
	
	@FXML
    public void buscarCliente() {
        /*String text = buscaField.getText();
    	text = CpfCnpjMask.Mask(text);
    	
    	List<Cliente> cliList = new ArrayList<>();
		
		try {
			cliList = clienteDAO.findByNameOrCpf(text);
		} catch (DomainException e) {
			menu.dialogAvisos("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
		tableView.getItems().clear();
		renderTable(an);  */
    }
	
}
