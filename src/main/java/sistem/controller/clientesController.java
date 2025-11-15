package sistem.controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.model.db.DbException;
import sistem.model.entities.Cliente;
import sistem.model.enums.Situacao;
import sistem.model.enums.StatusPagamento;
import sistem.model.exceptions.DomainException;
import sistem.model.dao.interfaces.AnimalDAO;
import sistem.model.dao.interfaces.ClienteDAO;
import sistem.service.CpfCnpjMask;
import sistem.app.App;



public class clientesController implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	//=====> Refência da Tabela e suas Colunas
	@FXML
    public TableView<Cliente> tableView;
	@FXML
    public TableColumn<Cliente, String> Id;
    @FXML
    public TableColumn<Cliente, String> Nome;
    @FXML
    public TableColumn<Cliente, String> Telefone;
    @FXML
    public TableColumn<Cliente, String> Pets;
    @FXML
    public TableColumn<Cliente, String> Orcamento;
    @FXML
    public TableColumn<Cliente, StatusPagamento> StatusPg;
    @FXML
    public TableColumn<Cliente, Situacao> Status;
    
    @FXML
    public TableColumn<Cliente, Void> See;
    @FXML
    public TableColumn<Cliente, Void> Edit;
    @FXML
    public TableColumn<Cliente, Void> Ban;
    @FXML
    public TableColumn<Cliente, Void> Del;
    
    //=> Elementos para Buscar Cliente
    @FXML
    public TextField buscaField;
    @FXML
    public Button buscaBtn;
    
    //=> Qual TableView recarregar após alguma operação
    private int view;
    @FXML
    public Label lblTitle;
    @FXML
    public Button btnLC;
    @FXML
    public Button btnHC;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	menu.setTableWidth(tableView, 0);
    	listarClientesAtivos();
    	
    	//=> Checa o input de buscar cliente
    	buscaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                buscarCliente(); 
            }
        });
    }
    
    public void listarClientesAtivos() {
    	
    	List<Cliente> cliList = new ArrayList<>();
		
		try {
			cliList = clienteDAO.findActive();
			view = 0;
		} 
		catch (DomainException e) {	
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
    	tableView.getItems().clear();
    	renderTable(cliList);  
    }
    
    @FXML
    public void renderTable(List<Cliente> clientes) {
    	
    	//=> Estilizar Tabela
    	Id.getStyleClass().add("centered");
        StatusPg.getStyleClass().add("centered");
        Status.getStyleClass().add("centered");
        
        //=> Parametrização das linhas da Tabela
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        Pets.setCellValueFactory(new PropertyValueFactory<>("animal"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("orcamentoTotalStr"));
        //StatusPg.setCellValueFactory(new PropertyValueFactory<>("StatusPagamento"));
        //Status.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
        
        // Listener do ComboBox de StatusPg
		StatusPg.setCellFactory(col -> new TableCell<Cliente, StatusPagamento>() {
		    private final ComboBox<StatusPagamento> comboBox = new ComboBox<>();
		    private ChangeListener<StatusPagamento> comboBoxListener;
		
		    @Override
		    protected void updateItem(StatusPagamento status, boolean empty) {
		        super.updateItem(status, empty);
		
		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
		            setGraphic(null);
		            setText(null);
		
		            // Remover listener anterior
		            if (comboBoxListener != null) {
		                comboBox.valueProperty().removeListener(comboBoxListener);
		            }
		        } else {
		            Cliente cliente = getTableRow().getItem(); 
		
		            comboBox.getItems().clear();
		            comboBox.getItems().addAll(StatusPagamento.values());
		            comboBox.setValue(cliente.getStatusPagamento());
		
		            comboBox.setStyle("-fx-background-color: transparent;");
		
		            // Remover listener anterior
		            if (comboBoxListener != null) {
		                comboBox.valueProperty().removeListener(comboBoxListener);
		            }
		
		            // Adicionar novo listener
		            comboBoxListener = (obs, oldValue, newValue) -> {
		                // Evitar alteração se o valor não mudou
		                if (newValue != oldValue && newValue != null) {
		                    cliente.setStatusPagamento(newValue); 
		                    newValue = null;
		                    try {
		                        clienteDAO.update(cliente);  // Atualize no banco de dados
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
		
		// Listener do ComboBox de Status
		Status.setCellFactory(col -> new TableCell<Cliente, Situacao>() {
		    private final ComboBox<Situacao> comboBox = new ComboBox<>();
		    private ChangeListener<Situacao> comboBoxListener;
		
		    @Override
		    protected void updateItem(Situacao status, boolean empty) {
		        super.updateItem(status, empty);
		
		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
		            setGraphic(null);
		            setText(null);
		
		            // Remover listener anterior
		            if (comboBoxListener != null) {
		                comboBox.valueProperty().removeListener(comboBoxListener);
		            }
		        } 
		        
		        else {
		            Cliente cliente = getTableRow().getItem(); 
		
		            comboBox.getItems().clear();
		            comboBox.getItems().addAll(Situacao.values());
		            comboBox.getItems().removeIf(x -> x.name() == "CANCELADO" || x.name() == "EXCLUÍDO");
		            comboBox.setValue(cliente.getSituacao());
		
		            comboBox.setStyle("-fx-background-color: transparent;");
		
		            // Remover listener anterior
		            if (comboBoxListener != null) {
		                comboBox.valueProperty().removeListener(comboBoxListener);
		            }
		
		            // Adicionar novo listener
		            comboBoxListener = (obs, oldValue, newValue) -> {
		                // Evitar alteração se o valor não mudou
		                if (newValue != null && cliente.getSituacao() != newValue) {
		                	
		                	if(newValue == Situacao.FINALIZADO) {
		                		cliente.Finalizar();	
		                	}
		                	
		                	else if(newValue != Situacao.FINALIZADO && cliente.getDataFinalizado() != null){
		                		cliente.setDataFinalizado(null);
		                	}

		                    oldValue = newValue;		                   
		                    
		                    newValue = null;
		                    try {
		                        clienteDAO.update(cliente);  // Atualize no banco de dados
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

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //=> Cria o botão para a coluna See
        See.setCellFactory(col -> new TableCell<Cliente, Void>() {
            private final Button visualizar = new Button("");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } 
                else {
                    FontIcon icon = new FontIcon("fas-eye"); //=> Criar Ícone
                    icon.setIconSize(15); 					//=> Tamanho do Ícone
                    visualizar.setGraphic(icon);		   //=> Seta o Ícone como gráfico do botão
                    setGraphic(visualizar);				  //=> Seta o Botão como gráfico da coluna
                    setAlignment(Pos.CENTER);

                    visualizar.setMaxWidth(Double.MAX_VALUE);
                    visualizar.setPrefWidth(65); // Adjust this value as needed
                    
                    // Use HBox to center and size the button
                    HBox hbox = new HBox(visualizar);
                    hbox.setAlignment(Pos.CENTER);
                    HBox.setHgrow(visualizar, Priority.ALWAYS);
                    
                    //=> Controller do Botão
                    visualizar.setOnAction(event -> {
                        Cliente selectedCliente = getTableRow().getItem();
                        if (selectedCliente != null) {
                        	verCliente(selectedCliente);
                        }
                    });
                }
            }
        });
        
        //=> Cria o botão para a coluna See
        Edit.setCellFactory(col -> new TableCell<Cliente, Void>() {
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
                        Cliente selectedCliente = getTableRow().getItem();
                        if (selectedCliente != null) {
                        	editarCliente(selectedCliente);
                        }
                    });
                }
            }
        });
          
        //=> Cria o botão para a coluna Ban
        Ban.setCellFactory(col -> new TableCell<Cliente, Void>() {
            private final Button block = new Button("");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } 
                
                else {
                	
                	Cliente cli = getTableRow().getItem();	//=> Cada cliente, conforme renderiza a tabela
                	//=> Checa se o cliente não está cancelado
                	FontIcon icon;
   	
                	if(cli != null) {
                		
                		Integer situacao = cli.getIntSituacao();
                		if(situacao != 2) {
                    		icon = new FontIcon("fas-ban"); //=> Criar Ícone
                    	}
                    	else{
                    		icon = new FontIcon("fa-arrow-circle-left"); //=> Criar Ícone
                    	}
                		
                		icon.setIconSize(15); 					 //=> Tamanho do Ícone
    	                block.setGraphic(icon);		   		//=> Seta o Ícone como gráfico do botão
    	                setGraphic(block);				  	    //=> Seta o Botão como gráfico da coluna
    	                setAlignment(Pos.CENTER);

    	                block.setMaxWidth(Double.MAX_VALUE);
                        block.setPrefWidth(65); // Adjust this value as needed
                        
                        // Use HBox to center and size the button
                        HBox hbox = new HBox(block);
                        hbox.setAlignment(Pos.CENTER);
                        HBox.setHgrow(block, Priority.ALWAYS);
                	}	
                	
                	else {
                		setGraphic(null);
                	}
	        		
	                
	                 
	                //=> Controller do Botão (Talvez seja removido)
	                block.setOnAction(event -> {
	                	Cliente selectedCliente = getTableRow().getItem();
	                    if (selectedCliente != null) {
	                    	cancelarCliente(selectedCliente);
	                    }
	                });
                }
            }
        });

        //=> Cria o botão para a coluna Del
        Del.setCellFactory(col -> new TableCell<Cliente, Void>() {
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
                        Cliente selectedCliente = getTableRow().getItem();
                        if (selectedCliente != null) {
                        	excluirCliente(selectedCliente);
                        }
                    });
                }
            }
        });

        tableView.getItems().addAll(clientes);
    }
    	 
    public void reloadTable() {	
    	
    	List<Cliente> cliList = new ArrayList<>();
		try {
			if(view == 0) {
				cliList = clienteDAO.findActive();
				view = 0;
				
				lblTitle.setText("Lista de clientes ativos");

                if(btnLC != null){
                    btnLC.setManaged(false); // Elimina o botão do Layout
                    btnLC.setVisible(false); // Esconde o botão
                }

				btnHC.setManaged(true); // Insere o botão no Layout
				btnHC.setVisible(true); // Exibe o botão
			}
			else if(view == 1) {
				cliList = clienteDAO.findAll();
				view = 1;
				lblTitle.setText("Histórico de Clientes");
				
				btnLC.setManaged(true); // Insere o botão no Layout
				btnLC.setVisible(true); // Exibe o botão

                if(btnHC != null){
                    btnHC.setManaged(false); // Elimina o botão do Layout
                    btnHC.setVisible(false); // Esconde o botão
                }
			}	
		} 
		catch (DomainException e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
		tableView.getItems().clear();
		renderTable(cliList);  
    }
    
	@FXML
    public void clickHistorico() {
		
		menu.loadContent("listaHistórico.fxml", menu.classe); 
    }
	
	@FXML
    public void cadCliente() {
    	menu.loadContent("cadastro.fxml", menu.classe); 
    }
	
	public void editarCliente(Cliente target) {
		int id = target.getId();
		menu.setSharedId(id);
		
		menu.loadContent("editarCliente.fxml", menu.classe); 
	}
	
	@FXML
    public void verCliente(Cliente target) {
		int id = target.getId();
		menu.setSharedId(id);
		
		Stage modalStage = new Stage();

        // Definindo a modalidade
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Visualizando Cliente");
      
       FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/verCliente.fxml"));
       Scene modalScene;
		try {
			modalScene = new Scene(fxmlLoader.load(), 1000, 800);
			modalScene.getStylesheets().add(App.class.getResource("/styles/StylesModal.css").toExternalForm());  
			modalStage.setScene(modalScene);
			
			verClientesController controller = fxmlLoader.getController();
			controller.setStage(modalStage);
			
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
        
		// Mostrando a janela modal
        modalStage.showAndWait();
    }
	
	@FXML
    public void buscarCliente() {
        String text = buscaField.getText();
    	text = CpfCnpjMask.Mask(text);
    	
    	List<Cliente> cliList = new ArrayList<>();
		
		try {
			cliList = clienteDAO.findByNameOrCpf(text);
		} catch (DomainException e) {
			menu.dialogAvisos("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
		tableView.getItems().clear();
		renderTable(cliList);  
    }
	
	public void cancelarCliente(Cliente target) {
		
		int situacao = target.getIntSituacao();
		String operacao = situacao == 2 ? "reestabelecer" : "cancelar";
		boolean confirmar = menu.dialogConfirmar(operacao, "cliente");
		
		if(confirmar) {
			if(situacao == 2) {
				target.setSituacao(0);
			}
			else if(situacao != 2) {
				target.setSituacao(2);
			}
			
			clienteDAO.update(target);	
			reloadTable();
		}

	}
	
	@FXML
    public void excluirCliente(Cliente target) {
		boolean confirmar = menu.dialogConfirmar("excluir", "cliente");	
		
		if(confirmar) {
			clienteDAO.deleteById(target.getId());
			reloadTable();
		}
    }
}
