package controller;

import restApplication.*;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

	@FXML
    private PasswordField mdpArea;
	
    @FXML
    private Button validerLoginButton;

    @FXML
    private TextField loginArea;
    
    @FXML
    private Label errorArea;
    
	@FXML
    void onClicValiderLogin(Event event) {
		// envoie au serveur l'id du pompier et son mot de passe
		//si ok charge le main.fxml (l'application)
    	int idPompier = Integer.parseInt(loginArea.getText());
        String mdp = mdpArea.getText();
        String reponse = ClientApp.login(idPompier, mdp); //appel de la methode "login" du modele client
        if(reponse == "ok"){
    		Stage currentStage = (Stage) validerLoginButton.getScene().getWindow();
        	currentStage.close();
        	
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Main.fxml")); 
            try {
    			Parent root = fxmlLoader.load();
    			Scene scene = new Scene(root);
    			scene.getStylesheets().add("/application/application.css");

    			Stage stage = new Stage();
    			stage.setScene(scene);
    			stage.setTitle("SapFor Candidature");
				stage.setMaximized(true);
    			stage.show();
    			
    		} 
            catch (IOException e) { e.printStackTrace(); } 
        }


        else if(reponse == "Vous etes deja connecte"){
        	String erreur = reponse + " : deconnectez la session ouverte.";

        	errorArea.setText(erreur);
        	errorArea.setTextFill(Color.RED);
        	errorArea.setVisible(true);
        	loginArea.setText("");
        	mdpArea.setText("");
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
	
	
	public void initialize() {
		
		//pour forcer le focus sur loginArea utiliser un run sur le request
		Platform.runLater(new Runnable() {
	        public void run() {
	        	loginArea.requestFocus(); 
	        }
	    });

	}

}
