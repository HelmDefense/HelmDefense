package fr.helmdefense.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class OptionsController implements Initializable {
	private Controller main;
	
	ScrollPane root;
	
	@FXML
	MenuButton difficultyButton;
	@FXML
	ToggleGroup difficulty;
	@FXML
	RadioMenuItem easyDifficulty;
	@FXML
	RadioMenuItem normalDifficulty;
	@FXML
	RadioMenuItem hardDifficulty;

	@FXML
	Slider speedness;
	
	@FXML
	MenuButton gamemodeButton;
	@FXML
	ToggleGroup gamemode;
	@FXML
	RadioMenuItem normalGamemode;
	@FXML
	RadioMenuItem demoGamemode;

	@FXML
	VBox controlsLabelBox;
	@FXML
	VBox controlsButtonsBox;

	@FXML
	Button restartLevelButton;
	
	public OptionsController(Controller main) {
		this.main = main;
		
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Options.fxml"));
			loader.setController(this);
			this.root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void restartLevelAction(ActionEvent event) {
		this.main.restartLevel();
	}

	@FXML
	void returnToMenuAction(ActionEvent event) {
		this.main.returnToMenu();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.speedness.valueProperty().bindBidirectional(this.main.speedness.valueProperty());
	}
	
	void show() {
		this.main.main.setCenter(this.root);
		Controller.setNodesVisibility(false, this.main.moneyBox, this.main.main.getLeft(), this.main.main.getRight(),
				this.main.buyInfoLabel, this.main.pauseButton, this.main.speedBox, this.main.stepButton);
	}
}