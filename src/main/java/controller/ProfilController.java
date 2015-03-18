package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import restApplication.ClientApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfilController { // ProfilController non utilise dans la version presentee

	@FXML
    private TextField nom;
	
	@FXML
    private TextField prenom;
	
	@FXML
    private Label profession;
	
	@FXML
    private ListView<String> UVList;
	
	@FXML
    private Button quit;
	
	
	public void initialize() {

		/*charge nom et prenom*/
		String nomr = ClientApp.getNomPomp();
		UVList.setDisable(false);
		nom.setText(ExtraireNom(0,nomr));
		prenom.setText(ExtraireNom(1,nomr));
		
		/*charge le titre de l'utilisateur*/
		if(ClientApp.isDirector()){
			profession.setText("Director");
			UVList.setDisable(true);
			
		}else if(ClientApp.getListUVApprenant().isEmpty()){
			profession.setText("Formateur");
			ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVFormateur());
			UVList.setItems(ListeUV);
		}else
		{
			profession.setText("Pompier");
			ObservableList<String> ListeUV = FXCollections.observableArrayList(ClientApp.getListUVApprenant());
			UVList.setItems(ListeUV);
		}
		
	}
	
	/*extraire le nom et le prenom*/
	/* index = 0 => nom; index = 1 => prenom */
	public String ExtraireNom(int index, String nomprenom){
		String nom = "";
		int i=0;
		while(nomprenom.charAt(i)!=' '||index>=0){
			
			if(index==0)
			{
				nom += nomprenom.charAt(i);
			}
			if(nomprenom.charAt(i)==' ')
			{
				index--;
			}
			i++;
		}
		
		return nom;
		
	}
	@FXML
	public void onClicQuit(Event event){
		
		Stage currentStage = (Stage) quit.getScene().getWindow();
		currentStage.close();
	}
					
	
}
