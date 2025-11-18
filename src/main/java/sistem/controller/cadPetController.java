package sistem.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import sistem.model.entities.Animal;
import sistem.model.entities.Servicos;
import sistem.model.enums.AnimalEmergencia;
import sistem.model.enums.AnimalSexo;
import sistem.model.enums.AnimalTipo;
import sistem.model.enums.FormaPagamento;
import sistem.model.enums.SituacaoPet;
import sistem.model.dao.interfaces.AnimalDAO;
import sistem.model.dao.interfaces.ClienteDAO;



public class cadPetController implements Initializable {
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	Animal animal;
	
	List<Object> AvailableServices = Arrays.stream(menu.serv).collect(Collectors.toList());

	//=> FORMULÁRIO DO PET
    @FXML
    public TextField nomeField;
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
    public TextField orcamentoPetField;
    @FXML
    public TextArea observacoesField;
    
    //=> TABELA DE SERVIÇOS
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
    
    //=> PREENCHER SERVIÇOS
    @FXML
    private ComboBox<String> servicosBox;
    @FXML
    public TextField precoServField;
    
    //=> CONTROLE DA JANELA
    private Stage modal;
    @FXML
    public Button close;
    @FXML
    public Button btnSalvarPet;
    
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
			
			sexoField.getItems().addAll(AnimalSexo.values());
			especieField.getItems().addAll(AnimalTipo.values());
			emergenciaField.getItems().addAll(AnimalEmergencia.values());
			statusField.getItems().addAll(SituacaoPet.values());
			statusField.getItems().removeIf(x -> x.name() == "NAO_LISTADO");
			
			List<Object> AvailableServices = Arrays.stream(menu.serv).collect(Collectors.toList());	
			for(Object AvServs : AvailableServices) {
				servicosBox.getItems().add(((Servicos)AvServs).nome);
			}
			
			servicosBox.setOnAction(event -> {
				Object selecionado = servicosBox.getValue();
		    	Object selectedServico = AvailableServices.stream()
		                .filter(s -> ((Servicos)s).getNome().equals(selecionado))
		                .findFirst()
		                .orElse(null);

				if (selectedServico != null) {
					double preco = ((Servicos)selectedServico).getPreco();
					precoServField.setText("R$" + ((Double) preco).toString());
				}
				
	        });
			
			animal = new Animal ();
			
			List<Servicos> servList = new ArrayList<>();	
			servList = animal.getServicosList(menu.serv);
			
	        renderTable(servList);
	        
	        close.setOnAction(e -> modal.close());
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
	    	
	    	
	 
	    	orcamentoPetField.setText(((Double) animal.getOrcamento()).toString());
	    	reloadTable();
		}
    	
    }
	
    public void salvarCadastro() {
    	   	
    	List<String> camposVazios = verificarCampos();
    	
    	
    	if (!camposVazios.isEmpty()) {
    	    String mensagem = "Os seguintes campos não foram preenchidos:\n" + String.join(", ", camposVazios);
    	    menu.dialogAvisos(mensagem);
    	    
    	    return;
    	}
    	
    	animal.setNome(nomeField.getText());
		animal.setSexo(sexoField.getValue());
		animal.setTipo(especieField.getValue());
		animal.setSituacao(statusField.getValue());
		animal.setEmergencia(emergenciaField.getValue());
		animal.setOrcamento(Double.parseDouble(orcamentoPetField.getText()));
		animal.setObservacoes(observacoesField.getText());
		  
		menu.setSharedAnimal(animal);  
		modal.close();
    }
    
    private List<String> verificarCampos(){
    	
    	List<String> camposVazios = new ArrayList<>();
    	
    	if (nomeField.getText().isBlank()) {
    	    camposVazios.add("Nome");
    	    nomeField.getStyleClass().add("field-error");
    	}
    	else {
		    nomeField.getStyleClass().remove("field-error");
		}
    	
    	if (sexoField.getValue() == null) {
    	    camposVazios.add("Sexo");
    	    sexoField.getStyleClass().add("field-error");
    	}
    	else {
    	    sexoField.getStyleClass().remove("field-error");
    	}
    	
    	if (especieField.getValue() == null) {
    	    camposVazios.add("Espécie");
    	    especieField.getStyleClass().add("field-error");
    	}
    	else {
    		especieField.getStyleClass().remove("field-error");
    	}
    	
    	if (statusField.getValue() == null) {
    	    camposVazios.add("Status");
    	    statusField.getStyleClass().add("field-error");
    	}
    	else {
    		statusField.getStyleClass().remove("field-error");
    	}
    	
    	if (emergenciaField.getValue() == null) {
    	    camposVazios.add("Emergência");
    	    emergenciaField.getStyleClass().add("field-error");
    	}
    	else {
    		emergenciaField.getStyleClass().remove("field-error");
    	}
    	
    	if (orcamentoPetField.getText().isBlank()) {
    	    camposVazios.add("Orçamento");
    	    orcamentoPetField.getStyleClass().add("field-error");
    	}
    	else {
    		orcamentoPetField.getStyleClass().remove("field-error");
    	}
    	
    	return camposVazios;
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
	    	
	    	
	    	orcamentoPetField.setText(((Double) animal.getOrcamento()).toString());
	    	reloadTable();
	    	
	    	servicosBox.setValue(null);
	    	precoServField.setText(null);
			
		}
    	
    	
    }
}

