package controller;

import restApplication.*;

import java.net.URL;
import java.util.ResourceBundle;

import model.LectureUVFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import controller.DirectorRightController;
import controller.DirectorLeftController;


public class DirectorController {
	
	private MainController main;

	@FXML DirectorLeftController directorLeftController;
	@FXML DirectorRightController directorRightController;
	
	@FXML
	public void initialize() {
		directorLeftController.init(this);
		directorRightController.init(this);
		}
	
	public void loadTokenFromLeft(String token) {
		directorRightController.loadCandidats(token);
	}
	
    public void init(MainController main) {
		this.main = main;
	}
    
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		}

}
