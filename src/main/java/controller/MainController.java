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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
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
    private TabPane	tabs;
	@FXML
    private Label mainLabelArea;
	
	@FXML
    private Hyperlink deco;
	@FXML
    private Hyperlink profil;
    	 		
	@FXML
	private DirectorController directornameController;  //Creer un objet pour pouvoir interagir directement sur l'instance du DirectorController
	
	@FXML public void initialize() {
		//initialise l'onglet directeur
		directornameController.init(this);
		}


	public void initialize(URL arg0, ResourceBundle arg1) {
		
		mainLabelArea.setText(ClientApp.getNomPomp()); // recupere le nom du pompier et l'affiche dans la barre de titre

		if(ClientApp.isDirector()){ // test si le pompier est directeur
			director.setDisable(false); //si oui active l'onglet directeur
		}

	}
	
	public void onClickDirector(Event event){
		//methode appele a chaque changement d'onglet
		//Mise a jour de l'onglet directeur (meme si onglet clique selectionne autre que directeur
		directornameController.initialize(); 
	}
	
	public void onClicDeconnexion(Event event){
		// methode appelee quand on clique sur le lien deconnexion
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

/*	public void onClicProfil(Event event){ // Methode mise en commentaire car probleme avec fichier xml

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Profil.fxml"));
       
        try {

        	AnchorPane page = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Profil");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
          
        }
	}*/
}
