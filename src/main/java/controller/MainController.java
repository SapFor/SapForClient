package controller;

import restApplication.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 * FXML Main Controller class
 *
 * 
 */
public class MainController implements Initializable{
	
	@FXML
    private Tab formation;
	@FXML
    private Tab director;
	@FXML
    private Label mainLabelArea;
	@FXML
    private Hyperlink deco;
	@FXML
	private DirectorController directornameController;
	
	@FXML public void initialize() {
		directornameController.init(this);
		}


	public void initialize(URL arg0, ResourceBundle arg1) {
		//mainLabelArea.setText("Jean Dupont n�12345"); //test � remplacer par la ligne suivante lors de l'acc�s au serveur
		mainLabelArea.setText(ClientApp.getNomPomp());

		if(ClientApp.isDirector()){
			director.setDisable(false);
		}
					
	}
	
	public void onClickDirector(Event event){
	      // LectureUVFichier fichierUV = new LectureUVFichier(URLRessource+"UVname", 0);
		directornameController.initialize();
		System.out.println("clique director");
	}
	
	public void onClicDeconnexion(Event event){
		ClientApp.deconnexion(ClientApp.getIdSession());
		Stage currentStage = (Stage) deco.getScene().getWindow();
		currentStage.close();
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/application/application.css");

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} 
        catch (IOException e) { e.printStackTrace(); } 
	}

}
