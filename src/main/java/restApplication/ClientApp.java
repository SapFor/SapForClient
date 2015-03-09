package restApplication;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import objectsTemplates.*;

/*
* Created with Eclipse.
* Author : Caroline Chabert
* Class to call server and supply methods for controllers of IHM
*/

public class ClientApp {
	
		public static PompierConcret moi;
		private static int idSession;
		private static List<StageConcret> listSession;
		private static List<UVConcret> listUV;
		private static int nbCandidats = 0;
		
		private static ClientConfig config;
		private static Client client;
		private static WebResource service;
		//private List<?> abstractList;
		
		// Get server URI
		private static URI getBaseURI() {
			URI uri = UriBuilder.fromUri("http://localhost:8080/SAPFORServer/webresources/serveur/").build();
			return uri;
		}
		
		/*
		// First connection with access to the service object and initialize the fireman session
		public static void firstconnect(){
			config = new DefaultClientConfig();
			client = Client.create(config);
			service = client.resource(getBaseURI());
			moi = service.path("1/12345").accept(MediaType.APPLICATION_JSON).get(new GenericType<PompierConcret>(){}); 
		}
		*/
		
		// Get idSession by login and password
		public static String login(int idPompier,String mdp){
			config = new DefaultClientConfig();
			client = Client.create(config);
			service = client.resource(getBaseURI());
			String defPath = idPompier + "/" + mdp;
			PompierConcret check = service.path(defPath).accept(MediaType.APPLICATION_JSON).get(new GenericType<PompierConcret>(){});
			if (check.getIdSession()==999){ return "erreur"; }
			else {
				moi=check;
				idSession = check.getIdSession(); 
				return "ok";
			}
		}
		
		// Get IdSession
		public static int getIdSession(){ return idSession; }
		
		// Test if the stage is closed
		public static boolean testDate(String nomStage){
			StageConcret test;
			int i = 0;
			while( i<listSession.size() && !nomStage.equals(listSession.get(i).getNomStage()) ){ i++;}
			test = listSession.get(i);
			
			if( test.getFinCandidature().after(Calendar.getInstance().getTime()) ){ return true; }
			else{ return false; }
		}
		
		// Deconnect the session
		public static String deconnexion(int idSession){
			String reponse;
			String nSession = "" + idSession;
			reponse = service.path(nSession).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
			if(reponse.equals("OK")){
				return "Vous etes maintenant deconnecte!";
			}
			else { return "erreur de deconnexion!"; }
		}
		
