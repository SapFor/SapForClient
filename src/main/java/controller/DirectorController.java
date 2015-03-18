package controller;

import restApplication.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import controller.DirectorRightController;
import controller.DirectorLeftController;


public class DirectorController {
	
	private MainController main;

	@FXML DirectorLeftController directorLeftController; //Creer un objet pour pouvoir interagir directement sur l'instance du directorLeftController 
	@FXML DirectorRightController directorRightController;//Creer un objet pour pouvoir interagir directement sur l'instance du directorRightController
	
	@FXML
	public void initialize() { 
		// methode qui initialise le contenu de l'onglet directeur
		directorLeftController.init(this);
		directorRightController.init(this);
		}
	
	public void loadTokenFromLeft(String token) {
		//methode qui recupere la liste des candidats du stage affiche dans le coté droit
		directorRightController.loadCandidats(token);
	}
	
    public void init(MainController main) {
    	//permet de recuperer l'instance du MainController (methode inherante à la communication entre element dans JavaFX)
		this.main = main;
	}
    
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		}

}
