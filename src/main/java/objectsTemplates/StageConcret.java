package objectsTemplates;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



/*
 * Created with Eclipse.
 * Author : Caroline Chabert
 * Class to get and set sessions data hosted by the server (cloning of data)
 * 
 */

@XmlRootElement
public class StageConcret {

	private String uv;
	//private int directeur;
	private String nomStage;
	private Calendar date;
	private Calendar finCandidature;
	private String lieu;
	private String infos;
	private List<String> candidats;
	private List<String> accepte;
	private List<String> attente;
	private List<String> refuse;
	
	
<<<<<<< HEAD
	/*@Override
	public void setDirecteur(int idDirecteur) {
		// TODO Auto-generated method stub
		this.directeur=idDirecteur;
	}
=======
	/*
	 * ID director of the session
	 */
	public void setDirecteur(int directeur) { this.directeur = directeur; }
	public int getDirecteur() { return directeur; }
	
	/*
	 * UV of the session
	 */
	public void setNomStage(String uv) { this.uv = uv; }
	public String getNomStage() { return uv; }
	
	/*
	 * Date of the session
	 */
	public void setDate(Calendar date) { this.date = date; }
	public Calendar getDate() { return this.date; }
	
	/*
	 * Date of the end of session
	 */
	public Calendar getFinCandidature(){ return finCandidature; }
	public void setFinCandidature(Calendar date){ this.finCandidature = date; }
	
	/*
	 * Place of the session
	 */
	public void setLieu(String lieu) { this.lieu = lieu; }
	public String getLieu() { return lieu; }
	
	/*
	 * Informations of the session
	 */
	public void setInfos(String infos) { this.infos = infos; }
	public String getInfos() { return infos; }
	
	/*
	 * List of no handled candidates for the session
	 */
	public void setCandidats(List<String> candidats) { this.candidats = candidats; }
	public List<String> getCandidats() { return candidats; }
	
	/*
	 * List of accepted candidates
	 */
	public void setAccepte(List<String> accepte) { this.accepte = accepte; }
	public List<String> getAccepte() { return accepte; }
	
	/*
	 * List of pending candidates
	 */
	public void setAttente(List<String> attente) { this.attente = attente; }
	public List<String> getAttente() { return attente; }
	
	/*
	 * List of refused candidates
	 */
	public void setRefuse(List<String> refuse) { this.refuse = refuse; }
	public List<String> getRefuse() { return refuse; }

>>>>>>> origin/master

	@Override
	public int getDirecteur() {
		// TODO Auto-generated method stub
		return directeur;
	}*/
	
	
	public void setNomStage(String nom){
		this.nomStage=nom;
		
	}
	
	
	public String getNomStage(){
		return nomStage;
		
	}
	
	
	public void setUV(String UV) {
		// TODO Auto-generated method stub
		this.uv=UV;
	}

	
	public String getUV() {
		// TODO Auto-generated method stub
		return uv;
	}
	
	
	public void setDate(Calendar date) {
		
		this.date=date;
	}

	
	
	public Calendar getDate() {
		// TODO Auto-generated method stub
				
		return this.date;
	}
		
	
	public Calendar getFinCandidature(){
		
		return finCandidature;
		
	}
	
	
	public void setFinCandidature(Calendar date){
		
		this.finCandidature=date;
	}
	
	
	public void setLieu(String lieu) {
		
		this.lieu=lieu;
		
	}

	
	public String getLieu() {
		// TODO Auto-generated method stub
		return lieu;
	}

	
	public void setInfos(String infos) {
		
		
		this.infos=infos;
		
	}

	
	public String getInfos() {
		// TODO Auto-generated method stub
		return infos;
	}

	
	public void setCandidats(List<String> candidats) {
		// TODO Auto-generated method stub
		this.candidats=candidats;
	}

	
	public List<String> getCandidats() {
		// TODO Auto-generated method stub
		return candidats;
	}

	
	public void setAccepte(List<String> accepte) {
		// TODO Auto-generated method stub
		this.accepte=accepte;
	}

	
	public List<String> getAccepte() {
		// TODO Auto-generated method stub
		return accepte;
	}

	
	public void setAttente(List<String> attente) {
		// TODO Auto-generated method stub
		this.attente=attente;
	}

	
	public List<String> getAttente() {
		// TODO Auto-generated method stub
		return attente;
	}

	
	public void setRefuse(List<String> refuse) {
		// TODO Auto-generated method stub
		this.refuse=refuse;
	}

	
	public List<String> getRefuse() {
		// TODO Auto-generated method stub
		return refuse;
	}
}
	
	
