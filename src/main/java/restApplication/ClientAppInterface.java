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
	
	// Get idSession by login and password by a creation of a new instance of the client/connection to server
	// Server return an error message if login/mdp not correct or user already connected
	public String login(int idPompier,String mdp);
			
	// Get IdSession
	public int getIdSession();
	
	// Get first name and last name of the user
	public String getNomPomp();
			
	// Test if the user is director
	// Return true if the user is director
	public boolean isDirector();
			
	// @param : idSession is the number of the session (clicked on the IHM and pushed by controller)  
	// Deconnect the session by server access, with a confirmation message (or an error message)
	public String deconnexion(int idSession);
	
	
	
//////////////////////Director Methods//////////////////////	
	
	// @param : full name of the stage (clicked on the IHM and pushed by controller)    
	// Test if the stage is closed (date of the end of candidature > current date)
	// Return true if the stage is not closed
	public boolean testDate(String nomStage);
	
	// @param : full name of the stage (clicked on the IHM and pushed by controller)   
	// Test if the stage has already started
	// Return true if the date of beginning of stage > current date
	public boolean testDateDebutStage(String nomStage);
	
	// Get list of the director stages by server access : to put into the director tab
	public List<String> getListSessionDirecteur();
	
	// @param : full name of the stage (clicked on the IHM and pushed by controller)    
	// @param : listLoading = 0 for "no handled candidat", 1 for "attente", 2 for "accepte", 3 for "refuse"
	// Get list of director candidates for a specific stage by server access : to put into the director tab
	public List<String> getListCandidatDirecteur(String ClickedItemSession, int listLoading);
		
	// @param : full name of the stage (clicked on the IHM and pushed by controller)   
	// Get the objet ListCandidats hosting all the lists (accepted, refused, pending, no handled candidates) : to put into the director tab
	public ListCandidats getListCandidatDirecteurGlobal(String ClickedItemSession);

	// @param : full name of the stage (clicked on the IHM and pushed by controller)   
	// @param : object hosting four updated lists of candidates (push by controller)
	// Push a updated list of candidates for a specific stage to the server : "Valider" button in the director tab
	public void validBoutonDirecteur(String session, ListCandidats updatedLists);
	
	// @param : full name of the stage (clicked on the IHM and pushed by controller)   
	// @param : object hosting four updated lists of candidates (push by controller)
	// Push a updated list of candidates for a specific stage to the server : "Sauvegarde temporaire" button in the director tab
	public void sauvTempBoutonDirecteur(String clicItemSession, ListCandidats updatedLists);
			
	// @param : full name of the stage (clicked on the IHM and pushed by controller)    
	// @param : jour/mois/annee (push by controller)	
	// Close the candidatures of a stage (push to the server) : associated to the "Cloturer" button in the director tab
	public String cloturerCandidature(String nomStage,int jour, int mois, int annee);

	
	
//////////////////////Formation Methods//////////////////////	
			
	// @param : date and place of the stage (clicked on the IHM and pushed by controller)  
	// Get boolean if the fireman is already candidate at this stage (present in one of the four lists)
	// Return true if he's already candidated
	public boolean isCandidate(String ClickedItemSession);
	
	// Get list of the formation UVs by server access : to put into the formation tab (apprenant radioButton)
	public List<String> getListUVApprenant();	
	
	// Get list of the formation UVs by server access : to put into the formation tab (formateur radioButton)
	public List<String> getListUVFormateur();	
			
	// @param : name of the UV (clicked on the IHM and pushed by controller)  
	// Get description for one UV : to put into the formation tab
	public String getDescriptionUV(String clickedItemUV);
			
	// @param : name of the UV (clicked on the IHM and pushed by controller)  
	// Get list of the formation stages for one UV, by server access : to put into the formation tab
	public List<String> getListSessionFormation(String clickedItemUV);
			
	// @param : date and place of the stage (clicked on the IHM and pushed by controller)  
	// Get detailled infos of the formation stage : to put into the formation tab
	public String getInfoDetailsFormation(String ClickedItemSession);
	
	// @param : date and place of the stage (clicked on the IHM and pushed by controller)  
	// Push a new candidating fireman for a specific stage to the server : "Candidater" button in the formation tab
	public void candidateBoutonFormation(String currentStage);
	
	// @param : date and place of the stage (clicked on the IHM and pushed by controller)  
	// Push a fireman who delete his candidacy, for a specific stage to the server : "Retirer" button in the formation tab
	public void retirerBoutonFormation(String currentStage);

	
	
//////////////////////Candidate Methods//////////////////////
	
	// @param : statut = 0 for "no handled candidat", 1 for "attente", 2 for "accepte", 3 for "refuse"
	// Get list of the candidate stages : to put into the candidate tab
	public List<String> getListSessionCandidate(int statut);
			
			
	
}