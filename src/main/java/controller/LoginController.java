package controller;

import restApplication.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

	@FXML
    private TextArea mdpArea;

    @FXML
    private Button validerLoginButton;

    @FXML
    private TextArea loginArea;
    
    @FXML
    private Label errorArea;
    
	@FXML
    void onClicValiderLogin(Event event) {
		
	/////// utilisation sans serveur /////////////////
		
		/*Stage currentStage = (Stage) validerLoginButton.getScene().getWindow();
    	currentStage.close();
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Main.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			//stage = (Stage) root.getScene().getWindow();
			scene.getStylesheets().add("/application/application.css");

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.show();
			
		} 
        catch (IOException e) { e.printStackTrace(); } */
		
		
		
  /////// utilisation avec serveur /////////////////
		
    	
    	int idPompier = Integer.parseInt(loginArea.getText());
        String mdp = mdpArea.getText();
        
        String reponse = ClientApp.login(idPompier, mdp);
        if(reponse == "ok"){
    		Stage currentStage = (Stage) validerLoginButton.getScene().getWindow();
        	currentStage.close();
        	
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Main.fxml")); 
            try {
    			Parent root = fxmlLoader.load();
    			Scene scene = new Scene(root);
    			//stage = (Stage) root.getScene().getWindow();
    			scene.getStylesheets().add("/application/application.css");

    			Stage stage = new Stage();
    			stage.setScene(scene);
				stage.setMaximized(true);
    			stage.show();
    			
    		} 
            catch (IOException e) { e.printStackTrace(); } 
        }
        else { 
        	String erreur = reponse.toUpperCase() + " : le login et/ou le mot de passe sont incorrects.";
        	errorArea.setText(erreur);
        	errorArea.setTextFill(Color.RED);
        	errorArea.setVisible(true);
        	loginArea.setText("");
        	mdpArea.setText("");
        }
        
    }
	
	public void initialize(URL arg0, ResourceBundle arg1) {
					
	}
    
	
	

}
