package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import restApplication.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class StatutController {

	@FXML
	private TextArea descrAreaStatut;
	
	@FXML
    private TextArea PersoAreaStatut;

    @FXML
    private TitledPane titleObtStatut;

    @FXML
    private Pane groupDescrStatut;

    @FXML
    private TitledPane titleRepassStatut;
    
    @FXML
    private ListView<String> obtStatutArea;

    @FXML
    private ListView<String> repassStatutArea;


    public void initialize() {
    	String text = "Centre ....." + "\n" + "Responsable ......" + "\n" + "Contact ....." + "\n" + 	
    			"Personne pr¨¦venir en cas dencident" + "\n" + "Infos ..... \n";
    	PersoAreaStatut.setText(text);
    }
    
    @FXML
    void onClicStatutObt(Event event) {
    	List<String> listSession = new ArrayList<String>();
    	listSession.add("Vous n'avez passe aucune session.");
    	listSession.add("Il est temps de candidater !");
    	
    	ObservableList<String> itemssession =FXCollections.observableArrayList (listSession);
    	obtStatutArea.setItems(itemssession);
    	putInfosUV();
    }

    @FXML
    void onClicRepassStatut(Event event) {
    	List<String> listSession = new ArrayList<String>();
    	listSession.add("Vous n'avez passe aucune session.");
    	listSession.add("Il est temps de candidater !");
    	
    	ObservableList<String> itemssession =FXCollections.observableArrayList (listSession);
    	repassStatutArea.setItems(itemssession);
    	putInfosUV();
    }
    
    public void putInfosUV(){
    	String infos = "Pas d'informations compleentaires disponibles pour cette UV.";
    	groupDescrStatut.setVisible(true);
    	descrAreaStatut.setText(infos);
    	descrAreaStatut.setEditable(false); //pour ne pas modifier le text area dans le programme
    }
    

}