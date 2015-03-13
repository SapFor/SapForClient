package controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import objectsTemplates.*;
import restApplication.ClientApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * 
 * @author May Workman
 *
 */

public class DirectorRightController {
	private String session;
	private DirectorController director;
	private ObservableList<String> noDecisionList;
	private ObservableList<String> acceptedList;
	private ObservableList<String> refusedList;
	private ObservableList<String> attenteList;
	private int countAttente;
	private int countRefuse;
	private int countAccepte;
	private Text txtCounter;
	private Text txtcountAccept;
	private Text txtcountRefuse;
	private Text txtcountAttente;
	private Line line1;
	private HBox boxCounter;

	
	@FXML
    private GridPane gridCandidats;
	
	@FXML
	private BorderPane bdrPaneCandidats;
	
	@FXML
    private ScrollPane scrollCandidats;
	
	@FXML
    private TitledPane titleCandidats;
	
	@FXML
    private Button btnCloturer;
	
	@FXML
    private Button btnEnvoyer;
	
	@FXML
    private Button btnSauvTemp;
	
	public void loadCandidats(String sessionID) {
		this.session = sessionID;
		noDecisionList =FXCollections.observableArrayList (ClientApp.getListCandidatDirecteur(sessionID,0)); 
		acceptedList =FXCollections.observableArrayList (ClientApp.getListCandidatDirecteur(sessionID,2));
		refusedList =FXCollections.observableArrayList (ClientApp.getListCandidatDirecteur(sessionID,3)); 
		attenteList =FXCollections.observableArrayList (ClientApp.getListCandidatDirecteur(sessionID,1));

		loadGrid();
		loadCounter();
		loadButtons();
	}

	public void init(DirectorController directorController) {
		director = directorController;
	}
	

