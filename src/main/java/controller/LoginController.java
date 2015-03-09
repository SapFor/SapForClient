package controller;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button validerLoginButton;

	@FXML
    void onClicValiderLogin(Event event) {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Main.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage = (Stage) root.getScene().getWindow();
			stage.show();
			
		} 
        catch (IOException e) { e.printStackTrace(); } 
    }

}
