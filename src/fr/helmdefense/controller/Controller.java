package fr.helmdefense.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {
	@FXML
	private VBox entityIDCardList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			for (int i = 0; i < 5; i++) {
				VBox card = FXMLLoader.load(this.getClass().getResource("../view/EntityIDCard.fxml"));
				this.entityIDCardList.getChildren().add(card);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}