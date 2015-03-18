package application;
	
import restApplication.ClientApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			System.out.println(getClass().getResource("/view/Login.fxml"));
			System.out.println(getClass().getResource("/view/DirectorLeft.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		/*
		ClientApp.login(5,"12345");
		List <String> ListeUV=ClientApp.getListUVApprenant();
		System.out.println("Liste des UVs accessible au candidat :");
		System.out.println(ListeUV);
		System.out.println();
		
		ListeUV=ClientApp.getListUVFormateur();
		System.out.println("Liste des UVs accessible au formateur :");
		System.out.println(ListeUV);
		System.out.println();
		
		List <String> ListeStage=ClientApp.getListSessionFormation(ListeUV.get(0));
		System.out.println("Liste des stages associ閟 � "+ListeUV.get(0));
		System.out.println(ListeStage); // affiche la liste des stage associ� � la premiere UV
		System.out.println();
		
		*/
		int i = ClientApp.getIdSession();
		ClientApp.deconnexion(i);
	}
}
