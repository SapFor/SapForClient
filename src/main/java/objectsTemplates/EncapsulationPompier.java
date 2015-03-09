package objectsTemplates;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EncapsulationPompier {
	
	public List<PompierConcret> capsule; 

	public EncapsulationPompier(){}
	
	public EncapsulationPompier(List<PompierConcret> aEncapsulerStage){ this.capsule = aEncapsulerStage; }

}
