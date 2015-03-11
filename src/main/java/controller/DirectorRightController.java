package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import model.LectureUVFichier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * 
 * @author May Workman
 *
 */

public class DirectorRightController {
	String URLRessource=System.getProperty("user.dir")+"/src/main/resources/";
	private String sessionToken;
	private DirectorController director;
	private ObservableList<String> noDecisionList;
	private ObservableList<String> acceptedList;
	private ObservableList<String> refusedList;
	private ObservableList<String> pendingList;
	
	@FXML
    private GridPane gridCandidats;
	
	@FXML
    private BorderPane bdrPaneCandidats;
	
	@FXML
    private Button btnCloturer;
	
	@FXML
    private Button btnEnvoyer;
	
	@FXML
    private Button btnSauvTemp;
	

	/*public void loadCandidats(ObservableList<String> token) {
		loadGrid(token);
    }*/
	
	public void loadCandidats(String sessionID) {
		// 0 = candidates without accepted, refused or pending decision
		//ObservableList<String> noDecisionList =FXCollections.observableArrayList (getListCandidatDirecteur(sessionID,0));  
		// 1 = candidates on waiting list
		//ObservableList<String> pendingList =FXCollections.observableArrayList (getListCandidatDirecteur(sessionID,1));
		// 2 = candidates on accepted list
		//ObservableList<String> acceptedList =FXCollections.observableArrayList (getListCandidatDirecteur(sessionID,2));
		// 3 = candidates on refused list
		//ObservableList<String> refusedList =FXCollections.observableArrayList (getListCandidatDirecteur(sessionID,3));
		LectureUVFichier fichierNoDecision = new LectureUVFichier(URLRessource + "nodecision" , 0); // comment out line once connected to database
		LectureUVFichier fichierAccepted = new LectureUVFichier(URLRessource + "accepted" , 0); // comment out line once connected to database
		LectureUVFichier fichierRefused = new LectureUVFichier(URLRessource + "refused", 0); // comment out line once connected to database
		LectureUVFichier fichierPending = new LectureUVFichier(URLRessource + "pending", 0); // comment out line once connected to database
		noDecisionList =FXCollections.observableArrayList (fichierNoDecision.getListUV()); // comment out line once connected to database
		acceptedList =FXCollections.observableArrayList (fichierAccepted.getListUV()); // comment out line once connected to database
		refusedList =FXCollections.observableArrayList (fichierRefused.getListUV()); // comment out line once connected to database
		pendingList =FXCollections.observableArrayList (fichierPending.getListUV()); // comment out line once connected to database

		loadGrid(noDecisionList, acceptedList, refusedList, pendingList);
		loadButtons(sessionID);
	}

	public void init(DirectorController directorController) {
		director = directorController;
		
		
		
	}
	
/*	private ObservableMap<K,V> loadHashMap(ObservableList<String> noDecisionList, ObservableList<String> acceptedList, 
							 ObservableList<String> refusedList, ObservableList<String> pendingList ) {
		

		
	}*/
		
