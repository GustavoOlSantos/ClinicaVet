package sistem.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.model.db.DbException;
import sistem.model.entities.Animal;
import sistem.model.enums.AnimalEmergencia;
import sistem.model.enums.AnimalSexo;
import sistem.model.enums.AnimalTipo;
import sistem.model.enums.SituacaoPet;
import sistem.model.exceptions.DomainException;
import sistem.model.dao.interfaces.AnimalDAO;
import sistem.model.dao.interfaces.ClienteDAO;
import sistem.app.App;



public class internadosController implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
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
    // @FXML
    //public TableColumn<Animal, AnimalInternado> Internado;
    @FXML
    public TableColumn<Animal, String> Orcamento;
    @FXML
    public TableColumn<Animal, SituacaoPet> Status;
    @FXML
    public TableColumn<Animal, String> Servicos;
    @FXML
    public TableColumn<Animal, Void> See;
    @FXML
    public TableColumn<Animal, Void> Edit;
	
	@FXML
	Button close;
	
	private Stage modal;
        
    @FXML
    public TextField buscaField;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	menu.setTableWidth(tableView, 0);
    	 List<Animal> aniList = new ArrayList<>();

         try {
 			aniList = animalDAO.findInternados();

 		 } catch (DomainException e) {
 			e.printStackTrace();
 		 }
         
         renderTable(aniList);
         
         buscaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                buscarAnimal(); 
            }
        });
	
    }  
    
    public void renderTable(List<Animal> animais) {
    	
    	Id.getStyleClass().add("centered");
        Status.getStyleClass().add("centered");
    
    	Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("OrcamentoStr"));
        
        // Listener do ComboBox de Status
 		Status.setCellFactory(col -> new TableCell<Animal, SituacaoPet>() {
 		    private final ComboBox<SituacaoPet> comboBox = new ComboBox<>();
 		    private ChangeListener<SituacaoPet> comboBoxListener;
 		
 		    @Override
 		    protected void updateItem(SituacaoPet status, boolean empty) {
 		        super.updateItem(status, empty);
 		
 		        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
 		            setGraphic(null);
 		            setText(null);
 		
 		            // Remover listener anterior
 		            if (comboBoxListener != null) {
 		                comboBox.valueProperty().removeListener(comboBoxListener);
 		            }
 		        } else {
 		            Animal animal = getTableRow().getItem(); 
 		
 		            comboBox.getItems().clear();
 		            comboBox.getItems().addAll(SituacaoPet.values());
 		            comboBox.setValue(animal.getSituacao());
 		
 		            comboBox.setStyle("-fx-background-color: transparent;");
 		
 		            // Remover listener anterior
 		            if (comboBoxListener != null) {
 		                comboBox.valueProperty().removeListener(comboBoxListener);
 		            }
 		
 		            // Adicionar novo listener
 		            comboBoxListener = (obs, oldValue, newValue) -> {
 		                // Evitar alteração se o valor não mudou
 		                if (newValue != oldValue && newValue != null) {
 		                    animal.setSituacao(newValue); 
 		                    
 		                   if(newValue == SituacaoPet.ALTA_MEDICA) {
		                    	animal.setDataAlta(LocalDateTime.now());
		                    }
		                    else if(newValue == SituacaoPet.FALECIDO) {
		                    	animal.setDataObito(LocalDateTime.now());
		                    }
 		                   
 		                    newValue = null;
 		                    try {
 		                        animalDAO.update(animal);  // Atualize no banco de dados
 		                    } catch (DbException e) {
 		                        e.printStackTrace();
 		                    }
 		                }
 		            };
 		
 		            comboBox.valueProperty().addListener(comboBoxListener);
 		
 		            setGraphic(comboBox);
 		            setText(null);
 		        }
 		    }
 		});
     		
        
        for(Animal animal : animais) {
        	animal.transformServicos(menu.serv);
        }
        
        //=> Cria o botão para a coluna See
        See.setCellFactory(col -> new TableCell<Animal, Void>() {
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
                    setAlignment(Pos.CENTER);

                    visualizar.setMaxWidth(Double.MAX_VALUE);
                    visualizar.setPrefWidth(65); // Adjust this value as needed
                    
                    // Use HBox to center and size the button
                    HBox hbox = new HBox(visualizar);
                    hbox.setAlignment(Pos.CENTER);
                    HBox.setHgrow(visualizar, Priority.ALWAYS);
                    
                    //=> Controller do Botão
                    visualizar.setOnAction(event -> {
                        Animal selectedAnimal = getTableRow().getItem();
                        if (selectedAnimal != null) {
                        	verAnimal(selectedAnimal);
                        }
                    });
                }
            }
        });
        
        //=> Cria o botão para a coluna See
        Edit.setCellFactory(col -> new TableCell<Animal, Void>() {
            private final Button editar = new Button("");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } 
                else {
                    FontIcon icon = new FontIcon("fa-pencil"); //=> Criar Ícone
                    icon.setIconSize(15); 					//=> Tamanho do Ícone
                    editar.setGraphic(icon);		   //=> Seta o Ícone como gráfico do botão
                    setGraphic(editar);				  //=> Seta o Botão como gráfico da coluna
                    setAlignment(Pos.CENTER);

                    editar.setMaxWidth(Double.MAX_VALUE);
                    editar.setPrefWidth(65); // Adjust this value as needed
                    
                    // Use HBox to center and size the button
                    HBox hbox = new HBox(editar);
                    hbox.setAlignment(Pos.CENTER);
                    HBox.setHgrow(editar, Priority.ALWAYS);
                    
                    //=> Controller do Botão
                    editar.setOnAction(event -> {
                        Animal selectedAnimal = getTableRow().getItem();
                        if (selectedAnimal != null) {
                        	editarAnimal(selectedAnimal);
                        }
                    });
                }
            }
        });
    
        Servicos.setCellValueFactory(new PropertyValueFactory<>("StringServicos"));
        
        tableView.getItems().addAll(animais);
    }
    
    public void setStage(Stage stage) {
    	modal = stage;
    }
    
    public void buscarAnimal() {
        
        String nome = buscaField.getText();
        
        List<Animal> aniList = new ArrayList<>();
		
            try {
                aniList = animalDAO.findByNameInternado(nome);
            } catch (DomainException e) {
            	menu.dialogAvisos("Erro: " + e.getMessage());

            }

            tableView.getItems().clear();
            renderTable(aniList);  

        
    }
    
    public void verAnimal (Animal target){
    	
	    int id = target.getId();
		menu.setSharedIdPet(id);
		
		Stage modalStage = new Stage();
	
	    // Definindo a modalidade
	    modalStage.initModality(Modality.APPLICATION_MODAL);
	    modalStage.setTitle("Visualizando Animal");
	      
       FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/verAnimal.fxml"));
       Scene modalScene;
		try {
			modalScene = new Scene(fxmlLoader.load(), 1000, 800);
			modalScene.getStylesheets().add(App.class.getResource("/styles/StylesModal.css").toExternalForm());  
			modalStage.setScene(modalScene);
			
			verAnimalController controller = fxmlLoader.getController();
			controller.setStage(modalStage);
			
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
        
		// Mostrando a janela modal 
        modalStage.showAndWait();
        
    }
    
    public void editarAnimal (Animal target){
    	int idPet = target.getId();
		int idCliente = target.getIdCliente();
		
		menu.setSharedIdPet(idPet);
		menu.setSharedId(idCliente);
		
		menu.loadContent("editarPet.fxml", menu.classe); 
    }
}
