package objectsTemplates;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EncapsulationPompier { 
	// classe utilisee pour envoyer un liste d'objets PompierConcret entre le client et le serveur
	// Permet de resoudre le probleme d'envoi de liste via le service Rest
	
	public List<PompierConcret> capsule; 

	public EncapsulationPompier(){} //constructeur vide necessaire car classe de type JavaBeans
	
	public EncapsulationPompier(List<PompierConcret> aEncapsulerStage){ this.capsule = aEncapsulerStage; } //constructeur avec argument

}
