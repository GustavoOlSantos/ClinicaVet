package sistem.vet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.enums.AnimalEmergencia;
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

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	
		formaPgField.getItems().addAll(FormaPagamento.values());
		
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
        }
    }
    
    public void salvarCadastro() {
        // salvando dados cliente
        String nomeCliente = nomeField.getText();
        String cpf = cpfField.getText();
        String email = emailField.getText();
        String telefone = telefoneField.getText();
        FormaPagamento fpg = formaPgField.getValue();
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
			
			//=> Método que exibe todos os dados do cliente e de seus animais (Para debug)
			//cliente.printClienteSheet(menu.serv);
			
			clienteDAO.insert(cliente);
			menu.dialogAvisos("Cliente Cadastrado com sucesso");
		} 
        catch (DomainException e) {
			menu.dialogAvisos(e.getMessage());
		}
        catch (DbException e) {
			menu.dialogAvisos(e.getMessage());
		}     
        
    }
    
}
