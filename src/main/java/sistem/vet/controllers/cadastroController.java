package sistem.vet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class cadastroController implements Initializable {
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;


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
    public TextField orcamentoField;
    @FXML
    public ChoiceBox<String> emergenciaChoiceBox;
    @FXML
    public TextField servicosField;
    @FXML
    public Button btnSalvar;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
			formaPgField.getItems().addAll(FormaPagamento.values());
			
			
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
        Animal animal = menu.getSharedAnimal(); 
        if(animal != null) {
        	System.out.println(animal.getNome());
        }
    }
    
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
