package sistem.vet;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class App extends Application {
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
        setRoot("main","Sistema Veterinário 1.0");
    }

    static void setRoot(String fxml) throws IOException {
        setRoot(fxml,stage.getTitle());
    }

    @FXML
    static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        
        scene.getStylesheets().add(App.class.getResource("/styles/Styles.css").toExternalForm());  
    
        stage.setTitle(title);
        stage.getIcons().add(new Image("file:src/main/resources/imgs/app-icon.png"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
    	//final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
    	
        launch(args);
    }

	public static void modalAlert(String title, String mensagem) {
		Stage modalStage = new Stage();

        // Definindo a modalidade
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(title);
         
        Label label = new Label(mensagem);
        label.getStyleClass().add("alertaMessage");
        
        Button closeButton = new Button("Fechar");
        closeButton.setOnAction(e -> modalStage.close());

        VBox modalLayout = new VBox(10);
        modalLayout.getStyleClass().add("alertaDiv");
        modalLayout.getChildren().addAll(label, closeButton);
        
        Scene modalScene = new Scene(modalLayout, 500, 100);
        modalStage.setScene(modalScene);
        modalScene.getStylesheets().add(App.class.getResource("/styles/Styles.css").toExternalForm()); 

        // Mostrando a janela modal
        modalStage.showAndWait();
		
	}

}
