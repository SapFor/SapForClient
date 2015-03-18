package objectsTemplates;

import java.util.List;
/*
* Created with Eclipse.
* Author : Caroline Chabert
* Class to get and set lists of stages data, by the client
*/

public class ListCandidats { // Classe regroupant les 4 listes d'un stage
	
	private List<String> candidat;
	private List<String> accepte;
	private List<String> attente;
	private List<String> refuse;
	
	
	/*
	 * Candidates future UV session
	 */
	public void setCandidat(List<String> candidat) { this.candidat = candidat; }
	public List<String> getCandidat(){ return candidat; }
	
	/*
	 * Accepted future UV session
	 */
	public void setAccepte(List<String> accepte) { this.accepte = accepte; }
	public List<String> getAccepte(){ return accepte; }
	
	/*
	 * Pending future UV session
	 */
	public void setAttente(List<String> attente) { this.attente = attente; }
	public List<String> getAttente(){ return attente; }
	
	/*
	 * Refused future UV session
	 */
	public void setRefuse(List<String> refuse) { this.refuse = refuse; }
	public List<String> getRefuse(){ return refuse; }
	

}