	private void loadGrid(ObservableList<String> noDecisionList, ObservableList<String> acceptedList, ObservableList<String> refusedList, ObservableList<String> pendingList) {

		gridCandidats.getChildren().clear(); // clear gridPane
		gridCandidats.setPadding(new Insets(20, 20, 20, 20));
		
		// setup column titles
		//Label counter = new Label("Candidats acceptés");
		Label candi = new Label(" Candidatures acceptées   ");
		TextField counter= new TextField("0");
		counter.setPrefWidth(40);
		Text txtNameTitle = new Text("Noms des Candidats");
		Text txtAccepteTitle = new Text("Accepté");
		Text txtRefuseTitle = new Text("Refusé");
		Text txtAttenteTitle = new Text("Liste d'Attente");
		
		HBox hbTitle = new HBox(txtAccepteTitle, txtRefuseTitle, txtAttenteTitle);
		HBox boxCounter = new HBox(candi,counter);
		boxCounter.setPadding(new Insets(10, 10, 10, 10));
		hbTitle.setSpacing(65);
		hbTitle.setPadding(new Insets(10, 10, 10, 10));
		//txtNameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		gridCandidats.add(txtNameTitle,0,0); // (txtNameTitle, column, row)
		gridCandidats.add(hbTitle,1,0);
		gridCandidats.add(boxCounter,0,1000);
		
		Iterator<String> noDecisionIter = noDecisionList.iterator();
		Iterator<String> acceptedIter = acceptedList.iterator();
		Iterator<String> refusedIter = refusedList.iterator();
		Iterator<String> pendingIter = pendingList.iterator();
		int i=1;
		String tempName;
		
		while (noDecisionIter.hasNext() || acceptedIter.hasNext() || refusedIter.hasNext() || pendingIter.hasNext()) {
			
			// setup of RadioButtons
			final RadioButton rdoAttente = new RadioButton();
			final RadioButton rdoRefuse = new RadioButton();
			final RadioButton rdoAccepte = new RadioButton();
			
			// only one RadioButton belonging to toggleGroup can be choosen at a given moment 
			final ToggleGroup radioGroup = new ToggleGroup();
			rdoAttente.setToggleGroup(radioGroup);
			rdoRefuse.setToggleGroup(radioGroup);
			rdoAccepte.setToggleGroup(radioGroup);
			rdoAttente.setVisible(true);
			rdoRefuse.setVisible(true);
			rdoAccepte.setVisible(true);
			
			// putting RadioButtons into HBox for easy horizontal distribution
			HBox hb = new HBox(rdoAccepte, rdoRefuse, rdoAttente);
			hb.setSpacing(100);
			hb.setPadding(new Insets(10, 10, 10, 10));
			
			if(noDecisionIter.hasNext()) {
				tempName = noDecisionIter.next();
			}
			else if(acceptedIter.hasNext()) {
				radioGroup.selectToggle(rdoAccepte);
				tempName = acceptedIter.next();
			}
			else if(refusedIter.hasNext()) {
				radioGroup.selectToggle(rdoRefuse);
				tempName = refusedIter.next();
			}
			else{		// pendingIter
				radioGroup.selectToggle(rdoAttente);
				tempName = pendingIter.next();
			}
			
			Text txtName = new Text();
			txtName.setText(tempName);
			rdoAccepte.setUserData(tempName);
			rdoRefuse.setUserData(tempName);
			rdoAttente.setUserData(tempName);
			
			// adding names and RadioButtons to gridPane
			gridCandidats.add(txtName, 0, i);
			gridCandidats.add(hb,1,i);
			i++;

			radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
			    	  removeAddList(oldToggle, newToggle, radioGroup,  rdoAccepte, rdoRefuse, rdoAttente);
			      }
			});
			    	
			

			
			
			
			// try to increase counter everytime the radio button is selected
						
						
			/*radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
	        {
	 			 public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle)
	            {
	 			int count = 0;			        	
	               if (radioGroup.getSelectedToggle() == rdoAccepte)
	                     {
	                       	count ++; counter.setText(""+count);
	                       	}
	            	   							                
	                else
	                { 
	                		                  		                    
	                }
	            } 
	        });*/
		} // end while
		
	} // end loadGrid
	
	private void removeAddList(Toggle oldToggle, Toggle newToggle, ToggleGroup radioGroup, RadioButton rdoAccepte, RadioButton rdoRefuse, RadioButton rdoAttente) {

		//refusedList.remove(rb.getUserData());
		
		if (radioGroup.getSelectedToggle() == newToggle) {
  		  if (oldToggle == rdoRefuse){
  			  try {
  				  refusedList.remove(rdoRefuse.getUserData());
  				  System.out.println("in here");
  				//  System.out.println(rdoAccepte.getUserData());
  				  System.out.println(rdoRefuse.getUserData());
  				//  System.out.println(rdoAttente.getUserData()); 				  
  			  }
  			  catch (Throwable t) {
  				  System.err.println("Error removing" + oldToggle.getUserData() + "from" + oldToggle);
  				  t.printStackTrace();
  			  }
  		 
	}
		}
	}
	
private void loadButtons(String sessionID){
		btnCloturer.setText("Clôturer la session");
		btnEnvoyer.setText("Valider les candidatures");
		btnSauvTemp.setText("Sauvegarde Temporaire");
		btnCloturer.setVisible(true);
		btnEnvoyer.setVisible(true);
		btnSauvTemp.setVisible(true);
		
		btnCloturer.setDisable(false); // delete line once connected to database
		btnEnvoyer.setDisable(false); // delete line once connected to database
		// if stage is closed testDate returns true
/*		if(testDate(sessionID)){		// uncomment once connected to database
			btnCloturer.setDisable(true);
			btnEnvoyer.setDisable(false);
		}
		else{
			btnCloturer.setDisable(false);
			btnEnvoyer.setDisable(true);
		}
*/		
		HBox hbButtons = new HBox(btnCloturer, btnEnvoyer, btnSauvTemp);
		hbButtons.setSpacing(90);
		hbButtons.setPadding(new Insets(10, 10, 10, 10));
		bdrPaneCandidats.setBottom(hbButtons);
	}	
	
	@FXML
	private void btnCloturerAction(ActionEvent event) {
		Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH) + 1; // January is 0
	    int day = cal.get(Calendar.DAY_OF_MONTH);
		//cloturerCandidature(getIdSession(), day, month, year);
	    btnCloturer.setDisable(true);
	 }
	
	@FXML
	private void btnEnvoyerAction(ActionEvent event) {
		// do I need a list noDecision??
		//enregBoutonDirecteur(getIdSession(), List<String> accepte, List<String> attente, List<String> refuse);
		btnEnvoyer.setDisable(true);
	 }
	
	
	@FXML
	private void btnSauverAction(ActionEvent event) {
		//enregBoutonDirecteur(getIdSession(), List<String> accepte, List<String> attente, List<String> refuse);
		
	
}
}


		
