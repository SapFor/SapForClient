package controller; 

import restApplication.*;

import java.net.URL;
import java.util.ResourceBundle;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import controller.DirectorController;

public class DirectorLeftController implements Initializable{
	
	String URLRessource=System.getProperty("user.dir")+"/src/main/resources/";
	private DirectorController director;
	

	    @FXML
	    private ListView<String> listUV;
	    
	    public URL urlleft;
	    
	    public ResourceBundle resleft;
	       	 	    	    	    
	    public void init(DirectorController directorController) {
			director = directorController;
			this.initialize(urlleft, resleft);
		}
	    
  
	     // JC: initialisation de la Liste au chargement de la page
	    
	    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
	    
	    ObservableList<String> items =FXCollections.observableArrayList (ClientApp.getListSessionDirecteur());
    	listUV.setItems(items);
    	
    	urlleft=fxmlFileLocation;
    	resleft=resources;
    	
    	listUV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        		
    	    	director.loadTokenFromLeft(newValue);
    	    }

			
			

    	});
	  
	    }
	    
	
	   
    	
	  		
}
	
	
	

