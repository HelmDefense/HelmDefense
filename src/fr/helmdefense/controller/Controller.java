package fr.helmdefense.controller;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.living.special.Hero;
import fr.helmdefense.utils.YAMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {
	private LevelController level;
	private MenuController menu;
	
	@FXML
	BorderPane main;
	
	/* Header */
	// Controls buttons
	@FXML
	Button optionButton;
	@FXML
	Button pauseButton;
	@FXML
	Button speedButton;

	// Title
	@FXML
	Label levelNameLabel;

	// Money
	@FXML
	Label moneyLabel;
	@FXML
	ImageView moneyImage;

	/* Right Entity Infos */
	// Entity list
	@FXML
	VBox entityIDCardList;

	/* Footer */
	// Right text
	@FXML
	Label buyInfoLabel;
	@FXML
	Label creditsLabel;

	@FXML
	void optionButtonAction(ActionEvent event) {
		this.returnToMenu();
	}

	@FXML
	void pauseButtonAction(ActionEvent event) {
		
	}

	@FXML
	void speedButtonAction(ActionEvent event) {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		YAMLLoader.loadEntityData();
		this.menu = new MenuController(this);
	}
	
	void startLevel(String name, Hero hero) {
		this.level = new LevelController(this, name, hero);
		this.level.start();
	}
	
	void returnToMenu() {
		this.level.stop();
		this.level = null;
		this.menu.show();
		this.main.getLeft().setVisible(false);
		this.main.getRight().setVisible(false);
	}

	static ImageView getImgView(String... paths) {
		return new ImageView(imgPath(paths));
	}
	
	static Image getImg(String... paths) {
		return new Image(imgPath(paths));
	}
	
	static String imgPath(String... paths) {
		return Paths.get(
				Paths.get(System.getProperty("user.dir"), "assets").toString(),
				paths
		).toUri().toString();
	}
}