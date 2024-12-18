package sistem.vet.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalInternado;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.enums.FormaPagamento;
import sistem.enums.SituacaoPet;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;
import sistem.vet.App;

public class cadastroController implements Initializable {
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	List<Animal> animaisCadastrados;
	Cliente cliente;

    @FXML
    public TextField nomeField;
    @FXML
    public TextField cpfField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField telefoneField;
    @FXML
    public TextField orcamentoField;
    @FXML
    public TextArea observacoesClienteField ;
    @FXML
    public TextField parcelasField;
    @FXML
    private ComboBox<FormaPagamento> formaPgField; 
    @FXML
    public Button btnSalvar;
    
    
    
    //=> TABELA DE ANIMAIS
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
    @FXML
    public TableColumn<Animal, String> Orcamento;
    @FXML
    public TableColumn<Animal, SituacaoPet> Status;
    @FXML
    public TableColumn<Animal, String> Servicos;
    @FXML
    public TableColumn<Animal, Void> Edit;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	
    	menu.setTableWidth(tableView, 550);
    	
    	parcelasField.setEditable(false);
    	
		formaPgField.getItems().addAll(FormaPagamento.values());
		
		formaPgField.valueProperty().addListener((observable, oldValue, newValue) -> {
			switch(newValue) {
				case CREDITO: 
					parcelasField.setEditable(true);
					parcelasField.setText("1");
					break;
				
				default: 
					parcelasField.setEditable(false);
					parcelasField.setText("1");
					break;
			}
		});
		
		//=> Inicializar Lista de Animais
		animaisCadastrados = new ArrayList<Animal>();
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
			
			cadPetController controller = fxmlLoader.getController();
			controller.setStage(modalStage);
			
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
        
		// Mostrando a janela modal
        modalStage.showAndWait();
        
        Animal petCadastrado = menu.getSharedAnimal();
        if(petCadastrado != null) {
        	animaisCadastrados.add(petCadastrado);
        	renderTable(animaisCadastrados);
        	double orcamento = Double.parseDouble(orcamentoField.getText());
        	orcamento += petCadastrado.getOrcamento();
        	orcamentoField.setText(""+orcamento);
        	
        }
    }
    
    
    public void renderTable(List<Animal> animais) {
        Id.getStyleClass().add("centered");
        Status.getStyleClass().add("centered");
    
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("OrcamentoStr"));
        
        // Listener do ComboBox de Status
 		Status.setCellFactory(col -> new TableCell<Animal, SituacaoPet>() {
 		    private final ComboBox<SituacaoPet> comboBox = new ComboBox<>();
 		    private ChangeListener<SituacaoPet> comboBoxListener;
 		
 		    @Override
 		    protected void updateItem(SituacaoPet status, boolean empty) {
 		        super.updateItem(status, empty);
 		
 		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
 		            setGraphic(null);
 		            setText(null);
 		
 		            // Remover listener anterior
 		            if (comboBoxListener != null) {
 		                comboBox.valueProperty().removeListener(comboBoxListener);
 		            }
 		        } else {
 		            Animal animal = getTableRow().getItem(); 
 		
 		            comboBox.getItems().clear();
 		            comboBox.getItems().addAll(SituacaoPet.values());
 		            comboBox.setValue(animal.getSituacao());
 		
 		            comboBox.setStyle("-fx-background-color: transparent;");
 		
 		            // Remover listener anterior
 		            if (comboBoxListener != null) {
 		                comboBox.valueProperty().removeListener(comboBoxListener);
 		            }
 		
 		            // Adicionar novo listener
 		            comboBoxListener = (obs, oldValue, newValue) -> {
 		                // Evitar alteração se o valor não mudou
 		                if (newValue != oldValue && newValue != null) {
 		                    animal.setSituacao(newValue); 
 		                    
 		                   if(newValue == SituacaoPet.ALTA_MEDICA) {
		                    	animal.setDataAlta(LocalDateTime.now());
		                    }
		                    else if(newValue == SituacaoPet.FALECIDO) {
		                    	animal.setDataObito(LocalDateTime.now());
		                    }
 		                   
 		                    newValue = null;
 		                    try {
 		                        animalDAO.update(animal);  // Atualize no banco de dados
 		                    } catch (DbException e) {
 		                        e.printStackTrace();
 		                    }
 		                }
 		            };
 		
 		            comboBox.valueProperty().addListener(comboBoxListener);
 		
 		            setGraphic(comboBox);
 		            setText(null);
 		        }
 		    }
 		});
     		
        
        for(Animal animal : animaisCadastrados) {
        	animal.transformServicos(menu.serv);
        }

        //=> Cria o botão para a coluna See
        Edit.setCellFactory(col -> new TableCell<Animal, Void>() {
            private final Button editar = new Button("");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } 
                else {
                    FontIcon icon = new FontIcon("fa-pencil"); //=> Criar Ícone
                    icon.setIconSize(15); 					//=> Tamanho do Ícone
                    editar.setGraphic(icon);		   //=> Seta o Ícone como gráfico do botão
                    setGraphic(editar);				  //=> Seta o Botão como gráfico da coluna
                    setAlignment(Pos.CENTER);

                    editar.setMaxWidth(Double.MAX_VALUE);
                    editar.setPrefWidth(65); // Adjust this value as needed
                    
                    // Use HBox to center and size the button
                    HBox hbox = new HBox(editar);
                    hbox.setAlignment(Pos.CENTER);
                    HBox.setHgrow(editar, Priority.ALWAYS);
                    
                    //=> Controller do Botão
                    editar.setOnAction(event -> {
                        Animal selectedAnimal = getTableRow().getItem();
                        if (selectedAnimal != null) {
                        	//editarAnimal(selectedAnimal);
                        }
                    });
                }
            }
        });
    
        Servicos.setCellValueFactory(new PropertyValueFactory<>("StringServicos"));
        
        tableView.getItems().addAll(animaisCadastrados);
    }
    
    public void salvarCadastro() {
        // salvando dados cliente
        String nomeCliente = nomeField.getText();
        String cpf = cpfField.getText();
        String email = emailField.getText();
        String telefone = telefoneField.getText();
        FormaPagamento fpg = formaPgField.getValue();
        Double orcamento = Double.parseDouble(orcamentoField.getText());
        int Parcelas = Integer.parseInt(parcelasField.getText());
        String observacao = observacoesClienteField.getText();
       
       
        //=> Transferência de Valores para o Objeto Cliente
        try {
			cliente = new Cliente(nomeCliente, cpf, telefone, animaisCadastrados.size());		
			cliente.animal = animaisCadastrados.toArray(new Animal[0]);
			cliente.setEmail(email);
			cliente.setFormaPagamento(fpg);
			cliente.setParcela(Parcelas);
			cliente.setObservacao(observacao);
			cliente.setOrcamentoTotal(orcamento);
			//=> Método que exibe todos os dados do cliente e de seus animais (Para debug)
			//cliente.printClienteSheet(menu.serv);
			
			clienteDAO.insert(cliente);
			menu.dialogAvisos("Cliente Cadastrado com sucesso");
			menu.goBack();
		} 
        catch (DomainException e) {
			menu.dialogAvisos(e.getMessage());
		}
        catch (DbException e) {
			menu.dialogAvisos(e.getMessage());
		}     
        
    }
    
}
