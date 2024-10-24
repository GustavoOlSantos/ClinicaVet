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
    public TableColumn<Cliente, Void> Del;
    
    //=> Elementos para Buscar Cliente
    @FXML
    public TextField buscaField;
    @FXML
    public Button buscaBtn;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
        List<Cliente> cliList = new ArrayList<>();

        try {
			cliList = clienteDAO.findAll();
		} catch (DomainException e) {
			e.printStackTrace();
		}
        
        renderTable(cliList);
        
        buscaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                buscarCliente(); // Call the handleSubmit method on Enter key press
            }
        });
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
                    
                    //=> Controller do Botão (Talvez seja removido)
                    visualizar.setOnAction(event -> {
                        Cliente selectedCliente = getTableRow().getItem();
                        if (selectedCliente != null) {
                        	verCliente(selectedCliente);
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
    	 
	@FXML
    public void clickHistorico() {
    	menu.loadContent("historicoClientes.fxml", menu.classe); 
    }
	
	@FXML
    public void cadCliente() {
    	//menu.loadContent("cadastrarCliente.fxml", menu.classe); 
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
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
	
	@FXML
    public void excluirCliente(Cliente target) {
		int id = target.getId();
		clienteDAO.deleteById(id);
		
		List<Cliente> cliList = new ArrayList<>();
		try {
			cliList = clienteDAO.findAll();
		} catch (DomainException e) {
			e.printStackTrace();
		}
		
		tableView.getItems().clear();
		renderTable(cliList);  
    }

}
