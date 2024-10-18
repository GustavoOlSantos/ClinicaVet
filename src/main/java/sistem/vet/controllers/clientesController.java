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
import sistem.entities.Cliente;
import sistem.enums.Situacao;
import sistem.enums.StatusPagamento;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.ClienteDAO;
import sistem.interfaces.dao.DaoFactory;

public class clientesController implements Initializable {
	
	menuDataSaver menu = MainController.menu;
	
	@FXML
    public TableView<Cliente> tableView;
	@FXML
    public TableColumn<Cliente, String> columnId;
    @FXML
    public TableColumn<Cliente, String> columnNome;
    @FXML
    public TableColumn<Cliente, String> columnTelefone;
    @FXML
    public TableColumn<Cliente, StatusPagamento> columnStatusPagamento;
    @FXML
    public TableColumn<Cliente, Situacao> columnStatus;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        columnStatusPagamento.setCellValueFactory(new PropertyValueFactory<>("StatusPagamento"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
        
 
        
        List<Cliente> cliList = new ArrayList();
		ClienteDAO clienteDAO = DaoFactory.createClienteDao();

        try {
			cliList = clienteDAO.findAll();
		} catch (DomainException e) {
			e.printStackTrace();
		}
        
        tableView.getItems().addAll(cliList);
    }
    	
    	
    
	@FXML
    public void ClickHistorico() {
    	menu.loadContent("historicoClientes.fxml", menu.classe); 
    }

}
