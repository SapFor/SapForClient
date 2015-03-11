package restApplication;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import objectsTemplates.*;

/*
* Created with Eclipse.
* Author : Caroline Chabert
* Class to call server and supply methods for controllers of IHM
*/

public class ClientApp {

//////////////////////Attributs//////////////////////
		public static PompierConcret moi;
		private static int idSession;
		private static List<StageConcret> listSessionForm;
		private static List<StageConcret> listSessionDir;
		private static List<UVConcret> listUV;
		private static int nbCandidats = 0;

		static enum GuideList { CANDIDAT, ATTENTE, ACCEPTE, REFUSE }
		// Working on the assumption that your int value is 
		// the ordinal value of the items in your enum (method getListCandidatDirecteur)

		
		private static ClientConfig config;
		private static Client client;
		private static WebResource service;
		//private List<?> abstractList;
		
//////////////////////Methods//////////////////////
		
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
		
		// Get name fireman
		public static String getNomPomp(){ return moi.getPrenom() + " " + moi.getNom() + " - Agent n°" + moi.getId(); }
		

		// Get boolean if the fireman is director
		public static boolean isDirector(){
			System.out.println(moi.getDirecteur());
			return (moi.getDirecteur()=="oui");
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
		
//////////////////////Director Methods//////////////////////	
		

		// Test if the stage is closed
		public static boolean testDate(String nomStage){
			StageConcret test;
			int i = 0;
			while( i<listSessionDir.size() && !nomStage.equals(listSessionDir.get(i).getNomStage()) ){ i++;}
			test = listSessionDir.get(i);
			
			if( test.getFinCandidature().after(Calendar.getInstance().getTime()) ){ return true; }
			else{ return false; }
		}
		
	
		// Get list of the director stages : to put into the director tab
		public static List<String> getListSessionDirecteur(){
			//WebResource service = connect();
			// Get list of stages from the server
			EncapsulationStage encapStage = service.path("directeur/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationStage>(){});
			listSessionDir=encapStage.capsule;
			
			List<String> listSess = new ArrayList<String>(); // create list of stages for pushing on the tab
			Calendar dateStage;
			String nomUV;
			String date;
			String nomLieu;
			String ligneSess;
	    	
			Iterator<StageConcret> ite = listSessionDir.iterator();
	    	while(ite.hasNext()){  // loop to get name/date/place of each stage and put into the created list
	    		StageConcret newLigne = ite.next();
				
				nomUV = newLigne.getUV();
				dateStage = newLigne.getDate();
				date = dateStage.get(Calendar.DAY_OF_MONTH) + "/" + (dateStage.get(Calendar.MONTH)+1) + "/" + dateStage.get(Calendar.YEAR);
				nomLieu = newLigne.getLieu();
	    		ligneSess = nomUV + "\t" + date + "\t" + nomLieu;
	    		listSess.add(ligneSess);
	    	}
	    	return listSess;
	    }
		
		
	  	// Get list of director candidates for a specific stage : to put into the director tab
		public static List<String> getListCandidatDirecteur(String ClickedItemSession, int listLoading){

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
			// when the correct stage is found in the list -> get candidates list (switch case)
			int i = 0;
	        while( i<listSessionDir.size() && !ClickedItemSession.equals(listSessionDir.get(i).getNomStage()) ){ i++; }

	        List<String> listId = null;
	        
	        GuideList whichList = GuideList.values()[listLoading];
			switch (whichList){  // choice of the candidates list to load
				case CANDIDAT : listId = listSessionDir.get(i).getCandidats(); break;
				case ATTENTE : listId = listSessionDir.get(i).getAttente(); break;
				case ACCEPTE : listId = listSessionDir.get(i).getAccepte(); break;
				case REFUSE : listId = listSessionDir.get(i).getRefuse(); break;
				default: System.out.println("Aucune liste trouvee");
			}
			
		 	List<String> listPompiers = new ArrayList<String>();  // create list of firemen for pushing on the tab
		 	
		 	if (listId!=null){
		    	Iterator<String> ite = listId.iterator();
		    	while(ite.hasNext()){  // loop to get last name/first name of each fireman and put into the created list
		    		String newLigne = ite.next();
		    		String path = "pompier/" + newLigne;
		    		
		    		PompierConcret pomp = service.path(path).accept(MediaType.APPLICATION_JSON).get(new GenericType<PompierConcret>(){}); 
	
		    		String namePomp = pomp.getNom() + "\t" + pomp.getPrenom() ;
		    		listPompiers.add(namePomp);
		    	}
	    	}
	    	return listPompiers;
	    }
		
		
		
		// Get the objet ListCandidats hosting all the list (accepted, refused, pending, no handled candidates) : to put into the director tab
		public static ListCandidats getListCandidatDirecteurGlobal(String ClickedItemSession){
			
			ListCandidats candidates = new ListCandidats();  // create object of the class ListCandidats for pushing on the tab
			List<String> currentListPomp;
			
			for (int j=0; j<4; j++){
				currentListPomp = getListCandidatDirecteur(ClickedItemSession, j);
				
				switch (j){  // choice of the candidates list to set in the ListCandidats object
					case 0 : candidates.setCandidat(currentListPomp); break;
					case 1 : candidates.setAttente(currentListPomp); break;
					case 2 : candidates.setAccepte(currentListPomp); break;
					case 3 : candidates.setRefuse(currentListPomp); break;
					default: System.out.println("Aucune liste chargee");
				}
	        }
	    	return candidates;
		}
		
		
		// Get the number of candidates for a stage : to put into the director tab 
		public static int getNbCandidats(){
			return nbCandidats;
		}
		
		
		// Push a updated list of candidates for a specific stage to the server : "Valider" button in the director tab
		public static void enregBoutonDirecteur(String session, ListCandidats updatedLists){
		//public static void enregBoutonDirecteur(String session, List<String> candidat, List<String> accepte, List<String> attente, List<String> refuse){
				
			// Comparison between the string stage and the correspondent element in the list of stages (of the server)
			int i = 0;
			while( i<listSessionDir.size() && !session.equals(listSessionDir.get(i).getNomStage()) ){ i++; }
			        
			StageConcret updatedSession = listSessionDir.get(i);
			List<String> candidat = updatedLists.getCandidat();
			List<String> accepte = updatedLists.getAttente();
			List<String> attente = updatedLists.getAccepte();
			List<String> refuse = updatedLists.getRefuse();
			updatedSession.setCandidats(candidat);
			updatedSession.setAccepte(accepte);
			updatedSession.setAttente(attente);
			updatedSession.setRefuse(refuse);
					
			// Add a new stage using the PUT HTTP method. managed by the Jersey framework
			service.path("directeur/selection").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(new GenericType<String>(){}, updatedSession);
		}
		
				
		
		// Close the candidatures of a stage : associated to the "Cloturer" button in the director tab
		public static String cloturerCandidature(String nomStage,int jour, int mois, int annee){
			String reponse;
			String date = jour + "/" + mois + "/" + annee;
			reponse = service.path("directeur/" + nomStage + "/" + date).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
			if(reponse == "OK"){
				return "date changée";
			}
			else { return "Problème lors du changement de date";}
		}
				
				
//////////////////////Formation Methods//////////////////////
		
		// Get boolean if the fireman is already candidate at this stage
		public static boolean isCandidate(String ClickedItemSession){

			// Comparison between the string ClickedItemSession and the correspondent element in the list of stage (of the server)
	        int i = 0;
	        while( i<moi.getEnCours().size() && !ClickedItemSession.equals(moi.getEnCours().get(i)) ){
	        	System.out.println(moi.getEnCours().get(i));i++; }
	        
	        // the correct stage is found in the list -> get detailled infos
	        return (i<moi.getEnCours().size());
			
		}
		
		
		
		
		// Get list of the formation UVs, apprenant radioButton : to put into the formation tab
		public static List<String> getListUVApprenant(){
			// Get list of stages from the server
			EncapsulationUV encapUV = service.path("UVcandidat/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationUV>(){});
			listUV=encapUV.capsule;
			
			List<String> listUVDispo = new ArrayList<String>(); // create list of UV for pushing on the tab
			String nomUV;
					
			Iterator<UVConcret> ite = listUV.iterator();
			while(ite.hasNext()){  // loop to get name of each UV and put into the created list
				UVConcret newLigne = ite.next();
				nomUV = newLigne.getNom();
				listUVDispo.add(nomUV);
						
			}
			return listUVDispo;
		}
		
		
		
		// Get list of the formation UVs, formateur radioButton : to put into the formation tab
		public static List<String> getListUVFormateur(){
			// Get list of stages from the server
			EncapsulationUV encapUV = service.path("UVformateur/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationUV>(){});
			listUV=encapUV.capsule;
						
			List<String> listUVDispo = new ArrayList<String>(); // create list of UV for pushing on the tab
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
			// Get list of stages from the server
			EncapsulationStage encapSession = service.path("UV/" + clickedItemUV).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationStage>(){});
			listSessionForm=encapSession.capsule;
			
			List<String> listSess = new ArrayList<String>(); // create list of stages for pushing on the tab
			Calendar dateStage;
			String date;
			String nomLieu;
			String ligneSess;
			
			// loop of comparison between the string clickedUV and the correspondent element in the list of UV (of the server)

	        for(int j=0; j<listSessionForm.size(); j++){
	        	StageConcret newligne = listSessionForm.get(j);
	        	if(clickedItemUV.compareTo(newligne.getUV())==0){ // if true, get the place/date of the stage and put into the created list 

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
	        while( i<listSessionForm.size() && !ClickedItemSession.equals(listSessionForm.get(i).getNomStage()) ){ i++; }
	        
	        // the correct stage is found in the list -> get detailled infos
	        String infosDetails = listSessionForm.get(i).getInfos(); 
		 	return infosDetails;		
		}
		
		
		
		
		// Push a new candidating fireman for a specific stage to the server : "Candidater" button in the formation tab
		public static void candidateBoutonFormation(String currentStage){
			
			// modification on the client side
			if (moi.getEnCours()==null){moi.setEnCours(new ArrayList<String>());}
			List<String> current = moi.getEnCours();
			current.add(currentStage);   
			moi.setEnCours(current);   // adding the new candidated stage on the list of stages of our fireman (adding side client)
			
			// Add a new stage using the GET HTTP method. managed by the Jersey framework
			service.path("candidater/" + moi.getIdSession() + "/" + currentStage).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
		}
		
		
		
		// Push a fireman who delete his candidacy, for a specific stage to the server : "Retirer" button in the formation tab
		public static void retirerBoutonFormation(String currentStage){
					
			// modification on the client side
			if (moi.getEnCours()!=null){
				List<String> current = moi.getEnCours();
				current.remove(currentStage);   
				moi.setEnCours(current);   // deleting the old candidated stage on the list of stages of our fireman (deleting side client)
					
				// Add a new stage using the GET HTTP method. managed by the Jersey framework
				service.path("candidater/" + moi.getIdSession() + "/" + currentStage).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
			}
		}
		
		
		
		public static void main(String[] args) {
			/*
			// candidat 1
			login(2,"12345");
						
			List <String> ListeUV=getListUVApprenant();
			System.out.println("Liste des UVs accessible au candidat :");
			System.out.println(ListeUV);
			System.out.println();
			
			System.out.println("Description de l'UV "+ListeUV.get(0));
			System.out.println(getDescriptionUV(ListeUV.get(0))); //affiche la description de la premiere uv)
			System.out.println();
			
			List <String> ListeStage=getListSessionFormation(ListeUV.get(0));
			System.out.println("Liste des stages associés à "+ListeUV.get(0));
			System.out.println(ListeStage); // affiche la liste des stage associé à la premiere UV
			System.out.println();
			
			System.out.println("Info du premier stage "+ListeUV.get(0));
			System.out.println(getInfoDetailsFormation(listSessionForm.get(0).getNomStage()));
			System.out.println();
			
			candidateBoutonFormation(listSessionForm.get(0).getNomStage());
			
			System.out.println(deconnexion(moi.getIdSession()));
			
			
			// candidat 2
			login(3,"12345");
			
			ListeUV=getListUVFormateur();
			System.out.println("Liste des UVs accessible au candidat :");
			System.out.println(ListeUV);
			System.out.println();
			
			System.out.println("Description de l'UV "+ListeUV.get(0));
			System.out.println(getDescriptionUV(ListeUV.get(0))); //affiche la description de la premiere uv)
			System.out.println();
			
			ListeStage=getListSessionFormation(ListeUV.get(0));
			System.out.println("Liste des stages associés à "+ListeUV.get(0));
			System.out.println(ListeStage); // affiche la liste des stage associé à la premiere UV
			System.out.println();
			
			System.out.println("Info du premier stage "+ListeUV.get(0));
			System.out.println(getInfoDetailsFormation(listSessionForm.get(0).getNomStage()));
			System.out.println();
			
			candidateBoutonFormation(listSessionForm.get(0).getNomStage());
			
			System.out.println(deconnexion(moi.getIdSession()));
			
			// candidat 3
			login(5,"12345");
			
			ListeUV=getListUVFormateur();
			System.out.println("Liste des UVs accessible au candidat :");
			System.out.println(ListeUV);
			System.out.println();
			
			System.out.println("Description de l'UV "+ListeUV.get(0));
			System.out.println(getDescriptionUV(ListeUV.get(0))); //affiche la description de la premiere uv)
			System.out.println();
			
			ListeStage=getListSessionFormation(ListeUV.get(0));
			System.out.println("Liste des stages associés à "+ListeUV.get(0));
			System.out.println(ListeStage); // affiche la liste des stage associé à la premiere UV
			System.out.println();
			
			System.out.println("Info du premier stage "+ListeUV.get(0));
			System.out.println(getInfoDetailsFormation(listSessionForm.get(0).getNomStage()));
			System.out.println();
			
			candidateBoutonFormation(listSessionForm.get(0).getNomStage());
			
			System.out.println(deconnexion(moi.getIdSession()));
			
			// candidat 4
			login(6,"12345");
			ListeUV=getListUVFormateur();
			System.out.println("Liste des UVs accessible au candidat :");
			System.out.println(ListeUV);
			System.out.println();
			
			System.out.println("Description de l'UV "+ListeUV.get(0));
			System.out.println(getDescriptionUV(ListeUV.get(0))); //affiche la description de la premiere uv)
			System.out.println();
			
			ListeStage=getListSessionFormation(ListeUV.get(0));
			System.out.println("Liste des stages associés à "+ListeUV.get(0));
			System.out.println(ListeStage); // affiche la liste des stage associé à la premiere UV
			System.out.println();
			
			System.out.println("Info du premier stage "+ListeUV.get(0));
			System.out.println(getInfoDetailsFormation(listSessionForm.get(0).getNomStage()));
			System.out.println();
			
			candidateBoutonFormation(listSessionForm.get(0).getNomStage());
			
			System.out.println(deconnexion(moi.getIdSession()));
			
			
			// Directeur
			
			login(1,"12345");
			
			List<String> ListeAGerer=getListSessionDirecteur();
			System.out.println(ListeAGerer);
			
						
			System.out.println(getListCandidatDirecteur(listSessionDir.get(0).getNomStage(),0));
			System.out.println(getListCandidatDirecteur(listSessionDir.get(0).getNomStage(),1));
			System.out.println(getListCandidatDirecteur(listSessionDir.get(0).getNomStage(),2));
			System.out.println(getListCandidatDirecteur(listSessionDir.get(0).getNomStage(),3));
			
			ListCandidats listeCand=getListCandidatDirecteurGlobal(listSessionDir.get(0).getNomStage());
			System.out.println(getListCandidatDirecteurGlobal(listSessionDir.get(0).getNomStage()).getCandidat());
			
			//cloturerCandidature(ListeAGerer.get(0),10,3,2015);
			
			listeCand.getAccepte().add(listSessionDir.get(0).getCandidats().get(0));
			listeCand.getAccepte().add(listSessionDir.get(0).getCandidats().get(2));
			listeCand.getAttente().add(listSessionDir.get(0).getCandidats().get(1));
			listeCand.getRefuse().add(listSessionDir.get(0).getCandidats().get(3));
			listeCand.getCandidat().clear();
			
			enregBoutonDirecteur(listSessionDir.get(0).getNomStage(),listeCand);
			
			System.out.println(deconnexion(moi.getIdSession()));
			
		*/
		}
		
}


