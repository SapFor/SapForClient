package controller;

import model.LectureUVFichier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;


/**
 * 
 * @author May Workman
 *
 */

public class DirectorRightController {

	//private DirectorController director;
	private String sessionToken;
	private DirectorController director;
	
	@FXML
    private Label nom;
	
	@FXML
    private Label prenom;
	
	@FXML
    private ListView<String> listNom;
	
	@FXML
    private ListView<String> listPrenom;
	
	@FXML
    private Pane paneRadio;
	
	@FXML
    private ListView<Pane> listRadio;
	
	
		
				
/*	public void init(DirectorController directorController) {
		director = directorController;
	}*/
	
	public void getSessionToken(String token){
		sessionToken = token;
	}

	public void loadCandidats(ObservableList<String> token) {
		listNom.setItems(token);
		
    	/*ObservableList<Pane> myRadioBtnList = FXCollections.observableArrayList (paneRadio);
    	   	for(int i=0; i<token.size(); i++) {
    		myRadioBtnList.add(paneRadio);
        	paneRadio.setVisible(true);
    	}
    	listRadio.setItems(myRadioBtnList);*/

    }

    
    
		  public void init(DirectorController directorController) {
			director = directorController;
			}
		
	}



		
