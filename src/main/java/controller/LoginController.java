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
		
	/////// utilisation sans serveur /////////////////
		/*
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
		*/
		
		
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
        else if(reponse == "Vous êtes déjà connecté"){
        	String erreur = reponse + " : déconnectez la session ouverte.";
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
	
		// En utilisant un TextField pour le login et un PasswordField pour le mdp, 
		//ENTER actionne Valider et TAB change le focus.
		// Si utilisation de TextArea pour gÃ©rer le login et un password visible utiliser les mÃ©thodes ci-dessous:
		// ce code permet seulement de changer le focus avec TAB et ENTER. 
		// Quand Focus sur : Valider + ENTER = action sur Valider
	
		/*loginArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	            public void handle(KeyEvent event) {
	                if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER ) {
	                    mdpArea.requestFocus();
	                    event.consume();
	                }
	            }
	        });
		mdpArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER ) {
                	validerLoginButton.requestFocus();
                    event.consume();
                }
            }
        });
			
		validerLoginButton.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.TAB ) {
                	loginArea.requestFocus();
                    event.consume();

                }
            }
        });*/
	}

}
