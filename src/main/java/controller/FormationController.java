package controller;

import restApplication.ClientApp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class FormationController implements Initializable{

	//Listes des UV et des sessions accessibles au candidat
	@FXML
	private ListView<String> UVList;
	@FXML
	private ListView<String> SessionList;
	
	//Boutons radio pour trier selon apprenant/formateur
	@FXML
	private RadioButton boutonApprenant;
	@FXML
	private RadioButton boutonFormateur;
	@FXML
	private ToggleGroup toggleGroupe = new ToggleGroup();
	
	//Champs de texte
	@FXML
	private TextArea UVDesc;
	@FXML
	private TextArea InfoSession;
	
	//Boutons de candidature
	@FXML
	private Button candidaterBt;
	@FXML
	private Button retirerBt;
	
	

	/**
	 * procédure d'initialisation
	 */
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVApprenant());
		UVList.setItems(ListeUV);
	}

	@FXML
	/**
	 * Evenement lors du clic sur le nom d'un UV
	 * @param event
	 */
	void clicUV(Event event) {
		// pour récupérer le nom de l'UV cliqué dans un String
		String UVSelectionne = UVList.getSelectionModel().getSelectedItem();
		
		//Affichge de la description de l'UV
		UVDesc.setText(ClientApp.getDescriptionUV(UVSelectionne));

		//pour ne pas modifier le text area dans le programme
		UVDesc.setEditable(false);

		//Affichage des la liste des sessions
		ObservableList<String> SessionUV = FXCollections.observableArrayList(ClientApp.getListSessionFormation(UVSelectionne));
		SessionList.setItems(SessionUV);
					
		// Clear le Texte area de info session quand on change d'UV.
		InfoSession.clear();
		
		// Clear la selection de session quand on change d'UV.
		SessionList.getSelectionModel().clearSelection();

		//Supprime les boutons de candidature
		candidaterBt.setVisible(false);
		retirerBt.setVisible(false);
	}


	@FXML
    void clicSession(Event event) {
    	
		//L'affichage des infos détaillées de la session 
		String infos= new String("les infos détaillées de la session"+
				"\n"+"La session se déroule à:...");
		InfoSession.setText(infos);

		//pour ne pas modifier le text area dans le programme
		InfoSession.setEditable(false);

		//afficher le bouton adequat une fois une session selectionnée
		String SessionSelect = SessionList.getSelectionModel().getSelectedItem();
		
		if(!ClientApp.isCandidate(SessionSelect)){
			candidaterBt.setVisible(true);
			retirerBt.setVisible(false);
		}
		else{
			retirerBt.setVisible(true);
			candidaterBt.setVisible(false);
		}
	}

	/**
	 * Affichage des UV pour le bouton apprenant sélectionné
	 * @param event
	 */
	@FXML
	private void boutonApprenantClicked(ActionEvent event){
//		ArrayList<String> ListeUVList = new  ArrayList<String>();
//		ListeUVList.add("INC1");
//		ListeUVList.add("INC2");
//		ListeUVList.add("DEV1");
//		ListeUVList.add("OC1");
		
		ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVApprenant());
		
		UVList.setItems(ListeUV);
		
		UVDesc.clear();
		SessionList.getSelectionModel().clearSelection();
		InfoSession.clear();
		// Clear la selection d'UV quand on change de mode apprenant ou formateur.
		UVList.getSelectionModel().clearSelection();
	}

	/**
	 * Affichage des UV pour le bouton formateur sélectionné
	 * @param event
	 */
	@FXML
	private void boutonFormateurClicked(ActionEvent event){
		//ArrayList<String> ListeUVList = new  ArrayList<String>();
		//ListeUVList.add("FORM1");
		//ListeUVList.add("FORM2");

		ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVFormateur());
		UVList.setItems(ListeUV);
		
		UVDesc.clear();
		SessionList.getSelectionModel().clearSelection();
		InfoSession.clear();
		UVList.getSelectionModel().clearSelection();
	}
	

	@FXML
	void clicCandidater(Event event) {
		
		String SessionSelect = SessionList.getSelectionModel().getSelectedItem();
		String UVSelect = UVList.getSelectionModel().getSelectedItem();
		String IdSession = UVSelect+SessionSelect;
		
		//if(ClientApp.isCandidate(SessionSelect)){
			candidaterBt.setVisible(false);
			retirerBt.setVisible(true);
			ClientApp.candidateBoutonFormation(SessionSelect);

		//}

		//System.out.println(IdSession);
	}
	
	@FXML
	void clicRetirer(Event event) {
		
		String SessionSelect = SessionList.getSelectionModel().getSelectedItem();
		String UVSelect = UVList.getSelectionModel().getSelectedItem();
		String IdSession = UVSelect+SessionSelect;
	
		candidaterBt.setVisible(true);
		retirerBt.setVisible(false);
		ClientApp.retirerBoutonFormation(SessionSelect);

		

		//System.out.println(IdSession);
	}
}