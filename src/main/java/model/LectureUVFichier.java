
package model;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
/**
 * class pour lire et �crire des fichiers
 *
 * @author Caroline Chabert
 */




public class LectureUVFichier {
    public static final int LECTURE  = 0;
    public static final int ECRITURE = 1;
    
    private int             mnMode;
    private PrintWriter     muSortie         = null;
    private BufferedReader  muEntree         = null;

    public LectureUVFichier(String asNom, int anMode) {
        mnMode = anMode;
        try {
            if(mnMode==LectureUVFichier.LECTURE) {
                FileReader luFileReader = new FileReader(asNom);
                muEntree = new BufferedReader(luFileReader); 
            }
            else if(mnMode==LectureUVFichier.ECRITURE) {
                FileWriter luFileWriter = new FileWriter(asNom);
                muSortie = new PrintWriter(luFileWriter);
            }
        }
        catch(IOException e) {
            System.out.println("IOException :" + e.getMessage());
        }
    }

    public String litMot(String asLigne, int anPosition) {
        // anPosition est censÃ© dÃ©marrer Ã  1
        String mot = null;
        StringTokenizer lst = new StringTokenizer(asLigne);
        for (int i=1;i<=anPosition;i++) {
            mot = lst.nextToken();
        }
        return mot;
    }

    public int nbMots(String asLigne) {
        StringTokenizer lst = new StringTokenizer(asLigne);
        return lst.countTokens();
    }
    
    public void ecrire(String asMessage) {
        muSortie.println(asMessage);
    }
    
    public String lireLigneSuivante() {
        String lsLigne = null;
        try {
            lsLigne = muEntree.readLine();
        }
        catch(IOException e) {
            System.out.println("IOException :" + e.getMessage());
        }
        return lsLigne;
    }
    
    public ArrayList<String> getListUV(){
    	ArrayList<String> listUV = new ArrayList<String>();
    	String ligne = lireLigneSuivante();
    	while(ligne!=null){
    		listUV.add(litMot(ligne,1));
    		ligne = lireLigneSuivante();
    	}
    	return listUV;
    }
    
    public String getDescription(){
    	String desc = "";
    	String ligne = lireLigneSuivante();
    	while(ligne!=null){
    		desc = desc + ligne + '\n';
    		ligne = lireLigneSuivante();
    	}
    	return desc;
    }
    
    public ArrayList getListSession(){
    	ArrayList listSession = new ArrayList();
    	String ligne = lireLigneSuivante();
    	while(ligne!=null){
    		listSession.add(ligne);
    		ligne = lireLigneSuivante();
    	}
    	return listSession;
    }
    
    public void Fermer() {
        try {
            if(mnMode==LectureUVFichier.LECTURE) {
                muEntree.close();
            }
            else if(mnMode==LectureUVFichier.ECRITURE) {
                muSortie.close();
            }
        }
        catch(IOException e) {
            System.out.println("IOException :" + e.getMessage());
        }
    }
    
}