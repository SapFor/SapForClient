package controller;

import restApplication.*;
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
	
	@FXML
    private GridPane gridCandidats;
	
	@FXML
    private BorderPane bdrPaneCandidats;
	
	@FXML
    private Button btnCloturer;
	
	@FXML
    private Button btnEnvoyer;

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
		ObservableList<String> noDecisionList =FXCollections.observableArrayList (fichierNoDecision.getListUV()); // comment out line once connected to database
		ObservableList<String> acceptedList =FXCollections.observableArrayList (fichierAccepted.getListUV()); // comment out line once connected to database
		ObservableList<String> refusedList =FXCollections.observableArrayList (fichierRefused.getListUV()); // comment out line once connected to database
		ObservableList<String> pendingList =FXCollections.observableArrayList (fichierPending.getListUV()); // comment out line once connected to database
//		loadHashMap(noDecisionList, acceptedList, refusedList, pendingList ); 
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
		
		while (noDecisionIter.hasNext() || acceptedIter.hasNext() || refusedIter.hasNext() || pendingIter.hasNext()) {
	
			// setup of RadioButtons
			final RadioButton rdoAttente = new RadioButton(); 
			final RadioButton rdoRefuse = new RadioButton(); 
			RadioButton rdoAccepte = new RadioButton(); 
			
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
			
			Text txtName = new Text();
			
			if(noDecisionIter.hasNext()) {
				//System.out.println(noDecisionIter.next());
				txtName.setText(noDecisionIter.next());
			}
			else if(acceptedIter.hasNext()) {
				radioGroup.selectToggle(rdoAccepte);
				txtName.setText(acceptedIter.next());
			}
			else if(refusedIter.hasNext()) {
				radioGroup.selectToggle(rdoRefuse);
				txtName.setText(refusedIter.next());
			}
			else{		// pendingIter
				radioGroup.selectToggle(rdoAttente);
				txtName.setText(pendingIter.next());
			}

			// adding names and RadioButtons to gridPane
			gridCandidats.add(txtName, 0, i);
			gridCandidats.add(hb,1,i);
			i++;
			
			radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov,
			              Toggle oldToggle, Toggle newToggle) {
			            if (radioGroup.getSelectedToggle() == rdoAttente) {
			            	// delete name from oldToggle list
			            	// put name into newToggle list (rdoAttente)
			            	// save to database (in some sort of temp way)
			            	// reload list ????
			            }
			            else if (radioGroup.getSelectedToggle() == rdoRefuse) {
			            	// delete name from oldToggle list
			            	// put name into newToggle list (rdoRefuse)
			            	// save to database (in some sort of temp way)
			            	// reload list ????
			            }
			            else {		// radioGroup.getSelectedToggle() == rdoAttente
			            	// delete name from oldToggle list
			            	// put name into newToggle list (rdoAttente)
			            	// save to database (in some sort of temp way)
			            	// reload list ????
			            }
			            
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
		
	
	
private void loadButtons(String sessionID){
		btnCloturer.setText("Clôturer la session");
		btnEnvoyer.setText("Valider les candidatures");
		btnCloturer.setVisible(true);
		btnEnvoyer.setVisible(true);
		
		btnCloturer.setDisable(false); // delete line once connected to database
		btnEnvoyer.setDisable(true); // delete line once connected to database
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
		HBox hbButtons = new HBox(btnCloturer, btnEnvoyer);
		hbButtons.setSpacing(100);
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
	 }
	
	@FXML
	private void btnEnvoyerAction(ActionEvent event) {
		
		//enregBoutonDirecteur(getIdSession(), List<String> accepte, List<String> attente, List<String> refuse);
	 }
}


		
