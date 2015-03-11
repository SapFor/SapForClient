package controller;

import restApplication.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

/**
 * FXML Main Controller class
 *
 * 
 */
public class MainController implements Initializable{
	
	@FXML
    private Tab formations;
	@FXML
    private Tab director;
	@FXML
    private Label mainLabelArea;
    	 		

	public void initialize(URL arg0, ResourceBundle arg1) {
		mainLabelArea.setText("Jean Dupont n°12345"); //test à remplacer par la ligne suivante lors de l'accès au serveur
		//mainLabelArea.setText(ClientApp.getNomPomp());
		//if(ClientApp.isDirector()){
			director.setDisable(false);
		//}
					
	}

}
