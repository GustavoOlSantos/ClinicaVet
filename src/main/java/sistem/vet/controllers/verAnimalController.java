package sistem.vet.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.entities.Servicos;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;



public class verAnimalController implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
    ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	List<Object> AvailableServices = Arrays.stream(menu.serv).collect(Collectors.toList());
	int idPet = menu.getSharedIdPet();
	int idCliente;
	
	@FXML
	VBox pet_icon;
	@FXML
	VBox wholePageWidth;
	
	@FXML
	Label nome;
	@FXML
	Label sexo;
	@FXML
	Label tipo;
	@FXML
	Label status;
	
	@FXML
	Label dataCad;
	@FXML
	Label dataAlta;
	@FXML
	Label dataObito;
	
	@FXML
	Label nomeTutor;
	@FXML
	Label cpfCnpjTutor;
	@FXML
	Label telefoneTutor;
	@FXML
	Label emailTutor;
	
	@FXML
	Label emergencia;
	@FXML
	TextArea medicamentos;
	@FXML
	TextArea observacoes;
	@FXML
	Label diagnostico;
	
	
	//=> TABELA PETS DO CLIENTE
    @FXML
    public TableView<Servicos> tableView;
	@FXML
    public TableColumn<Servicos, String> Id;
    @FXML
    public TableColumn<Servicos, String> Nome;
    @FXML
    public TableColumn<Servicos, String> Preco;
    @FXML
    public TableColumn<Servicos, Void> Del;
    
    @FXML
	Button close;
	private Stage modal;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
        try {
        	
        	wholePageWidth.setMinWidth(1000);
        	
			Animal animal = animalDAO.findById(idPet);
			
			idCliente = animal.getIdCliente();		
			Cliente cli = clienteDAO.findById(idCliente);

			
			String iconLiteral = null;
			int icon_size = 150;
			
			switch (animal.getTipo()) {
	            case GATO: 				iconLiteral = "mdi2c-cat"; break;
	            case CACHORRO: 			iconLiteral = "mdi2d-dog"; break;
	            case AVE: 				iconLiteral = "fas-dove"; icon_size = 100; break;
	            case COELHO: 			iconLiteral = "mdi2r-rabbit"; break;
	            case TARTARUGA:		 	iconLiteral = "mdi2t-turtle"; break;
	            case COBRA: 			iconLiteral = "mdi2s-snake"; break;
	            case LAGARTO: 			iconLiteral = "fas-dragon"; icon_size = 100; break;
	            case OUTROS_SILVESTRES: iconLiteral = "mdi2p-paw"; break;
	        };

	        FontIcon icon = new FontIcon(iconLiteral);
	        icon.setIconSize(icon_size);
	        icon.getStyleClass().add("animal-icon");
	        pet_icon.getChildren().add(icon);
			
	        //=> DADOS DO PET
			nome.setText(animal.getNome());
			sexo.setText(animal.getSexo().toString());
			tipo.setText(animal.getTipo().toString());
			status.setText(animal.getSituacao().toString());
			
			//=> DADOS DO TUTOR
			nomeTutor.setText(cli.getNome());
			cpfCnpjTutor.setText(cli.getCpf());
			telefoneTutor.setText(cli.getTelefone());
			emailTutor.setText(cli.getEmail());
			
			//=> DADOS CLÍNICOS DO PET
			emergencia.setText(animal.getEmergencia().toString());
			medicamentos.setText(animal.getMedicamentos());
			observacoes.setText(animal.getObservacoes());
			diagnostico.setText(animal.getDiagnostico());
			
			medicamentos.setEditable(false);
			observacoes.setEditable(false);
			
			dataCad.setText(cli.getDataCadastro().format(cli.timeFormat).toString());
			
			String dataAltaF = animal.getDataAlta() == null ? null : animal.getDataAlta().format(cli.timeFormat).toString();
			String dataObitoF = animal.getDataObito() == null ? null : animal.getDataObito().format(cli.timeFormat).toString();
					
			dataAlta.setText(dataAltaF);
			dataObito.setText(dataObitoF);
			
			//=> TABELA
			List<Servicos> servList = new ArrayList<>();
			servList = animal.getServicosList(menu.serv);
	        renderTable(servList);
			
		} catch (DomainException e) {
			e.printStackTrace();
		}  
        
        close.setOnAction(e -> modal.close());
    }  
    
	public void renderTable(List<Servicos> servicos) {
		    
		Id.getStyleClass().add("centered");
		
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Preco.setCellValueFactory(new PropertyValueFactory<>("precoStr"));
        
        //=> Cria o botão para a coluna Del
        Del.setCellFactory(col -> new TableCell<Servicos, Void>() {
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
                        Servicos selectedServico = getTableRow().getItem();
                        if (selectedServico != null) {
                        	//excluirServico(selectedServico);
                        }
                    });
                }
            }
        });

        
        tableView.getItems().addAll(servicos);
    }
	    
    public void setStage(Stage stage) {
    	this.modal = stage;
    }
}
