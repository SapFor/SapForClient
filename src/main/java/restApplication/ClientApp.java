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
import java.util.HashMap;
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

//////////////////////Attributs//////////////////////
		public static PompierConcret moi;
		private static int idSession;
		private static List<StageConcret> listSessionForm;
		private static List<StageConcret> listSessionDir;
		private static List<UVConcret> listUV;
		private static int nbCandidats = 0;
		
		private static HashMap<String,StageConcret> tableDeCorrespondanceDir;
		private static HashMap<String,StageConcret> tableDeCorrespondanceForm;
		private static HashMap<String,Integer> tableDeCorrespondancePomp = new HashMap<String,Integer>();
		static enum GuideList { CANDIDAT, ATTENTE, ACCEPTE, REFUSE }
		// Working on the assumption that your int value is 
		// the ordinal value of the items in your enum (method getListCandidatDirecteur)

		
		private static ClientConfig config;
		private static Client client;
		private static WebResource service;
		
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
			else if(check.getIdSession()==1000){ return "deja connecte";}
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
			return (moi.getDirecteur().compareTo("oui")==0);
		}
		
		
		// Deconnect the session (idSession is the number of the session)
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
		

		// Test if the stage is closed (date of the end of candidature > current date)
		// return true if the stage is not closed
		public static boolean testDate(String nomStage){
			StageConcret test;
			
			String correspondance = tableDeCorrespondanceDir.get(nomStage).getNomStage();
			int i = 0;
			while( i<listSessionDir.size() && !correspondance.equals(listSessionDir.get(i).getNomStage()) ){ i++;}
			test = listSessionDir.get(i);

			return test.getFinCandidature().getTime().after(Calendar.getInstance().getTime());
		}
		
		
		// Test if the stage has already started
		// return true if the date of beginning of stage > current date
		public static boolean testDateDebutStage(String nomStage){
			StageConcret test;
			
			String correspondance = tableDeCorrespondanceDir.get(nomStage).getNomStage();
			int i = 0;
			while( i<listSessionDir.size() && !correspondance.equals(listSessionDir.get(i).getNomStage()) ){ i++;}
			test = listSessionDir.get(i);
					
			return test.getDate().after(Calendar.getInstance().getTime());
		}
		
		
		
	
		// Get list of the director stages : to put into the director tab
		public static List<String> getListSessionDirecteur(){
			//WebResource service = connect();
			// Get list of stages from the server
			EncapsulationStage encapStage = service.path("directeur/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationStage>(){});
			listSessionDir = encapStage.capsule;
			
			tableDeCorrespondanceDir = new HashMap<String,StageConcret>();
			List<String> listSess = new ArrayList<String>(); // create list of stages for pushing on the tab
			Calendar dateStage;
			String nomUV;
			String date;
			String nomLieu;
			String ligneSess;
	    	

			if (listSessionDir!=null){
			Iterator<StageConcret> ite = listSessionDir.iterator();
	    	while(ite.hasNext()){  // loop to get name/date/place of each stage and put into the created list
	    		StageConcret newLigne = ite.next();
				
				nomUV = newLigne.getUV();
				dateStage = newLigne.getDate();
				date = dateStage.get(Calendar.DAY_OF_MONTH) + "/" + (dateStage.get(Calendar.MONTH)+1) + "/" + dateStage.get(Calendar.YEAR);
				nomLieu = newLigne.getLieu();
	    		ligneSess = nomUV + "\t" + date + "\t" + nomLieu;
	    		listSess.add(ligneSess);
	    		tableDeCorrespondanceDir.put(ligneSess, newLigne);

	    		}
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
	    	String correspondance = tableDeCorrespondanceDir.get(ClickedItemSession).getNomStage();
	        // Comparison between the string stage and the correspondent element in the list of stages (of the server)
			// when the correct stage is found in the list -> get candidates list (switch case)
			int i = 0;
	        while( i<listSessionDir.size() && !correspondance.equals(listSessionDir.get(i).getNomStage()) ){ i++; }

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
	
		    		String namePomp = pomp.getNom() + "_" + pomp.getPrenom() ;
		    		listPompiers.add(namePomp);
		    		tableDeCorrespondancePomp.put(namePomp, pomp.getId());

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
		public static String validBoutonDirecteur(String clicItemSession, ListCandidats updatedLists){
		//public static void validBoutonDirecteur(String clicItemSession, List<String> candidat, List<String> accepte, List<String> attente, List<String> refuse){
			
			String correspondance = tableDeCorrespondanceDir.get(clicItemSession).getNomStage();
			int correspId;			
			// Comparison between the string stage and the correspondent element in the list of stages (of the server)
			int i = 0;
			while( i<listSessionDir.size() && !correspondance.equals(listSessionDir.get(i).getNomStage()) ){ i++; }
			      
			StageConcret updatedSession = listSessionDir.get(i);
			
			// recovery of updated lists from the client
			List<String> candidat=new ArrayList<String>();
			List<String> accepte = new ArrayList<String>();
			List<String> attente = new ArrayList<String>();
			List<String> refuse = new ArrayList<String>();
			
			for(int j=0; j<updatedLists.getCandidat().size();j++){
				correspId = tableDeCorrespondancePomp.get(updatedLists.getCandidat().get(j));
				candidat.add(""+correspId);	
				
			}
				
			for(int j=0; j<updatedLists.getAccepte().size();j++){
				correspId = tableDeCorrespondancePomp.get(updatedLists.getAccepte().get(j));
				accepte.add(""+correspId);	
			}
			
			for(int j=0; j<updatedLists.getAttente().size();j++){
				correspId = tableDeCorrespondancePomp.get(updatedLists.getAttente().get(j));
				attente.add(""+correspId);	
			}
			
			for(int j=0; j<updatedLists.getRefuse().size();j++){
				correspId = tableDeCorrespondancePomp.get(updatedLists.getRefuse().get(j));
				refuse.add(""+correspId);	
			}
			
			// put these updated lists in the StageConcret object, to push into the server
			updatedSession.setCandidats(candidat);
			updatedSession.setAccepte(accepte);
			updatedSession.setAttente(attente);
			updatedSession.setRefuse(refuse);
					
			// Add a new stage using the PUT HTTP method. managed by the Jersey framework
			String reponse = service.path("directeur/selection").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(new GenericType<String>(){}, updatedSession);
			return reponse;
			
		}
		
		// Push a updated list of candidates for a specific stage to the server : "Sauvegarde temporaire" button in the director tab
		public static String sauvTempBoutonDirecteur(String clicItemSession, ListCandidats updatedLists){
					
			String correspondance = tableDeCorrespondanceDir.get(clicItemSession).getNomStage(); // A VERIFIE AVEC CARO
			// Comparison between the string stage and the correspondent element in the list of stages (of the server)
			int i = 0;
			while( i<listSessionDir.size() && !correspondance.equals(listSessionDir.get(i).getNomStage()) ){ i++; }
					        
			StageConcret updatedSession = listSessionDir.get(i);
			
			// recovery of updated lists from the client
			List<String> candidat=new ArrayList<String>();
			List<String> accepte = new ArrayList<String>();
			List<String> attente = new ArrayList<String>();
			List<String> refuse = new ArrayList<String>();
			
			for(int j=0; j<updatedLists.getCandidat().size();j++){
				int correspId = tableDeCorrespondancePomp.get(updatedLists.getCandidat().get(j));
				candidat.add(""+correspId);	
			}
			
			for(int j=0; j<updatedLists.getAccepte().size();j++){
				int correspId = tableDeCorrespondancePomp.get(updatedLists.getAccepte().get(j));
				accepte.add(""+correspId);	
			}
			
			for(int j=0; j<updatedLists.getAttente().size();j++){
				int correspId = tableDeCorrespondancePomp.get(updatedLists.getAttente().get(j));
				attente.add(""+correspId);	
			}
			
			for(int j=0; j<updatedLists.getRefuse().size();j++){
				int correspId = tableDeCorrespondancePomp.get(updatedLists.getRefuse().get(j));
				refuse.add(""+correspId);	
			}
			
			// put these updated lists in the StageConcret object, to push into the server
			updatedSession.setCandidats(candidat);
			updatedSession.setAccepte(accepte);
			updatedSession.setAttente(attente);
			updatedSession.setRefuse(refuse);
							
			// Add a new stage using the PUT HTTP method. managed by the Jersey framework
			String reponse = service.path("directeur/sauvegarde").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(new GenericType<String>(){}, updatedSession);
			return reponse;
		}
		
				
		
		// Close the candidatures of a stage : associated to the "Cloturer" button in the director tab
		public static String cloturerCandidature(String nomSession,int jour, int mois, int annee){
			String nomStage = tableDeCorrespondanceDir.get(nomSession).getNomStage();
			String reponse;
			String date = jour + "." + mois + "." + annee;
			reponse = service.path("directeur/" + nomStage + "/" + date).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
			if(reponse == "OK"){
				return "date changée";
			}
			else { return "Problème lors du changement de date";}
		}
				
				
//////////////////////Formation Methods//////////////////////
		
		// Get boolean if the fireman is already candidate at this stage
		public static boolean isCandidate(String ClickedItemSession){

			// the fireman is candidate if he is present in one of the 4 lists (enCours, accepte, refuse, attente)
			
			String correspondance = tableDeCorrespondanceForm.get(ClickedItemSession).getNomStage();
			boolean founded = false; 
			
			// Comparison between the string ClickedItemSession 
			// and the possible correspondent element in the 4 lists of stages (of the server)
	       
			if(moi.getEnCours()!=null){
	        	int i = 0;
	        	while( i<moi.getEnCours().size() && !correspondance.equals(moi.getEnCours().get(i)) ){ i++; }
	        
	        	// the correct stage is found in the list -> get detailled infos
	        	if(i<moi.getEnCours().size()){ founded = true;}
	        }
	        
	        if(moi.getAccepte()!=null){
	        	int i = 0;
	        	while( i<moi.getAccepte().size() && !correspondance.equals(moi.getAccepte().get(i)) ){ i++; }
	        
	        	// the correct stage is found in the list -> get detailled infos
	        	if(i<moi.getAccepte().size()){ founded = true;}
	        }
	        
	        if(moi.getAttente()!=null){
	        	int i = 0;
	        	while( i<moi.getAttente().size() && !correspondance.equals(moi.getAttente().get(i)) ){ i++; }
	        
	        	// the correct stage is found in the list -> get detailled infos
	        	if(i<moi.getAttente().size()){ founded = true;}
	        }
	        
	        if(moi.getRefuse()!=null){
	        	int i = 0;
	        	while( i<moi.getRefuse().size() && !correspondance.equals(moi.getRefuse().get(i)) ){ i++; }
	        
	        	// the correct stage is found in the list -> get detailled infos
	        	if(i<moi.getRefuse().size()){ founded = true;}
	        }
	        
	        return founded;
			
		}
		
		
		
		
		// Get list of the formation UVs, apprenant radioButton : to put into the formation tab
		public static List<String> getListUVApprenant(){
			// Get list of stages from the server
			EncapsulationUV encapUV = service.path("UVcandidat/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationUV>(){});
			listUV = encapUV.capsule;
			
			List<String> listUVDispo = new ArrayList<String>(); // create list of UV for pushing on the tab
			String nomUV;
			if(listUV != null){		
				Iterator<UVConcret> ite = listUV.iterator();
				while(ite.hasNext()){  // loop to get name of each UV and put into the created list
					UVConcret newLigne = ite.next();
					nomUV = newLigne.getNom();
					listUVDispo.add(nomUV);
							
				}
			}
			return listUVDispo;
		}
		
		
		
		// Get list of the formation UVs, formateur radioButton : to put into the formation tab
		public static List<String> getListUVFormateur(){
			// Get list of stages from the server
			EncapsulationUV encapUV = service.path("UVformateur/" + moi.getIdSession()).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationUV>(){});
			listUV = encapUV.capsule;
						
			List<String> listUVDispo = new ArrayList<String>(); // create list of UV for pushing on the tab
			String nomUV;
			if(listUV != null){
				Iterator<UVConcret> ite = listUV.iterator();
				while(ite.hasNext()){  // loop to get name of each UV and put into the created list
					UVConcret newLigne = ite.next();
					nomUV = newLigne.getNom();
					listUVDispo.add(nomUV);
										
				}
			}
			return listUVDispo;
		}
				
		
		
		// Get description of the formation UVs : to put into the formation tab
		public static String getDescriptionUV(String clickedItemUV){
			
			String description="";
			// Comparison between the string clickedItemUV and the correspondent element in the list of UV (of the server)
	        int i = 0;
	        if(listUV!=null){
	        	while( i<listUV.size() && !clickedItemUV.equals(listUV.get(i).getNom()) ){ i++; }
	        
	        	// the correct UV is found in the list -> get description
	        	description = listUV.get(i).getDescr();
	        }
		 	return description;		
		}
		
		
		
		
		// Get list of the formation stages of the formation UVs : to put into the formation tab
		public static List<String> getListSessionFormation(String clickedItemUV){
			// Get list of stages from the server
			EncapsulationStage encapSession = service.path("UV/" + clickedItemUV).accept(MediaType.APPLICATION_JSON).get(new GenericType<EncapsulationStage>(){});
			listSessionForm = encapSession.capsule;
			
			tableDeCorrespondanceForm = new HashMap<String,StageConcret>();
			
			List<String> listSess = new ArrayList<String>(); // create list of stages for pushing on the tab
			Calendar dateStage;
			String date;
			String nomLieu;
			String ligneSess;
			
			// loop of comparison between the string clickedUV and the correspondent element in the list of UV (of the server)
			if (listSessionForm !=null){
	        for(int j=0; j<listSessionForm.size(); j++){
	        	StageConcret newligne = listSessionForm.get(j);
	        	if(clickedItemUV.compareTo(newligne.getUV())==0){ // if true, get the place/date of the stage and put into the created list 

	        		dateStage = newligne.getDate();
	        		date = dateStage.get(Calendar.DAY_OF_MONTH) + "/" + (dateStage.get(Calendar.MONTH)+1) + "/" + dateStage.get(Calendar.YEAR);
	        		nomLieu = newligne.getLieu();
	        		ligneSess = nomLieu + " le " + date;
				    listSess.add(ligneSess);
				    tableDeCorrespondanceForm.put(ligneSess, newligne);
				    
	        	}
	        }
			}
			return listSess;
		}
		
		
		// Get detailled infos of the formation stage : to put into the formation tab
		public static String getInfoDetailsFormation(String ClickedItemSession){
			
			String correspondance = tableDeCorrespondanceForm.get(ClickedItemSession).getNomStage();
			// Comparison between the string ClickedItemSession and the correspondent element in the list of stage (of the server)
			int i = 0;
	        while( i<listSessionForm.size() && !correspondance.equals(listSessionForm.get(i).getNomStage()) ){ i++; }
	        
	        // the correct stage is found in the list -> get detailled infos
	        String infosDetails = listSessionForm.get(i).getInfos(); 
		 	return infosDetails;		
		}
		
		

		// auxiliary method : Get the three words of the item "NomLieu le Date" and redo a unique completed string
		// exemple : FDF1broceliande le 15/06/2015 -> FDF1broceliande15juin15
		public static String getConcatNameStage(String nomStage){
			String newNomStage = "";
			String mot = null;
			StringTokenizer tok = new StringTokenizer(nomStage);

			mot = tok.nextToken();
			newNomStage = newNomStage + mot;
			mot = tok.nextToken();
			mot = tok.nextToken();
			newNomStage = newNomStage + mot;
						
			String[] elements =  newNomStage.split("/");
			String jour = elements[0];
			String moisInt = elements[1];
			String annee = elements[2];
			String anneeCut = annee.substring(2);
						
			int partMois = Integer.parseInt(moisInt)-1; 
			String mois;
			switch(partMois){
				case 0: mois="janv";break;
				case 1: mois="fev";break;
				case 2: mois="mars";break;
				case 3: mois="avr";break;
				case 4: mois="mai";break;
				case 5: mois="juin";break;
				case 6: mois="juil";break;
				case 7: mois="aout";break;
				case 8: mois="sept";break;
				case 9: mois="oct";break;
				case 10: mois="nov";break;
				case 11: mois="dec";break;
				default: mois="erreur";break;
			}
			newNomStage = jour + mois + anneeCut;
			return newNomStage;
						
		}
		
		
		// Push a new candidating fireman for a specific stage to the server : "Candidater" button in the formation tab
		public static void candidateBoutonFormation(String currentItemStage){
			
			//String currentStage = getConcatNameStage(currentItemStage);
			String currentStage = tableDeCorrespondanceForm.get(currentItemStage).getNomStage();
			
			// modification on the client side
			if (moi.getEnCours()==null){moi.setEnCours(new ArrayList<String>());}
			List<String> current = moi.getEnCours();
			current.add(currentStage);   
			moi.setEnCours(current);   // adding the new candidated stage on the list of stages of our fireman (adding side client)
			
			// Add a new stage using the GET HTTP method. managed by the Jersey framework
			service.path("candidater/" + moi.getIdSession() + "/" + currentStage).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
		}
		
		
		
		// Push a fireman who delete his candidacy, for a specific stage to the server : "Retirer" button in the formation tab
		public static void retirerBoutonFormation(String currentItemStage){
			
			//String currentStage = getConcatNameStage(currentItemStage);
			String currentStage = tableDeCorrespondanceForm.get(currentItemStage).getNomStage();
			
			// modification on the client side
			if (moi.getEnCours()!=null){
				List<String> current = moi.getEnCours();
				current.remove(currentStage);   
				moi.setEnCours(current);   // deleting the old candidated stage on the list of stages of our fireman (deleting side client)
					
				// Add a new stage using the GET HTTP method. managed by the Jersey framework
				service.path("desincription/" + moi.getIdSession() + "/" + currentStage).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(new GenericType<String>(){});
			}
		}
		
	
//////////////////////Candidate Methods//////////////////////
		
		// Get list of the candidate stages : to put into the candidate tab
		// statut = 0 for "no handled candidat", 1 for "attente", 2 for "accepte", 3 for "refuse"
		public static List<String> getListSessionCandidate(int statut){
				
			List<String> listId = new ArrayList<String>(); // create list of stages for pushing on the tab
			List<String> listParStatut = new ArrayList<String>();
				        
			GuideList whichList = GuideList.values()[statut];
			switch (whichList){  // choice of the candidates list to load
				case CANDIDAT : listId = moi.getEnCours(); break;
				case ATTENTE : listId = moi.getAttente(); break;
				case ACCEPTE : listId = moi.getAccepte(); break;
				case REFUSE : listId = moi.getRefuse(); break;
				default: System.out.println("Aucune liste trouvee");
			}
				
			if (listId!=null){
				Iterator<String> ite = listId.iterator();
				while(ite.hasNext()){  // loop to get name/date/place of each stage and put into the created list
				   String newLigne = ite.next();
				   listParStatut.add(newLigne);
				}
			}
			else{
				listParStatut.add("Il n'y a pas encore d'historique de sessions.");
				listParStatut.add("Veuillez candidater à une session pour afficher l'historique.");
			}
			return listParStatut;
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
			List<String> ListeTruc=getListSessionCandidate(3);
			System.out.println(ListeTruc);
			
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


