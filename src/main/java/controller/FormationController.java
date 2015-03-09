package controller;

import restApplication.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class FormationController implements Initializable{

	@FXML private Button btnFormation1;
	
	@FXML private Label lblFormation1;
	
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML private void btnFormation1Clicked(ActionEvent event){
		System.out.println("Button Formation Clicked");
	}
	
	
}
