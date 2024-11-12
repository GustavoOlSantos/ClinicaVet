package sistem.vet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sistem.entities.Cliente;
import sistem.enums.FormaPagamento;
import sistem.enums.Situacao;
import sistem.enums.StatusPagamento;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;

public class editarController implements Initializable {
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
    public TextField obsField;
    @FXML
    public Button btnSalvar;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			
			idCliente = menu.getSharedId();
			cliente = clienteDAO.findById(idCliente);
			
			nomeField.setText(cliente.getNome());
			cpfField.setText(cliente.getCpf());
			//emailField.setText(cliente.getEmail());
			telefoneField.setText(cliente.getTelefone());
			
			formaPgField.getItems().addAll(FormaPagamento.values());
			formaPgField.setValue(cliente.getFormaPagamento());
			
			parcelasField.setText(((Integer) cliente.getParcelas()).toString());
			
			statusPgField.getItems().addAll(StatusPagamento.values());
			statusPgField.setValue(cliente.getStatusPagamento());
			
			situacaoField.getItems().addAll(Situacao.values());
			situacaoField.setValue(cliente.getSituacao());
			
			dataCadField.setText(cliente.getDataCadastro().format(cliente.timeFormat).toString());
			
			String dataEnd = cliente.getDataFinalizado() == null ? "" : cliente.getDataFinalizado().format(cliente.timeFormat).toString();
			dataEndField.setText(dataEnd);
			
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
			
		} catch (DomainException e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
    @FXML
    public void salvarCadastro() {
        // salvando dados cliente
        String nomeCliente = nomeField.getText();
        String cpf = cpfField.getText();
        String email = emailField.getText();
        String telefone = telefoneField.getText();

        
        //cliente
        // salvar dados do cliente adicionado
        //clienteDAO.insert(null);
    }
}
