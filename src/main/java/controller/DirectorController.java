package controller;

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

	@FXML DirectorLeftController directorLeftController;
	@FXML DirectorRightController directorRightController;
	
	@FXML public void initialize() {
		directorLeftController.init(this);
		directorRightController.init(this);
		}
	
	public void loadTokenFromLeft(ObservableList<String> token) {
		directorRightController.loadCandidats(token);
	}
	

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		}

}
