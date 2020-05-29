package fr.helmdefense.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.helmdefense.view.statbar.StatBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MenuController implements Initializable {
	@FXML
	ImageView previousArrow;
	@FXML
	ImageView nextArrow;
	
	@FXML
	ImageView currentImage;
	@FXML
	Label currentLabel;
	
	@FXML
	StatBar hpBar;
	@FXML
	StatBar dmgBar;
	
	@FXML
	StackPane upgradeHp;
	@FXML
	StackPane upgradeDmg;
	
	@FXML
	Button mainButton;
	@FXML
	Button settings;
	
	@FXML
	void mainAction(ActionEvent event) {
		System.out.println("Play!");
	}
	
	@FXML
	void settingsAction(ActionEvent event) {
		System.out.println("Options");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}