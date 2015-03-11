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
	void clicUV(Event event) {

		//L'affichage de la descritpion de l'UV
		String description= new String("la description de l'UV");
		UVDesc.setText(description);

		//pour ne pas modifier le text area dans le programme
		UVDesc.setEditable(false);

		// pour récupérer le nom de l'UV cliqué dans un String
		String UVclic = UVList.getSelectionModel().getSelectedItem();

//		if (UVclic=="INC1"){
//			//l'affichage des sessions 
//			ArrayList<String> SessionUVList = new  ArrayList<String>();
//			SessionUVList.add("Rennes");
//			SessionUVList.add("St Malo");
//			SessionUVList.add("Redon");
//			ObservableList<String> SessionUV = FXCollections.observableArrayList(SessionUVList);
//
//			SessionList.setItems(SessionUV);}
//		else {
//
//			ArrayList<String> SessionUVList = new  ArrayList<String>();
//			SessionUVList.add("Nantes");
//			SessionUVList.add("Laval");
//			SessionUVList.add("Reims");
			ObservableList<String> SessionUV = FXCollections.observableArrayList(ClientApp.getListUVApprenant());

			SessionList.setItems(SessionUV);
		//}
						
			// Clear le Texte area de info session quand on change d'UV.
			InfoSession.clear();
			
			// Clear la selection de session quand on change d'UV.
			SessionList.getSelectionModel().clearSelection();

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
		boolean bool=true;
		if (bool){
			candidaterBt.setVisible(true);
		}
		else {
			retirerBt.setVisible(true);
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
		ArrayList<String> ListeUVList = new  ArrayList<String>();
		ListeUVList.add("FORM1");
		ListeUVList.add("FORM2");

		ObservableList<String> ListeUV = FXCollections.observableArrayList(ListeUVList);
		UVList.setItems(ListeUV);
		
		UVDesc.clear();
		SessionList.getSelectionModel().clearSelection();
		InfoSession.clear();
		UVList.getSelectionModel().clearSelection();
	}
	

	@FXML
	void clicCandidater(Event event) {
		candidaterBt.setVisible(false);
		retirerBt.setVisible(true);
		String SessionSelect = SessionList.getSelectionModel().getSelectedItem();
		String UVSelect = UVList.getSelectionModel().getSelectedItem();
		String IdSession = UVSelect+SessionSelect;
		System.out.println(IdSession);

	}
	
	@FXML
	void clicRetirer(Event event) {
		
		candidaterBt.setVisible(true);
		retirerBt.setVisible(false);
		
		String SessionSelect = SessionList.getSelectionModel().getSelectedItem();
		String UVSelect = UVList.getSelectionModel().getSelectedItem();
		String IdSession = UVSelect+SessionSelect;
		System.out.println(IdSession);
	}


}