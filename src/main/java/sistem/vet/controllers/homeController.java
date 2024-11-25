package sistem.vet.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.entities.Cliente;
import sistem.entities.dto.animalDTO;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalSexo;
import sistem.enums.SituacaoPet;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;
import sistem.services.CpfCnpjMask;
import sistem.services.TelefoneMask;
import sistem.vet.App;



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
    
    @FXML
    Label todayDate;
	@FXML
    Label caixaValue;
    @FXML
    Label AnimaisCads;
    @FXML
    Label ClientesCads;
    
    @FXML
    TextField nomeField;
    @FXML
    TextField nomePetField;
    @FXML
    TextField cpfField;
    @FXML
    TextField telefoneField;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//menu.setTableWidth(tableView, 0);
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	todayDate.setText(LocalDateTime.now().format(timeFormat).toString());
    	
    	preecherDados();
    	
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
	
	public void preecherDados() {
		try {
			List<Cliente> clientes = clienteDAO.findToday();
			
			int qtdClientes = clientes.size();
			int qtdPets = 0;
			double caixa = 0;
			
			for(Cliente cli : clientes) {
				caixa += cli.getOrcamentoTotal();
				qtdPets += cli.qtdAnimal;
			}
			
			caixaValue.setText(""+ caixa);
			AnimaisCads.setText(""+ qtdPets);
			ClientesCads.setText(""+ qtdClientes);
			
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    public void buscarCliente() {
		
		try {
	        String nomeTutor = nomeField.getText();
	        String cpf = CpfCnpjMask.Mask(cpfField.getText());
	        String tel = telefoneField.getText().isBlank() ? "" : TelefoneMask.Mask(telefoneField.getText());
	        String pet = nomePetField.getText();
	        
	        List<Cliente> clis;
	        int code = nomeTutor.isBlank() && cpf.isBlank() && tel.isBlank() ? 0 :
        		!nomeTutor.isBlank() && cpf.isBlank() && tel.isBlank()   ? 1 :
    			!nomeTutor.isBlank() && !cpf.isBlank() && tel.isBlank()  ? 2 : 3 ;
	        
	        if(pet.isBlank()) {
	        	
	        	if(code == 0) {
	        		return;
	        	}
	        	
	        	clis = clienteDAO.findInHome(nomeTutor, cpf, tel, code);
	        	List<animalDTO> animais = new ArrayList<animalDTO>();
				
				for(Cliente cli : clis) {
					for(int i = 0; i < cli.qtdAnimal; i++) {
						
						if(cli.animal[i] != null) {
							
							cli.animal[i].transformServicos(menu.serv);
							
							animalDTO petpet = new animalDTO(cli.animal[i].getId(),
									cli.animal[i].getIdCliente(),
									cli.animal[i].getNome(),
									cli.animal[i].getSexo(),
									cli.animal[i].getSituacao(), 
									cli.animal[i].getEmergencia(),
									cli.animal[i].getStringServicos(),
									cli.animal[i].getOrcamentoStr(),
									cli.getNome());
							
							animais.add(petpet);
						}					
					}
				}
				
				menu.setSharedAnimais(animais);
				menu.loadContent("resultadoBuscaClientes.fxml", getClass());
	        }
	        else {
	        	
	        	clis = clienteDAO.findInHome(nomeTutor, cpf, tel, pet, code);
	        	
        		List<animalDTO> animais = new ArrayList<animalDTO>();
				
				for(Cliente cli : clis) {
					for(int i = 0; i < cli.qtdAnimal; i++) {
						
						if(cli.animal[i] != null) {
							
							cli.animal[i].transformServicos(menu.serv);
							
							animalDTO petpet = new animalDTO(cli.animal[i].getId(),
									cli.animal[i].getIdCliente(),
									cli.animal[i].getNome(),
									cli.animal[i].getSexo(),
									cli.animal[i].getSituacao(), 
									cli.animal[i].getEmergencia(),
									cli.animal[i].getStringServicos(),
									cli.animal[i].getOrcamentoStr(),
									cli.getNome());
							
							animais.add(petpet);
						}					
					}
				}
				
				if(animais.size() > 1) {
					menu.setSharedAnimais(animais);
					menu.loadContent("resultadoBuscaClientes.fxml", getClass());
				}
				else {
		    		int id = clis.get(0).animal[0].getId();
		    		menu.setSharedIdPet(id);
		    		
		    		Stage modalStage = new Stage();

		    		// Definindo a modalidade
		    		modalStage.initModality(Modality.APPLICATION_MODAL);
		    		modalStage.setTitle("Visualizando Animal");
		            
		            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/verAnimal.fxml"));
		            Scene modalScene;
		            
		      		try {
		      			modalScene = new Scene(fxmlLoader.load(), 1000, 800);
		      			modalScene.getStylesheets().add(App.class.getResource("/styles/StylesModal.css").toExternalForm());  
		      			modalStage.setScene(modalScene);
		      			
		      			verAnimalController controller = fxmlLoader.getController();
		      			controller.setStage(modalStage);
		      			
		      		} 
		      		catch (IOException e1) {
		      			e1.printStackTrace();
		      		}
		              
		      		// Mostrando a janela modal
		              modalStage.showAndWait();
				}
	        }
		}
		catch(DomainException e) {
			menu.dialogAvisos(e.getMessage());
		}
    }
	
}