		// Close the candidatures of a stage : associated to the "Candidater" button in the formation tab
		public static String cloturerCandidature(String nomStage,int jour, int mois, int annee){
			String reponse;
			String date = jour + "/" + mois + "/" + annee;
			reponse = service.path("directeur/" + nomStage + "/" + date).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
			if(reponse == "OK"){
				return "date changée";
			}
			else { return "Problème lors du changement de date";}
		}
		
		
		
	
		// Get list of the director stages : to put into the director tab
		public static List<String> getListSessionDirecteur(){
			//WebResource service = connect();
			// Get list of stages from the server
			EncapsulationStage encapStage = service.path("directeur/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationStage>(){});
			listSession=encapStage.capsule;
			
			List<String> listSess = new ArrayList(); // create list of stages for pushing on the tab
			Calendar dateStage;
			String nomUV;
			String date;
			String nomLieu;
			String ligneSess;
	    	
			Iterator<StageConcret> ite = listSession.iterator();
	    	while(ite.hasNext()){  // loop to get name/date/place of each stage and put into the created list
	    		StageConcret newLigne = ite.next();
				
				nomUV = newLigne.getNomStage();
				dateStage = newLigne.getDate();
				date = dateStage.get(Calendar.DAY_OF_MONTH) + "/" + dateStage.get(Calendar.MONTH)+1 + "/" + dateStage.get(Calendar.YEAR);
				nomLieu = newLigne.getLieu();
	    		ligneSess = nomUV + "\t" + date + "\t" + nomLieu;
	    		listSess.add(ligneSess);
	    	}
	    	return listSess;
	    }
		
		
		
		
	  	// Get list of director candidates for a specific stage : to put into the director tab
		public static List<String> getListCandidatDirecteur(String ClickedItemSession, String listLoading){

			/*
			// Get the three words of the item "Nom  Date  Lieu" and redo a unique completed string
			String nomUVItem = "";
			String mot = null;
			StringTokenizer tok = new StringTokenizer(ClickedItemSession);
	        for (int i=1; i<=3; i++) {
	        	mot = tok.nextToken();
	        	nomUVItem = nomUVItem + mot;
	        }
	        */
	    	
	        // Comparison between the string stage and the correspondent element in the list of stages (of the server)
	        int i = 0;
	        while( i<listSession.size() && !ClickedItemSession.equals(listSession.get(i).getNomStage()) ){ i++; }
	        
	        // the correct stage is found in the list -> get candidates list
	        List<String> listId;
			switch (listLoading){  // choice of the candidates list to load
			
				case "candidat": listId = listSession.get(i).getCandidats();
								 nbCandidats = listId.size();  
								 break;
			
				case "attente": listId = listSession.get(i).getAttente(); break;
				
				case "accepte": listId = listSession.get(i).getAccepte(); break;
				
				case "refuse": listId = listSession.get(i).getRefuse(); break;

				default: System.out.println("Aucune liste trouvee");
			}
			
		 	List<String> listPompiers = new ArrayList();  // create list of firemans for pushing on the tab
		 	
	    	Iterator<String> ite = listId.iterator();
	    	while(ite.hasNext()){  // loop to get last name/first name of each fireman and put into the created list
	    		String newLigne = ite.next();
	    		String path = "pompier/" + newLigne;
	    		
	    		PompierConcret pomp = service.path(path).accept(MediaType.APPLICATION_JSON).get(new GenericType<PompierConcret>(){}); 

	    		String namePomp = pomp.getNom() + "\t" + pomp.getPrenom() ;
	    		listPompiers.add(namePomp);
	    	}
	    	return listPompiers;
	    }
		
		
		// Get the number of candidates for a stage : to put into the director tab 
		public static int getNbCandidats(){
			return nbCandidats;
		}
		
		
		
		
		// Get list of the formation UVs : to put into the formation tab
		public static List<String> getListUVFormation(){
			// Get list of stages from the server
			EncapsulationUV encapUV = service.path("candidat/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationUV>(){});
			listUV=encapUV.capsule;
			
			List<String> listUVDispo = new ArrayList(); // create list of UV for pushing on the tab
			String nomUV;
					
			Iterator<UVConcret> ite = listUV.iterator();
			while(ite.hasNext()){  // loop to get name of each UV and put into the created list
				UVConcret newLigne = ite.next();
				nomUV = newLigne.getNom();
				listUVDispo.add(nomUV);
						
			}
			return listUVDispo;
		}
				
		
		
		// Get description of the formation UVs : to put into the formation tab
		public static String getDescriptionUV(String clickedItemUV){
			
			// Comparison between the string clickedItemUV and the correspondent element in the list of UV (of the server)
	        int i = 0;
	        while( i<listUV.size() && !clickedItemUV.equals(listUV.get(i).getNom()) ){ i++; }
	        
	        // the correct UV is found in the list -> get description
	        String description = listUV.get(i).getDescr(); 
		 	return description;		
		}
		
		
		
		
		// Get list of the formation stages of the formation UVs : to put into the formation tab
		public static List<String> getListSessionFormation(String clickedItemUV){
			
			List<String> listSess = new ArrayList(); // create list of stages for pushing on the tab
			Calendar dateStage;
			String date;
			String nomLieu;
			String ligneSess;
			
			// loop of comparison between the string clickedUV and the correspondent element in the list of UV (of the server)
	        for(int i=0; i<listSession.size(); i++){
	        	StageConcret newligne = listSession.get(i);
	        	if(clickedItemUV == newligne.getNomStage()){ // if true, get the place/date of the stage and put into the created list 
	        		dateStage = newligne.getDate();
	        		date = dateStage.get(Calendar.DAY_OF_MONTH) + "/" + (dateStage.get(Calendar.MONTH)+1) + "/" + dateStage.get(Calendar.YEAR);
	        		nomLieu = newligne.getLieu();
	        		ligneSess = nomLieu + " le " + date;
				    listSess.add(ligneSess);
	        	}
	        }
			return listSess;
		}
		
		
		// Get detailled infos of the formation stage : to put into the formation tab
		public static String getInfoDetailsFormation(String ClickedItemSession){

			// Comparison between the string ClickedItemSession and the correspondent element in the list of stage (of the server)
	        int i = 0;
	        while( i<listSession.size() && !ClickedItemSession.equals(listSession.get(i).getNomStage()) ){ i++; }
	        
	        // the correct stage is found in the list -> get detailled infos
	        String infosDetails = listSession.get(i).getInfos(); 
		 	return infosDetails;		
		}
		
		

	 
		// Push a updated list of candidates for a specific stage to the server : "Enregistrer" button in the director tab
		public static void enregBoutonDirecteur(String session, List<String> candidat, List<String> accepte, List<String> attente, List<String> refuse){
			
			// Comparison between the string stage and the correspondent element in the list of stages (of the server)
	        int i = 0;
	        while( i<listSession.size() && !session.equals(listSession.get(i).getNomStage()) ){ i++; }
	        
			StageConcret updatedSession = listSession.get(i);
			updatedSession.setCandidats(candidat);
			updatedSession.setAccepte(accepte);
			updatedSession.setAttente(attente);
			updatedSession.setRefuse(refuse);
			
			// Add a new stage using the POST HTTP method. managed by the Jersey framework
			service.path("directeur/" + moi.getIdSession()).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(new GenericType<StageConcret>(){}, updatedSession);
		}
		
		
		
		// Push a new candidating fireman for a specific stage to the server : "Candidater" button in the formation tab
		public static void candidateBoutonFormation(String currentStage){
			
			moi.setEnCours(currentStage); // on renseigne une liste de stages ou une stage ?
			
			// Add a new fireman using the POST HTTP method. managed by the Jersey framework
			service.path("candidat/" + moi.getIdSession()).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(new GenericType<PompierConcret>(){}, moi);
		}
		
		
		
		public static void main(String[] args) {
			/*
			login(1,"12345");
			List<String> maSession = getListSessionDirecteur();
			//List<String> mesCandidats = getListCandidatDirecteur(maSession);
			
			List<Integer> listeId=new ArrayList<Integer>();
			System.out.println(login(1,"12345"));
			listeId.add(idSession);
			System.out.println(idSession);
			System.out.println(login(1,"12345"));
			System.out.println(idSession);
			System.out.println(login(2,"12345"));
			listeId.add(idSession);
			System.out.println(idSession);
			for(int i=0;i<listeId.size();i++){
			System.out.println(deconnexion(listeId.get(i)));
			}
			*/
		}
}


