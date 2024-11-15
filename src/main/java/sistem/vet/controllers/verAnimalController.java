package sistem.vet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalInternado;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;



public class verAnimalController implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
        ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	//AnimalDAO animalDAO = menu.animalDAO;
	
	int id = menu.getSharedId();
	
	@FXML
	Label nome;
	@FXML
	Label cpfCnpj;
	@FXML
	Label telefone;
	@FXML
	Label email;
	
	@FXML
	Label status;
	@FXML
	Label formaPg;
	@FXML
	Label parcelas;
	@FXML
	Label statusPg;
	@FXML
	Label orcamento;
	@FXML
	Label dataCadastro;
	@FXML
	Label dataFinalizado;
	
	//=====> Refência da Tabela de Pets e suas Colunas
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
    public TableColumn<Animal, AnimalInternado> Internado;
    @FXML
    public TableColumn<Animal, String> Orcamento;
    @FXML
    public TableColumn<Animal, String> Servicos;
	
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
			
			email.setText(cli.getEmail());
			
			dataCadastro.setText(cli.getDataCadastro().format(cli.timeFormat).toString());
			dataFinalizado.setText(null);
			
			status.setText(cli.getSituacao().toString());
			formaPg.setText(cli.getFormaPagamento().toString());
			
			String parcela = cli.getParcelas() == 1 ? ", à vista" : ", " + String.valueOf(cli.getParcelas()) + " x";
			parcelas.setText(parcela);
			
			statusPg.setText(cli.getStatusPagamento().toString());
			orcamento.setText("R$" + cli.getOrcamentoTotal());
			
			for(Animal pet : cli.animal) {
				Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
		    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
		        Internado.setCellValueFactory(new PropertyValueFactory<>("Internado"));
		        Orcamento.setCellValueFactory(new PropertyValueFactory<>("OrcamentoStr"));
		        
		        pet.transformServicos(menu.serv);
		        Servicos.setCellValueFactory(new PropertyValueFactory<>("StringServicos"));
		        
		        tableView.getItems().add(pet);
			}
			
		} catch (DomainException e) {
			e.printStackTrace();
		}  
        
        close.setOnAction(e -> modal.close());
    }  
    
    public void setStage(Stage stage) {
    	modal = stage;
    }
}
