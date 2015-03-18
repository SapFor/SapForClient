package objectsTemplates;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class EncapsulationStage {
	// classe utilisee pour envoyer un liste d'objets StageConcret entre le client et le serveur
	// Permet de resoudre le probleme d'envoi de liste via le service Rest

	public List<StageConcret> capsule;

	public EncapsulationStage(){} //Constructeur vide car classe de type JavaBean

	public EncapsulationStage(List<StageConcret> aEncapsulerStage){ this.capsule = aEncapsulerStage; }
	
}