package controller;

import restApplication.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;


public class CandidatureController {

	@FXML
    private TitledPane titleAccepteesCand;
	
	@FXML
    private ListView<String> acceptCandArea;

    @FXML
    void onClicCandAccept(Event event) {
    	List<String> listSession = ClientApp.getListSessionCandidate(3);
    	
    	ObservableList<String> itemssession =FXCollections.observableArrayList (listSession);
    	acceptCandArea.setItems(itemssession);
    	
    	

    }
	
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		}

}