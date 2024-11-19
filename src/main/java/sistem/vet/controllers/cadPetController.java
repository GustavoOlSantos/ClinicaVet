package sistem.vet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Servicos;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.enums.FormaPagamento;
import sistem.enums.Situacao;
import sistem.enums.SituacaoPet;
import sistem.enums.StatusPagamento;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;
import sistem.services.CpfCnpjMask;
import sistem.services.TelefoneMask;
import sistem.vet.App;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class cadPetController implements Initializable {
	//=> Coleta dados do MenuSaver
		menuDataSaver menu = MainController.menu;
		ClienteDAO clienteDAO = menu.clienteDAO;
		AnimalDAO animalDAO = menu.animalDAO;
		Animal animal;
		List<Object> AvailableServices = Arrays.stream(menu.serv).collect(Collectors.toList());
		
	private Stage modal;
    @FXML
    private ComboBox<AnimalSexo> sexoField; 
    @FXML
    private ComboBox<AnimalTipo> especieField; 
    @FXML
    private ComboBox<AnimalEmergencia> emergenciaField; 
    @FXML
    private ComboBox<FormaPagamento> formaPgField; 
    @FXML
    private ComboBox<SituacaoPet> statusField; 	
    @FXML
    private ComboBox<String> servicosBox;
    @FXML
    public TextField nomeField;
    @FXML
    public TextField precoServField;
    @FXML
    public TextField cpfField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField telefoneField;
    
    @FXML
    public TextField observacoesField;
    @FXML
    public ChoiceBox<String> sexoChoiceBox;
    @FXML
    public TextField tipoField;
    @FXML
    public ChoiceBox<String> emergenciaChoiceBox;
    @FXML
    public TextField orcamentoField;
    @FXML
    public TextField servicosField;
    @FXML
    public Button btnSalvar;
    
    @FXML
    public TableView<Servicos> tableView;
	@FXML
    public TableColumn<Servicos, String> Id;
    @FXML
    public TableColumn<Servicos, String> Nome;
    @FXML
    public TableColumn<Servicos, AnimalSexo> Preco;
    @FXML
    public TableColumn<Servicos, Void> Del;
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
			
			sexoField.getItems().addAll(AnimalSexo.values());
			especieField.getItems().addAll(AnimalTipo.values());
			emergenciaField.getItems().addAll(AnimalEmergencia.values());
			statusField.getItems().addAll(SituacaoPet.values());
			List<Object> AvailableServices = Arrays.stream(menu.serv).collect(Collectors.toList());
			for(Object AvServs : AvailableServices) {
				servicosBox.getItems().add(((Servicos)AvServs).nome);
			}
			
			animal = new Animal ();
			List<Servicos> servList = new ArrayList<>();
			servList = animal.getServicosList(menu.serv);
	        renderTable(servList);
	}
public void renderTable(List<Servicos> animais) {
	    
		Id.getStyleClass().add("centered");
		
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Preco.setCellValueFactory(new PropertyValueFactory<>("precoStr"));
        
        //=> Cria o botão para a coluna Del
        Del.setCellFactory(col -> new TableCell<Servicos, Void>() {
            private final Button excluir = new Button("");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } 
                else {
                    FontIcon icon = new FontIcon("fas-trash"); //=> Criar Ícone
                    icon.setIconSize(15); 					//=> Tamanho do Ícone
                    excluir.setGraphic(icon);		   //=> Seta o Ícone como gráfico do botão
                    setGraphic(excluir);				  //=> Seta o Botão como gráfico da coluna
                    setAlignment(Pos.CENTER);
                    
                    
                    excluir.setMaxWidth(Double.MAX_VALUE);
                    excluir.setPrefWidth(65); // Adjust this value as needed
                    
                    // Use HBox to center and size the button
                    HBox hbox = new HBox(excluir);
                    hbox.setAlignment(Pos.CENTER);
                    HBox.setHgrow(excluir, Priority.ALWAYS);
                    
                    //=> Controller do Botão (Talvez seja removido)
                    excluir.setOnAction(event -> {
                        Servicos selectedServico = getTableRow().getItem();
                        if (selectedServico != null) {
                        	excluirServico(selectedServico);
                        }
                    });
                }
            }
        });

        
        tableView.getItems().addAll(animais);
    }
		
    @FXML
   
    
    public void reloadTable() {	
    	
    	List<Servicos> servList = new ArrayList<>();
		servList = animal.getServicosList(menu.serv);
		tableView.getItems().clear();
		renderTable(servList);  
    }
    
  
   
    public void excluirServico(Servicos servico) {
    	
    	boolean confirmar = menu.dialogConfirmar("excluir", "serviço");	
		
		if(confirmar) {
			
	    	int idServico = servico.getId();
	    	animal.excluirServico(idServico, menu.serv);
	    	
	    	animal.setOrcamentoInc(-(servico.preco));
	    	
	    	
	 
	    	orcamentoField.setText(((Double) animal.getOrcamento()).toString());
	    	reloadTable();
		}
    	
    }
	
 public void cadPet (){
    	
       
		
		Stage modalStage = new Stage();

        // Definindo a modalidade
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Cadastrar animal");
      
       FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/cadastrarPet.fxml"));
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
    
    public void salvarCadastro() {
        // salvando dados cliente
      animal.setNome(nomeField.getText());
      animal.setSexo(sexoField.getValue());
      animal.setTipo(especieField.getValue());
      animal.setSituacao(statusField.getValue());
      animal.setEmergencia(emergenciaField.getValue());
      animal.setOrcamento(Double.parseDouble(orcamentoField.getText()));
      animal.setObservacoes(observacoesField.getText());
      
      menu.setSharedAnimal(animal);  
        
		
        
        //cliente
        // salvar dados do cliente adicionado
        //clienteDAO.insert(null);
    }
    public void setStage(Stage stage) {
    	this.modal = stage;
    }
    public void adicionarServicos() {
    	String selecionado = servicosBox.getValue();
    	Object selectedServico = AvailableServices.stream()
                .filter(s -> ((Servicos)s).getNome().equals(selecionado))
                .findFirst()
                .orElse(null);

		if (selectedServico != null) {
			int idServ = ((Servicos)selectedServico).getId();
			double preco =((Servicos)selectedServico).getPreco();
			
			animal.addServicos(idServ);
			
	    	animal.setOrcamentoInc(preco);
	    	
	    	
	    	orcamentoField.setText(((Double) animal.getOrcamento()).toString());
	    	reloadTable();
	    	
	    	servicosBox.setValue(null);
	    	precoServField.setText(null);
			
		}
    	
    	
    }
}

