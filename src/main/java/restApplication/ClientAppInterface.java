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
	
	// Get name fireman
	public String getNomPomp();
			
	
	// Get boolean if the fireman is director
	public boolean isDirector();
			
	// Deconnect the session
	public String deconnexion(int idSession);
	
	
	
//////////////////////Director Methods//////////////////////	
	
	// Test if the stage is closed (true == non closed)
	public boolean testDate(String nomStage);

	// Push a updated list of candidates for a specific stage to the server : "Valider" button in the director tab
	//public void enregBoutonDirecteur(String UVname, List<String> candidat, List<String> accepte, List<String> attente, List<String> refuse);
	void enregBoutonDirecteur(String session, ListCandidats updatedLists);
			
	// Close the candidatures of a stage : associated to the "Cloturer" button in the director tab
	public String cloturerCandidature(String nomStage,int jour, int mois, int annee);
			
	// Get list of the director stages : to put into the director tab
	public List<String> getListSessionDirecteur();
			
	// Get list of director candidates for a specific session : to put into the director tab
	// listLoading = 0 for "no handled candidat", 1 for "attente", 2 for "accepte", 3 for "refuse"
	public List<String> getListCandidatDirecteur(String ClickedItemSession, int listLoading);
	
	// Get the objet ListCandidats hosting all the list (accepted, refused, pending, no handled candidates) : to put into the director tab
	public ListCandidats getListCandidatDirecteurGlobal(String ClickedItemSession);
	
	// Get the number of candidates for a stage : to put into the director tab 
	public int getNbCandidats();
	

	
//////////////////////Formation Methods//////////////////////	
			
	// Get boolean if the fireman is already candidate at this stage
	// return true if he's already candidated
	public boolean isCandidate(String ClickedItemSession);
	
	// Get list of the formation UVs, apprenant radioButton : to put into the formation tab
	public List<String> getListUVApprenant();	
	
	// Get list of the formation UVs, formateur radioButton : to put into the formation tab
	public List<String> getListUVFormateur();	
			
	// Get description of the formation UVs : to put into the formation tab
	public String getDescriptionUV(String clickedItemUV);
			
	// Get list of the formation stages of the formation UVs : to put into the formation tab
	public List<String> getListSessionFormation(String clickedItemUV);
			
	// Get detailled infos of the formation stage : to put into the formation tab
	public String getInfoDetailsFormation(String ClickedItemSession);
	
	// Push a new candidating fireman for a specific stage to the server : "Candidater" button in the formation tab
	public void candidateBoutonFormation(String currentStage);
	
	// Push a fireman who delete his candidacy, for a specific stage to the server : "Retirer" button in the formation tab
	public void retirerBoutonFormation(String currentStage);
	
	
	
//////////////////////Candidate Methods//////////////////////
	
	// Get list of the candidate stages : to put into the candidate tab
	// listLoading = 0 for "no handled candidat", 1 for "attente", 2 for "accepte", 3 for "refuse"
	public List<String> getListSessionCandidate(int statut);
			
			
	
}