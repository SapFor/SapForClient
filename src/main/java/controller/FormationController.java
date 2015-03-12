package controller;

import restApplication.ClientApp;

import java.net.URL;
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
		//Affichage des UV pour un apprenant
		ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVApprenant());
		UVList.setItems(ListeUV);
		//Gestion du bouton radio "formateur" si le pompier n'y a pas accès
		boutonFormateur.setDisable(ClientApp.getListUVFormateur().isEmpty());
	}

	@FXML
	/**
	 * Action au clic sur le nom d'un UV
	 * Affichage de la liste des sessions et de la description
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

	/**
	 * Action au clic sur le nom d'une session
	 * des infos détaillées, et mise à jour du bouton candidater/retirer
	 * @param event
	 */
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
	 * Action à la sélection du bouton radio "Apprenant" (par défaut)
	 * Affichage de la liste d'UV et vidage des autres champs
	 * @param event
	 */
	@FXML
	private void boutonApprenantClicked(ActionEvent event){
		//Affichage de la liste d'UV correspondante
		ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVApprenant());
		UVList.setItems(ListeUV);
		
		//Vidage des champs et désélection de l'UV dans la liste
		UVDesc.clear();
		SessionList.getSelectionModel().clearSelection();
		InfoSession.clear();
		UVList.getSelectionModel().clearSelection();
	}

	/**
	 * Action à la sélection du bouton radio "Formateur"
	 * Affichage de la liste d'UV et vidage des autres champs
	 * @param event
	 */
	@FXML
	private void boutonFormateurClicked(ActionEvent event){
		//Affichage de la liste d'UV correspondante
		ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVFormateur());
		UVList.setItems(ListeUV);
		
		//Vidage des champs et désélection de l'UV dans la liste
		UVDesc.clear();
		SessionList.getSelectionModel().clearSelection();
		InfoSession.clear();
		UVList.getSelectionModel().clearSelection();
	}
	
	/**
	 * Action au clic sur le bouton "candidater"
	 * @param event
	 */
	@FXML
	void clicCandidater(Event event) {
		//Récupération de l'UV
		String SessionSelect = SessionList.getSelectionModel().getSelectedItem();
		//Changement de bouton
		candidaterBt.setVisible(false);
		retirerBt.setVisible(true);
		//Envoi du nom de l'UV
		ClientApp.candidateBoutonFormation(SessionSelect);
	}
	
	/**
	 * Action au clic sur le bouton "retirer"
	 * @param event
	 */
	@FXML
	void clicRetirer(Event event) {
		//Récupération de l'UV
		String SessionSelect = SessionList.getSelectionModel().getSelectedItem();
		//Changement de bouton
		candidaterBt.setVisible(true);
		retirerBt.setVisible(false);
		//Envoi du nom de l'UV
		ClientApp.retirerBoutonFormation(SessionSelect);
	}
}