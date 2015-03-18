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
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml")); 
			//getClass().getResource("") --> fourni l'adresse racine de projet- ajout entre "" suite de l'adresse pour acces au fichier desire
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

		int i = ClientApp.getIdSession(); // Doit gerer la fermeture X
		ClientApp.deconnexion(i);
	}
}
