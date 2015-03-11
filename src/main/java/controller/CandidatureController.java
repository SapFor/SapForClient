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
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;


public class CandidatureController {

	@FXML
    private TitledPane titleAccepteesCand;
	
	@FXML
    private TitledPane titleAttenteCand;
	
	@FXML
    private TitledPane titleNonClotureesCand;
	
	@FXML
    private TitledPane titleRefuseesCand;
	
	@FXML
    private ListView<String> acceptCandArea;
	
	@FXML
    private ListView<String> attentCandArea;
	
	@FXML
    private ListView<String> nonClotCandArea;
	
	@FXML
    private ListView<String> refusCandArea;
	
	@FXML
    private TextArea UVInfosCand;

    @FXML
    void onClicCandAccept(Event event) {
    	List<String> listSession = ClientApp.getListSessionCandidate(2);
    	
    	ObservableList<String> itemssession =FXCollections.observableArrayList (listSession);
    	acceptCandArea.setItems(itemssession);
    	putInfosUV();
    }
    
    @FXML
    void onClicCandAttent(Event event) {
    	List<String> listSession = ClientApp.getListSessionCandidate(1);
    	
    	ObservableList<String> itemssession =FXCollections.observableArrayList (listSession);
    	attentCandArea.setItems(itemssession);
    	putInfosUV();
    }
    
    @FXML
    void onClicCandNonClot(Event event) {
    	List<String> listSession = ClientApp.getListSessionCandidate(0);
    	
    	ObservableList<String> itemssession =FXCollections.observableArrayList (listSession);
    	nonClotCandArea.setItems(itemssession);  
    	putInfosUV();
    }
    
    @FXML
    void onClicCandRefus(Event event) {
    	List<String> listSession = ClientApp.getListSessionCandidate(3);
    	
    	ObservableList<String> itemssession =FXCollections.observableArrayList (listSession);
    	refusCandArea.setItems(itemssession);  
    	putInfosUV();
    }
    
    public void putInfosUV(){
    	String infos = "Pas d'informations complémentaires disponibles pour cette UV.";
    	UVInfosCand.setText(infos);
    	UVInfosCand.setEditable(false); //pour ne pas modifier le text area dans le programme
    }
	
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		
	}

}