	private void loadGrid() {
	
		scrollCandidats.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollCandidats.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
				
		// setup gridPane
		gridCandidats.getChildren().clear(); 
		gridCandidats.setPadding(new Insets(20, 20, 20, 20));

		// setup column titles
		Text txtNameTitle = new Text("Noms des Candidats");
		Text txtAccepteTitle = new Text("Accepté");
		Text txtRefuseTitle = new Text("Refusé");
		Text txtAttenteTitle = new Text("Liste d'Attente");
	//	txtNameTitle.setFont(Font.font(null, FontWeight.SEMI_BOLD, 15));
	//	txtAccepteTitle.setFont(Font.font(null, FontWeight.SEMI_BOLD, 15));
	//	txtRefuseTitle.setFont(Font.font(null, FontWeight.SEMI_BOLD, 15));
	//	txtAttenteTitle.setFont(Font.font(null, FontWeight.SEMI_BOLD, 15));
		
		// setup HBox containing column titles
		HBox hbTitle = new HBox(txtAccepteTitle, txtRefuseTitle, txtAttenteTitle);
		hbTitle.setAlignment(Pos.CENTER);
		hbTitle.setSpacing(55);
		hbTitle.setPadding(new Insets(50, 10, 50, 10));
		hbTitle.setMinHeight(100);
		gridCandidats.add(txtNameTitle,0,0); // (txtNameTitle, column, row)
		gridCandidats.add(hbTitle,1,0);
		
		// setup necessary to begin accessing data in lists
		Iterator<String> noDecisionIter = noDecisionList.iterator();
		Iterator<String> acceptedIter = acceptedList.iterator();
		Iterator<String> refusedIter = refusedList.iterator();
		Iterator<String> attenteIter = attenteList.iterator();
		int countLines=1;		// number of candidate rows, not including titles
		String tempName;
		countAttente = 0;
		countRefuse = 0;
		countAccepte = 0;
		
		while (noDecisionIter.hasNext() || acceptedIter.hasNext() || refusedIter.hasNext() || attenteIter.hasNext()) {
			
			// setup of RadioButtons
			final RadioButton rdoAttente = new RadioButton();
			final RadioButton rdoRefuse = new RadioButton();
			final RadioButton rdoAccepte = new RadioButton();
			
			// only one RadioButton belonging to toggleGroup can be chosen at a given moment 
			final ToggleGroup radioGroup = new ToggleGroup();
			rdoAttente.setToggleGroup(radioGroup);
			rdoRefuse.setToggleGroup(radioGroup);
			rdoAccepte.setToggleGroup(radioGroup);
			rdoAttente.setVisible(true);
			rdoRefuse.setVisible(true);
			rdoAccepte.setVisible(true);
			
			// put RadioButtons into HBox for easy horizontal distribution
			HBox hb = new HBox(rdoAccepte, rdoRefuse, rdoAttente);
			hb.setSpacing(100);
			hb.setAlignment(Pos.CENTER);
			hb.setPadding(new Insets(10, 10, 10, 10));
			
			if(noDecisionIter.hasNext()) {
				tempName = noDecisionIter.next();
			}
			else if(acceptedIter.hasNext()) {
				countAccepte++;
				radioGroup.selectToggle(rdoAccepte);
				tempName = acceptedIter.next();
			}
			else if(refusedIter.hasNext()) {
				countRefuse++;
				radioGroup.selectToggle(rdoRefuse);
				tempName = refusedIter.next();
			}
			else{		// attenteIter
				countAttente++;
				radioGroup.selectToggle(rdoAttente);
				tempName = attenteIter.next();
			}
			
			// setup of Text node 
			Text txtName = new Text();
			txtName.setText(tempName);
			
			// associate the candidates name (tempName) with each radioButton
			rdoAccepte.setUserData(tempName);
			rdoRefuse.setUserData(tempName);
			rdoAttente.setUserData(tempName);
			
			// adding names and RadioButtons to gridPane
			gridCandidats.add(txtName, 0, countLines);
			gridCandidats.add(hb, 1, countLines);
			countLines++;

			radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			      public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
			    	  updateList(oldToggle, newToggle, rdoAccepte, rdoRefuse, rdoAttente);
			    	  cleanCounter();
			    	  loadCounter();
			      }
			});
		} // end while	
	} // end loadGrid()
	
	private void updateList(Toggle oldToggle, Toggle newToggle, RadioButton rdoAccepte, RadioButton rdoRefuse, RadioButton rdoAttente) {
		
		String nameMoving = oldToggle.getUserData().toString();
		
		// Remove from old list
		if (oldToggle == rdoAccepte){
			try {
				acceptedList.remove(nameMoving);
				countAccepte--;
			}
			catch (Throwable t) {
				System.err.println("Error removing" + nameMoving + "from" + oldToggle);
				t.printStackTrace();
			}
		}
		else if (oldToggle == rdoRefuse){
			  try {
				  refusedList.remove(nameMoving);
				  countRefuse--;
			  }
			  catch (Throwable t) {
				  System.err.println("Error removing" + nameMoving + "from" + oldToggle);
				  t.printStackTrace();
			  }
		}
		else if (oldToggle == rdoAttente){
			  try {
				  attenteList.remove(nameMoving);
				  countAttente--;
			  }
			  catch (Throwable t) {
				  System.err.println("Error removing" + nameMoving + "from" + oldToggle);
				  t.printStackTrace();
			  }
		}
		else {		// noDecisionList
			  try {
				  noDecisionList.remove(nameMoving);
			  }
			  catch (Throwable t) {
				  System.err.println("Error removing" + nameMoving + "from noDecisionList");
				  t.printStackTrace();
			  }
		}
		
		// Add to new list
		if (newToggle == rdoAccepte){
			try {
				acceptedList.add(nameMoving);
				countAccepte++;
			}
			catch (Throwable t) {
				System.err.println("Error adding" + nameMoving + "to" + newToggle);
				t.printStackTrace();
			}
		}
		else if (newToggle == rdoRefuse){
			try {
				refusedList.add(nameMoving);
				countRefuse++;
			}
			catch (Throwable t) {
				System.err.println("Error adding" + nameMoving + "to" + newToggle);
				t.printStackTrace();
			}
		}
		else if (newToggle == rdoAttente){
			  try {
				  attenteList.add(nameMoving);
				  countAttente++;
			  }
			  catch (Throwable t) {
				  System.err.println("Error adding" + nameMoving + "to" + newToggle);
				  t.printStackTrace();
			  }
		}
		else {		// noDecisionList
			  try {
				  noDecisionList.add(nameMoving);
			  }
			  catch (Throwable t) {
				  System.err.println("Error adding" + nameMoving + "to" + newToggle);
				  t.printStackTrace();
			  }
		}
	} // end updateList()

	private void loadCounter(){
		line1 = new Line(0, 0, 600, 0);
		txtCounter = new Text("Nombre de candidats : ");
		txtcountAccept = new Text(String.valueOf(countAccepte));
		txtcountRefuse = new Text(String.valueOf(countRefuse));
		txtcountAttente = new Text(String.valueOf(countAttente));
		boxCounter = new HBox(txtcountAccept, txtcountRefuse, txtcountAttente);
		boxCounter.setSpacing(120);
		boxCounter.setPadding(new Insets(10, 10, 10, 10));
		boxCounter.setAlignment(Pos.CENTER);
		gridCandidats.add(line1, 0, 1000);
		gridCandidats.add(txtCounter, 0, 1001);
		gridCandidats.add(boxCounter, 1, 1001);
	}

		
	private void cleanCounter(){
		gridCandidats.getChildren().remove(line1);
		gridCandidats.getChildren().remove(txtCounter);
		gridCandidats.getChildren().remove(txtcountAccept);
		gridCandidats.getChildren().remove(txtcountRefuse);
		gridCandidats.getChildren().remove(txtcountAttente);
		gridCandidats.getChildren().remove(boxCounter);
	}
	
	
	
	private void loadButtons(){
		btnCloturer.setText("Clôturer la session");
		btnEnvoyer.setText("Valider les candidatures");
		btnSauvTemp.setText("Sauvegarde Temporaire");
		btnCloturer.setVisible(true);
		btnEnvoyer.setVisible(true);
		btnSauvTemp.setVisible(true);
		
		// if stage is closed testDate returns true
		if(ClientApp.testDate(session)){
			btnCloturer.setDisable(true);
			btnEnvoyer.setDisable(false);
		}
		else {
			btnCloturer.setDisable(false);
			btnEnvoyer.setDisable(true);
		}
		
		HBox hbButtons = new HBox(btnCloturer, btnEnvoyer, btnSauvTemp);
		hbButtons.setAlignment(Pos.CENTER);
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
	    ClientApp.cloturerCandidature(session, day, month, year);
	    btnCloturer.setDisable(true);
	    btnEnvoyer.setDisable(false);
	 }
	
	@FXML
	private void btnEnvoyerAction(ActionEvent event) {
		ListCandidats list = new ListCandidats();
		list.setAccepte(noDecisionList);
		list.setAccepte(acceptedList);
		list.setAccepte(refusedList);
		list.setAccepte(attenteList);
		ClientApp.validBoutonDirecteur(session, list);
		btnEnvoyer.setDisable(true);
	 }
	
	
	@FXML
	private void btnSauverAction(ActionEvent event) {
		ListCandidats list = new ListCandidats();
		list.setAccepte(noDecisionList);
		list.setAccepte(acceptedList);
		list.setAccepte(refusedList);
		list.setAccepte(attenteList);
		//ClientApp.validBoutonDirecteur(session, list);
	}
}