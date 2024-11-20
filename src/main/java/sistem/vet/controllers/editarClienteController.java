package sistem.vet.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Cliente;
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

public class editarClienteController implements Initializable {
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	int idCliente;
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
    private ComboBox<FormaPagamento> formaPgField; 
    @FXML
    public TextField parcelasField;
    @FXML
    private ComboBox<StatusPagamento> statusPgField; 
    @FXML
    private ComboBox<Situacao> situacaoField; 
    @FXML
    public TextField dataCadField;
    @FXML
    public TextField dataEndField;
    @FXML
    public TextField qtdPetField;
    @FXML
    public TextField orcamentoField;
    @FXML
    public TextField obsField;
    @FXML
    public Button btnSalvar;
    
    //=> TABELA PETS DO CLIENTE
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
    public TableColumn<Animal, Void> Del;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
	    	menu.setTableWidth(tableView, 0);
	    	
			idCliente = menu.getSharedId();
			cliente = clienteDAO.findById(idCliente);
			
			nomeField.setText(cliente.getNome());
			cpfField.setText(CpfCnpjMask.Unmask(cliente.getCpf()));
			emailField.setText(cliente.getEmail());
			telefoneField.setText(TelefoneMask.Unmask(cliente.getTelefone()));
			
			formaPgField.getItems().addAll(FormaPagamento.values());
			formaPgField.setValue(cliente.getFormaPagamento());
			
			parcelasField.setText(String.valueOf(cliente.getParcelas()));
			
			statusPgField.getItems().addAll(StatusPagamento.values());
			statusPgField.setValue(cliente.getStatusPagamento());
			
			situacaoField.getItems().addAll(Situacao.values());
			situacaoField.getItems().removeIf(x -> x.name() == "CANCELADO" || x.name() == "EXCLUÍDO");
			situacaoField.setValue(cliente.getSituacao());
			
			dataCadField.setText(cliente.getDataCadastro().format(cliente.timeFormat).toString());
			
			String dataEnd = cliente.getDataFinalizado() == null ? "" : cliente.getDataFinalizado().format(cliente.timeFormat).toString();
			dataEndField.setText(dataEnd);
			
			orcamentoField.setText(((Double) cliente.getOrcamentoTotal()).toString());
			qtdPetField.setText(String.valueOf(cliente.qtdAnimal));
			
			obsField.setStyle("-fx-alignment: TOP_LEFT");
			obsField.setText(cliente.getObservacao());
			
			//=> Listeners das Caixas de Seleção
			formaPgField.setOnAction(event -> {
				FormaPagamento selecionado = formaPgField.getValue();
	            cliente.setFormaPagamento(selecionado);
	        });

			statusPgField.setOnAction(event -> {
				StatusPagamento selecionado = statusPgField.getValue();
				cliente.setStatusPagamento(selecionado);
	        });

			situacaoField.setOnAction(event -> {
	        	Situacao selecionado = situacaoField.getValue();
	        	cliente.setSituacao(selecionado);
	        });
			
			
			//=> TABELA
			List<Animal> aniList = new ArrayList<>();

	         try {
	        	 //=> Mudar para FindAll
	 			aniList = animalDAO.findByCliente(cliente.getId());
	 		 } catch (DomainException e) {
	 			e.printStackTrace();
	 		 }
	         
	         renderTable(aniList);
			
		} catch (DomainException e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void renderTable(List<Animal> animais) {
	    
		Id.getStyleClass().add("centered");
		
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("OrcamentoStr"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
        
        for(Animal animal : animais) {
        	animal.transformServicos(menu.serv);
        }
    
        Servicos.setCellValueFactory(new PropertyValueFactory<>("StringServicos"));
        
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
                        Animal selectedPet = getTableRow().getItem();
                        if (selectedPet != null) {
                        	editarPet(selectedPet);
                        }
                    });
                }
            }
        });
          
        //=> Cria o botão para a coluna Del
        Del.setCellFactory(col -> new TableCell<Animal, Void>() {
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
                        Animal selectedAnimal = getTableRow().getItem();
                        if (selectedAnimal != null) {
                        	excluirAnimal(selectedAnimal);
                        }
                    });
                }
            }
        });

        
        tableView.getItems().addAll(animais);
    }
		
    @FXML
    public void salvarEdicaoCliente() throws NumberFormatException, DomainException{
    	
    	cliente.setNome(nomeField.getText());
    	cliente.setCpf(cpfField.getText());
    	cliente.setEmail(emailField.getText());
    	cliente.setTelefone(telefoneField.getText());
    	cliente.setFormaPagamento(formaPgField.getValue());
    	cliente.setParcela(Integer.parseInt(parcelasField.getText()));
    	cliente.setStatusPagamento(statusPgField.getValue());
    	cliente.setSituacao(situacaoField.getValue());
    	cliente.setOrcamentoTotal(Double.parseDouble(orcamentoField.getText()));
    	cliente.setObservacao(obsField.getText());
    	
    	try {
        	clienteDAO.update(cliente);
        	menu.dialogAvisos("Cliente Atualizado com Sucesso!");
    	}
    	catch(DbException e) {
    		menu.dialogAvisos("Erro: " + e.getMessage());
    	}

    }
    
    public void reloadTable() {	
    	
		List<Animal> aniList = new ArrayList<>();
	
	    try {
	    	aniList = animalDAO.findByCliente(cliente.getId());
		 } catch (DomainException e) {
			e.printStackTrace();
		 }
			
			tableView.getItems().clear();
			renderTable(aniList);  
    }
    
    public void voltarLista() {
    	menu.loadContent("listaClientes.fxml", menu.classe); 
    }
    
    public void editarPet(Animal pet){
    	menu.setSharedId(idCliente);
    	menu.setSharedIdPet(pet.getId());
    	
    	menu.loadContent("editarPet.fxml", menu.classe); 
    	
    }
    
    public void excluirAnimal(Animal pet) {
    	
    	boolean confirmar = menu.dialogConfirmar("excluir", "animal");	
		
		if(confirmar) {
	    	int idPet = pet.getId();
	    	animalDAO.deleteById(idPet, idCliente);
	    	
	    	cliente.qtdAnimal -= 1;
	    	cliente.setOrcamentoInc(-(pet.getOrcamento()));
	    	clienteDAO.update(cliente);
	    	
	    	
	    	qtdPetField.setText(String.valueOf(cliente.qtdAnimal));
	    	orcamentoField.setText(((Double) cliente.getOrcamentoTotal()).toString());
	    	
	    	reloadTable();
		}
    	
    }
    
}
