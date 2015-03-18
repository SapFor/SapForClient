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
    	 		

	private DirectorController directornameController;
	
	@FXML public void initialize() {
		directornameController.init(this);
		}


	public void initialize(URL arg0, ResourceBundle arg1) {
		//mainLabelArea.setText("Jean Dupont n�12345"); //test � remplacer par la ligne suivante lors de l'acc鑣 au serveur
		mainLabelArea.setText(ClientApp.getNomPomp());

		if(ClientApp.isDirector()){
			director.setDisable(false);
		}
		
		// set default tab to Formation
		SingleSelectionModel<Tab> selectionModel = tabs.getSelectionModel();
		selectionModel.select(formation);
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

	public void onClicProfil(Event event){

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
	}
}
