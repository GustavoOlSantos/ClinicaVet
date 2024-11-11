package sistem.vet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;

public class editarController implements Initializable {
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;

    @FXML
    public TextField nomeField;
    @FXML
    public TextField cpfField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField telefoneField;
    @FXML
    public TextField nomeAnimalField;
    @FXML
    public ChoiceBox<String> sexoChoiceBox;
    @FXML
    public TextField tipoField;
    @FXML
    public ChoiceBox<String> emergenciaChoiceBox;
    @FXML
    public TextField servicosField;
    @FXML
    public Button btnSalvar;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
    @FXML
    public void salvarCadastro() {
        // salvando dados cliente
        String nomeCliente = nomeField.getText();
        String cpf = cpfField.getText();
        String email = emailField.getText();
        String telefone = telefoneField.getText();
        String nomeAnimal = nomeAnimalField.getText();
        String sexo = sexoChoiceBox.getValue();
        String tipo = tipoField.getText();
        String emergencia = emergenciaChoiceBox.getValue();
        String servicos = servicosField.getText();
        
        //cliente
        // salvar dados do cliente adicionado
        //clienteDAO.insert(null);
    }
}
