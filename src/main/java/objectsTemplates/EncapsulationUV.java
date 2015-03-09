package objectsTemplates;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class EncapsulationUV {
	
	public List<UVConcret> capsule; 
	
	public EncapsulationUV(){}
	
	public EncapsulationUV(List<UVConcret> aEncapsulerUV){ this.capsule = aEncapsulerUV; }

}
