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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.entities.Servicos;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.enums.FormaPagamento;
import sistem.enums.SituacaoPet;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;

public class editarPetController implements Initializable {
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	int idCliente;
	int idAnimal;
	
	Animal animal;
	Cliente cliente;
	List<Object> AvailableServices = Arrays.stream(menu.serv).collect(Collectors.toList());
	
    @FXML
    public TextField nomeField;
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
    public TextField orcamentoField;
    @FXML
    public TextField obsField;
    @FXML
    public TextField medicamentosField;
    @FXML
    private ComboBox<String> servicosBox; 
    @FXML
    public TextField precoServField;
    
    @FXML
    public Button btnSalvar;
    
    //=> TABELA PETS DO CLIENTE
    @FXML
    public TableView<Servicos> tableView;
	@FXML
    public TableColumn<Servicos, String> Id;
    @FXML
    public TableColumn<Servicos, String> Nome;
    @FXML
    public TableColumn<Servicos, AnimalSexo> Preco;
    @FXML
    public TableColumn<Servicos, Void> Del;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			
			menu.setTableWidth(tableView, 500);
			
			idCliente = menu.getSharedId();
			idAnimal = menu.getSharedIdPet();
			
			cliente = clienteDAO.findById(idCliente);
			animal = animalDAO.findById(idAnimal);
			
			nomeField.setText(animal.getNome());
			
			sexoField.getItems().addAll(AnimalSexo.values());
			sexoField.setValue(animal.getSexo());
			
			especieField.getItems().addAll(AnimalTipo.values());
			especieField.setValue(animal.getTipo());
			
			emergenciaField.getItems().addAll(AnimalEmergencia.values());
			emergenciaField.setValue(animal.getEmergencia());

			statusField.getItems().addAll(SituacaoPet.values());
			statusField.setValue(animal.getSituacao());
			
			orcamentoField.setText(((Double) animal.getOrcamento()).toString());
			
			obsField.setStyle("-fx-alignment: TOP_LEFT");
			obsField.setText(animal.getObservacoes());
			
			medicamentosField.setStyle("-fx-alignment: TOP_LEFT");
			medicamentosField.setText(animal.getMedicamentos());
			
			List<Object> AvailableServices = Arrays.stream(menu.serv).collect(Collectors.toList());
			for(Object AvServs : AvailableServices) {
				servicosBox.getItems().add(((Servicos)AvServs).nome);
			}
			
			//=> Listeners das Caixas de Seleção
			sexoField.setOnAction(event -> {
				AnimalSexo selecionado = sexoField.getValue();
	            animal.setSexo(selecionado);
	        });
			
			especieField.setOnAction(event -> {
				AnimalTipo selecionado = especieField.getValue();
	            animal.setTipo(selecionado);
	        });
			
			emergenciaField.setOnAction(event -> {
				AnimalEmergencia selecionado = emergenciaField.getValue();
	            animal.setEmergencia(selecionado);
	        });

			statusField.setOnAction(event -> {
				SituacaoPet selecionado = statusField.getValue();
				animal.setSituacao(selecionado);
	        });
			
			servicosBox.setOnAction(event -> {
				Object selecionado = servicosBox.getValue();
		    	Object selectedServico = AvailableServices.stream()
		                .filter(s -> ((Servicos)s).getNome().equals(selecionado))
		                .findFirst()
		                .orElse(null);

				if (selectedServico != null) {
					double preco = ((Servicos)selectedServico).getPreco();
					precoServField.setText("R$" + ((Double) preco).toString());
				}
				
	        });

			//=> TABELA
			List<Servicos> servList = new ArrayList<>();
			servList = animal.getServicosList(menu.serv);
	        renderTable(servList);
			
		} catch (DomainException e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public void renderTable(List<Servicos> animais) {
	    
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
                        	excluirServico(selectedServico);
                        }
                    });
                }
            }
        });

        
        tableView.getItems().addAll(animais);
    }
		
    @FXML
    public void salvarEdicaoPet() throws NumberFormatException, DomainException{
    	
    	double oldOrcamento = animal.getOrcamento();
    	cliente.setOrcamentoInc(-(oldOrcamento));
    	
    	animal.setNome(nomeField.getText());
    	animal.setSexo(sexoField.getValue());
    	animal.setTipo(especieField.getValue());
    	animal.setEmergencia(emergenciaField.getValue());
    	animal.setSituacao(statusField.getValue());
    	animal.setOrcamento(Double.parseDouble(orcamentoField.getText()));
    	animal.setObservacoes(obsField.getText());
    	animal.setMedicamentos(medicamentosField.getText());
    	
    	cliente.setOrcamentoInc(animal.getOrcamento());
    	
    	try {
    		clienteDAO.update(cliente);
        	animalDAO.update(animal);
        	menu.dialogAvisos("Animal Atualizado com Sucesso!");
        	
        	menu.setSharedId(idCliente);
        	menu.loadContent("editarCliente.fxml", menu.classe); 
    	}
    	catch(DbException e) {
    		menu.dialogAvisos("Erro: " + e.getMessage());
    	}

    }
    
    public void reloadTable() {	
    	
    	List<Servicos> servList = new ArrayList<>();
		servList = animal.getServicosList(menu.serv);
		tableView.getItems().clear();
		renderTable(servList);  
    }
    
    public void voltarLista() {
    	menu.setSharedId(idCliente);
    	menu.loadContent("editarCliente.fxml", menu.classe); 
    }
   
    public void excluirServico(Servicos servico) {
    	
    	boolean confirmar = menu.dialogConfirmar("excluir", "serviço");	
		
		if(confirmar) {
			
	    	int idServico = servico.getId();
	    	animal.excluirServico(idServico, menu.serv);
	    	
	    	animal.setOrcamentoInc(-(servico.preco));
	    	cliente.setOrcamentoInc(-(servico.preco));
	    	
	    	animalDAO.update(animal);
	    	clienteDAO.update(cliente);
	    	
	    	orcamentoField.setText(((Double) animal.getOrcamento()).toString());
	    	reloadTable();
		}
    	
    }

    public void adicionarServicos() {
    	String selecionado = servicosBox.getValue();
    	Object selectedServico = AvailableServices.stream()
                .filter(s -> ((Servicos)s).getNome().equals(selecionado))
                .findFirst()
                .orElse(null);

		if (selectedServico != null) {
			int idServ = ((Servicos)selectedServico).getId();
			double preco =((Servicos)selectedServico).getPreco();
			
			animal.addServicos(idServ);
			
	    	animal.setOrcamentoInc(preco);
	    	cliente.setOrcamentoInc(preco);
	    	
	    	orcamentoField.setText(((Double) animal.getOrcamento()).toString());
	    	reloadTable();
	    	
	    	servicosBox.setValue(null);
	    	precoServField.setText(null);
			
		}
    	
    	
    }
}
