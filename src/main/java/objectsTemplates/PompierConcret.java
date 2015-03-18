package objectsTemplates;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/*
* Created with Eclipse.
* Author : Caroline Chabert
* Class to get and set firemen data hosted by the server (cloning of data)
*
*/

@XmlRootElement
public class PompierConcret { //Classe associee au donnee cote serveur contient que des getter et setter
	
	private int idSession;
	private int id;
	private String mdp;
	private String directeur;
	private String nom;
	private String prenom;
	private List<String> UV;
	private List<String> enCours;
	private List<String> accepte;
	private List<String> attente;
	private List<String> refuse;
	private List<String> gestion;
	
	/*
	 * Id of the session obtained through login
	 */
	public void setIdSession(int idSession){ this.idSession = idSession; }
	public int getIdSession(){ return idSession; }
	
	/*
	 * Id of the fireman
	 */
	public void setId(int id) { this.id = id; }
	public int getId(){ return id; }
	
	/*
	 * Mdp of the fireman
	 */
	public void setMdp(String mdp) { this.mdp = mdp; }
	public String getMdp(){ return mdp; }
	
	/*
	 * Is this fireman a director ?
	 */
	public void setDirecteur(String directeur) { this.directeur = directeur; }
	public String getDirecteur(){ return directeur; }
	
	/*
	 * Last name of the fireman
	 */
	public void setNom(String nom) { this.nom = nom; }
	public String getNom(){ return nom; }
	
	/*
	 * First name of the fireman
	 */
	public void setPrenom(String prenom) { this.prenom = prenom; }
	public String getPrenom(){ return prenom; }
	
	/*
	 * UV suggested to the fireman
	 */
	public void setUV(List<String> UV) { this.UV = UV; }
	public List<String> getUV(){ return UV; }
	
	/*
	 * Current UV session
	 */
	public void setEnCours(List<String> enCours) { this.enCours = enCours; }
	public List<String> getEnCours(){ return enCours; }
	
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
	
	/*
	 * Management of the UV session by the director
	 */
	public void setGestion(List<String> gestion) { this.gestion = gestion; }
	public List<String> getGestion(){ return gestion; }
	
	
	
}