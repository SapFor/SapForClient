package objectsTemplates;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class EncapsulationUV {
	// classe utilisee pour envoyer un liste d'objets UVConcret entre le client et le serveur
	// Permet de resoudre le probleme d'envoi de liste via le service Rest
	
	public List<UVConcret> capsule; 
	
	public EncapsulationUV(){} //Constructeur vide car classe de type JavaBean
	
	public EncapsulationUV(List<UVConcret> aEncapsulerUV){ this.capsule = aEncapsulerUV; }

}
