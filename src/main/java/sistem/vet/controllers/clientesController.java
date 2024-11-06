package sistem.vet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.entities.Cliente;
import sistem.enums.Situacao;
import sistem.enums.StatusPagamento;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;
import sistem.services.CpfCnpjMask;
import sistem.vet.App;



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
    public TableColumn<Cliente, Double> Orcamento;
    @FXML
    public TableColumn<Cliente, StatusPagamento> StatusPagamento;
    @FXML
    public TableColumn<Cliente, Situacao> Status;
    
    @FXML
    public TableColumn<Cliente, Void> See;
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
			
			lblTitle.setText("Lista de clientes ativos");
						
			btnLC.setManaged(false); // Elimina o botão do Layout
			btnLC.setVisible(false); // Esconde o botão
			
			btnHC.setManaged(true); // Insere o botão no Layout
			btnHC.setVisible(true); // Exibe o botão
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
    	
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        Pets.setCellValueFactory(new PropertyValueFactory<>("animal"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("orcamentoTotal"));
        StatusPagamento.setCellValueFactory(new PropertyValueFactory<>("StatusPagamento"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
        
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
				
				btnLC.setManaged(false); // Elimina o botão do Layout
				btnLC.setVisible(false); // Esconde o botão
				
				btnHC.setManaged(true); // Insere o botão no Layout
				btnHC.setVisible(true); // Exibe o botão
			}
			else if(view == 1) {
				cliList = clienteDAO.findAll();
				view = 1;
				lblTitle.setText("Histórico de Clientes");
				
				btnLC.setManaged(true); // Insere o botão no Layout
				btnLC.setVisible(true); // Exibe o botão
				
				btnHC.setManaged(false); // Elimina o botão do Layout
				btnHC.setVisible(false); // Esconde o botão

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
		
		List<Cliente> cliList = new ArrayList<>();
		
		try {
			cliList = clienteDAO.findAll();
			view = 1;
			
			lblTitle.setText("Histórico de Clientes");
			
			btnLC.setManaged(true); // Insere o botão no Layout
			btnLC.setVisible(true); // Exibe o botão
			
			btnHC.setManaged(false); // Elimina o botão do Layout
			btnHC.setVisible(false); // Esconde o botão
			
		} catch (DomainException e) {
			
			App.modalAlert("Exception", "Falha ao gerar o Histórico - " + e.getMessage());
			e.printStackTrace();
		}
		
    	tableView.getItems().clear();
    	renderTable(cliList);  
    }
	
	@FXML
    public void cadCliente() {
    	menu.loadContent("cadastro.fxml", menu.classe); 
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
			e.printStackTrace();
		}
		
		tableView.getItems().clear();
		renderTable(cliList);  
    }
	
	public void cancelarCliente(Cliente target) {
		
		int situacao = target.getIntSituacao();
		String operacao = situacao == 2 ? "reestabelecer" : "cancelar";
		boolean confirmar = dialogConfirmar(0, operacao);
		
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
		boolean confirmar = dialogConfirmar(0, "excluir");	
		
		if(confirmar) {
			clienteDAO.deleteById(target.getId());
			reloadTable();
		}
    }
	
	
	public Boolean dialogConfirmar(int operacaoId, String operacaoNome) {
		
		Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Confirmar Operação");
        dialog.setHeaderText("Tem certeza que deseja "+ operacaoNome +" esse cliente?");

        ButtonType btnAllowType = new ButtonType("Confirmar");
        ButtonType btnFecharType = new ButtonType("Retornar", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(btnAllowType, btnFecharType);
        dialog.getDialogPane().getStyleClass().add("centerAlert");

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAllowType) {    
            	return true; // Return type must match Dialog<Void>
            } 
            return false;
        });
               
        dialog.showAndWait(); // This will block until the dialog is closed
        return dialog.getResult() != null && dialog.getResult();
	}
}
