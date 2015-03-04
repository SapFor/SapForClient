package controller; 
import java.net.URL;
import java.util.ResourceBundle;

import model.LectureUVFichier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
//import javafx.scene.control.TextArea;
import controller.DirectorController;

public class DirectorLeftController implements Initializable{
	
	private DirectorController director;
	

	    @FXML
	    private ListView<String> listUV;
	       	 	    	    	    
	    public void init(DirectorController directorController) {
			director = directorController;
		}
	    
  
	     // JC: initialisation de la Liste au chargement de la page
	    
	    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
	    assert listUV != null : "fx:id=\"listUV\" was not injected: check your FXML file 'Director-left.fxml'.";
	    LectureUVFichier fichierUV = new LectureUVFichier("C:/JC/Cours_Master2_CCI/Depot-Github/SapForClient/src/main/resources/UVname", 0);
    	ObservableList<String> items =FXCollections.observableArrayList (fichierUV.getListUV());
    	listUV.setItems(items);
    	
    	
    	listUV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	    	
    	    	LectureUVFichier fichier = new LectureUVFichier("C:/JC/Cours_Master2_CCI/Depot-Github/SapForClient/src/main/resources/" + newValue, 0);
    		    ObservableList<String> ObserListNom =FXCollections.observableArrayList (fichier.getListUV());
    		    director.loadTokenFromLeft(ObserListNom);
    	        System.out.println("Selected item: " + newValue);
    	    }

    	}
    );
}
	    
	
	   
    	
	  		
}
	
	
	

