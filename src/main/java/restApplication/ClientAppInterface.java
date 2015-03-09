package restApplication;

import java.util.List;

/*
* Created with Eclipse.
* Author : Caroline Chabert
* Interface methods for controllers of IHM
* Belonging class : "ClientApp"
*/


import objectsTemplates.ListCandidats;
import objectsTemplates.PompierConcret;
import objectsTemplates.StageConcret;

public interface ClientAppInterface {
	
	// Get idSession by login and password
	public String login(int idPompier,String mdp);
			
	// Get IdSession
	public int getIdSession();
			
	// Deconnect the session
	public String deconnexion(int idSession);
	
	// Test if the stage is closed
	public boolean testDate(String nomStage);
			
	// Close the candidatures of a stage : associated to the "Candidater" button in the formation tab
	public String cloturerCandidature(String nomStage,int jour, int mois, int annee);
			
	// Get list of the director stages : to put into the director tab
	public List<String> getListSessionDirecteur();
			
	// Get list of director candidates for a specific session : to put into the director tab
	public List<String> getListCandidatDirecteur(String ClickedItemSession, int listLoading);
	
	// Get the objet ListCandidats hosting all the list (accepted, refused, pendind, all candidates) : to put into the director tab
	public ListCandidats getListCandidatDirecteurGlobal(String ClickedItemSession);
	
	// Get the number of candidates for a stage : to put into the director tab 
	public int getNbCandidats();
			
	// Get list of the formation UVs : to put into the formation tab
	public List<String> getListUVFormation();	
			
	// Get description of the formation UVs : to put into the formation tab
	public String getDescriptionUV(String clickedItemUV);
			
	// Get list of the formation stages of the formation UVs : to put into the formation tab
	public List<String> getListSessionFormation(String clickedItemUV);
			
	// Get detailled infos of the formation stage : to put into the formation tab
	public String getInfoDetailsFormation(String ClickedItemSession);
	
	// Push a updated list of candidates for a specific stage to the server : "Enregistrer" button in the director tab
	public void enregBoutonDirecteur(String UVname, List<String> candidat, List<String> accepte, List<String> attente, List<String> refuse);
			
	// Push a new candidating fireman for a specific stage to the server : "Candidater" button in the formation tab
	public void candidateBoutonFormation(String currentStage);
			
	
}