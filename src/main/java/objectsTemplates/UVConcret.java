package objectsTemplates;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * Created with Eclipse.
 * Author : Caroline Chabert
 * Class to get and set UVs data hosted by the server (cloning of data)
 * 
 */

@XmlRootElement
public class UVConcret {
	
	private String nom;
	private String descr;
	private List<String> stage;
	
	/*
	 * Name of each UV
	 */
	public void setNom(String nom) { this.nom = nom; }
	public String getNom() { return nom; }
	
	/*
	 * Description of each UV
	 */
	public void setDescr(String descr) { this.descr = descr; }
	public String getDescr() { return descr; }

	/*
	 * Sessions of each UV
	 */
	public void setSessions(List<String> stage) { this.stage = stage; }
	public List<String> getSessions() { return stage; }
	
	@Override
	public String toString() {
		return nom;
	}
	

}
