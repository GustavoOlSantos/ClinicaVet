package sistem.vet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sistem.entities.Cliente;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;



public class verClientesController implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	int id = menu.getSharedId();
	
	@FXML
	Label nome;
	@FXML
	Label cpfCnpj;
	@FXML
	Label telefone;
	
	
	@FXML
	Button close;
	
	private Stage modal;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	Cliente cli;
    	
        try {
			cli = clienteDAO.findById(id);
			
			nome.setText(cli.getNome());
			cpfCnpj.setText(cli.getCpf());
			telefone.setText(cli.getTelefone());
		} catch (DomainException e) {
			e.printStackTrace();
		}  
        
        close.setOnAction(e -> modal.close());
    }  
    
    public void setStage(Stage stage) {
    	modal = stage;
    }
}
