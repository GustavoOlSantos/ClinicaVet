package sistem.vet.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.entities.Animal;
import sistem.entities.dto.animalDTO;
import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalSexo;
import sistem.enums.SituacaoPet;
import sistem.interfaces.dao.AnimalDAO;
import sistem.interfaces.dao.ClienteDAO;
import sistem.vet.App;



public class resultadoBusca implements Initializable {
	
	//=> Coleta dados do MenuSaver
	menuDataSaver menu = MainController.menu;
	ClienteDAO clienteDAO = menu.clienteDAO;
	AnimalDAO animalDAO = menu.animalDAO;
	
	//=====> Refência da Tabela e suas Colunas
	@FXML
    public TableView<animalDTO> tableView;
    @FXML
    public TableColumn<animalDTO, String> NomePet;
    @FXML
    public TableColumn<animalDTO, AnimalSexo> Sexo;
    @FXML
    public TableColumn<animalDTO, AnimalEmergencia> Emergencia;
    @FXML
    public TableColumn<animalDTO, SituacaoPet> Status;
    @FXML
    public TableColumn<animalDTO, String> Orcamento;
    @FXML
    public TableColumn<animalDTO, String> Servicos;
    @FXML
    public TableColumn<animalDTO, String> NomeTutor;
    @FXML
    public TableColumn<animalDTO, Void> See;
    @FXML
    public TableColumn<animalDTO, Void> Edit;
    
    @FXML
    public HBox hboxMargin;
    
    @Override	//=> Atualiza a Tabela ao inicializar
    public void initialize(URL location, ResourceBundle resources) {
    	
    	hboxMargin.setSpacing(menu.getScreenWidth() - 800.0);
    	menu.setTableWidth(tableView, 50);
		List<animalDTO> animais = menu.getSharedAnimais();
		renderTable(animais);		
    }
 
  
    @FXML
    public void renderTable(List<animalDTO> animais) {
        NomePet.setCellValueFactory(new PropertyValueFactory<>("nome"));
        Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Emergencia.setCellValueFactory(new PropertyValueFactory<>("Emergencia"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
        Servicos.setCellValueFactory(new PropertyValueFactory<>("Servicos"));
        Orcamento.setCellValueFactory(new PropertyValueFactory<>("Orcamento"));
        NomeTutor.setCellValueFactory(new PropertyValueFactory<>("NomeTutor"));
        
      //=> Cria o botão para a coluna See
        See.setCellFactory(col -> new TableCell<animalDTO, Void>() {
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
                        animalDTO selectedAnimal = getTableRow().getItem();
                        if (selectedAnimal != null) {
                        	verAnimal(selectedAnimal);
                        }
                    });
                }
            }
        });
        
        //=> Cria o botão para a coluna See
        Edit.setCellFactory(col -> new TableCell<animalDTO, Void>() {
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
                    	animalDTO selectedAnimal = getTableRow().getItem();
                        if (selectedAnimal != null) {
                        	editarAnimal(selectedAnimal);
                        }
                    });
                }
            }
        });
    

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getItems().addAll(animais);
    }
    
    @FXML
    public void goBack() {
    	menu.loadContent("home.fxml", getClass());
    }
    
    public void verAnimal (animalDTO target){
	
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
    
    public void editarAnimal (animalDTO target){
    	int idPet = target.getId();
		int idCliente = target.getIdCliente();
		
		menu.setSharedIdPet(idPet);
		menu.setSharedId(idCliente);
		
		menu.loadContent("editarPet.fxml", menu.classe); 
    }


}
