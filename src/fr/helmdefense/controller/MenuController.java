package fr.helmdefense.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.HelmDefense;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.view.statbar.StatBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuController implements Initializable {
	private Controller main;
	private HelmDefense game;
	private String[] levels;
	private int selectedLevel;
	private int selectedHero;
	private int selectionMode;
	
	private static final int HERO_MODE = 1;
	private static final int LEVEL_MODE = 2;
	
	VBox root;
	
	@FXML
	ImageView previousArrow;
	@FXML
	ImageView nextArrow;
	
	@FXML
	ImageView currentImage;
	@FXML
	Label currentLabel;
	@FXML
	HBox heroInfo;
	@FXML
	HBox levelInfo;
	
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
	
	public MenuController(Controller main) {
		this.main = main;
		this.game = new HelmDefense();
		File saveDir = Paths.get(System.getProperty("user.dir"), "saves").toFile();
		this.levels = saveDir.list();
		this.selectedLevel = 0;
		this.selectedHero = 0;
		this.selectionMode = HERO_MODE;
		
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Menu.fxml"));
			loader.setController(this);
			this.root = loader.load();
			this.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.selectHero(0);
	}
	
	@FXML
	void previousArrowClicked(MouseEvent event) {
		switch (this.selectionMode) {
		case HERO_MODE:
			this.selectHero(-1);
			break;
		case LEVEL_MODE:
			this.selectLevel(-1);
			break;
		}
	}
	
	@FXML
	void previousArrowEntered(MouseEvent event) {
		this.previousArrow.setScaleX(1.15);
	}
	
	@FXML
	void previousArrowExited(MouseEvent event) {
		this.previousArrow.setScaleX(1);
	}
	
	@FXML
	void nextArrowClicked(MouseEvent event) {
		switch (this.selectionMode) {
		case HERO_MODE:
			this.selectHero(1);
			break;
		case LEVEL_MODE:
			this.selectLevel(1);
			break;
		}
	}
	
	@FXML
	void nextArrowEntered(MouseEvent event) {
		this.nextArrow.setScaleX(1.15);
	}
	
	@FXML
	void nextArrowExited(MouseEvent event) {
		this.nextArrow.setScaleX(1);
	}
	
	@FXML
	void playAction(ActionEvent event) {
		this.main.startLevel(this.selectedLevel(), this.selectedHero());
	}
	
	@FXML
	void mainAction(ActionEvent event) {
		switch (this.selectionMode) {
		case HERO_MODE:
			this.selectionMode = LEVEL_MODE;
			this.selectLevel(0);
			this.mainButton.setText("Choisir un héros");
			this.levelInfo.setVisible(true);
			break;
		case LEVEL_MODE:
			this.selectionMode = HERO_MODE;
			this.selectHero(0);
			this.mainButton.setText("Choisir un niveau");
			this.heroInfo.setVisible(true);
			break;
		}
	}
	
	@FXML
	void settingsAction(ActionEvent event) {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.heroInfo.managedProperty().bind(this.heroInfo.visibleProperty());
		this.heroInfo.visibleProperty().addListener((obs, o, n) -> this.levelInfo.setVisible(! n.booleanValue()));
		this.levelInfo.managedProperty().bind(this.levelInfo.visibleProperty());
		this.levelInfo.visibleProperty().addListener((obs, o, n) -> this.heroInfo.setVisible(! n.booleanValue()));
		this.levelInfo.setVisible(false);
		
		this.previousArrow.scaleYProperty().bind(this.previousArrow.scaleXProperty());
		this.nextArrow.scaleYProperty().bind(this.nextArrow.scaleXProperty());
	}
	
	private LivingEntityType selectedHero() {
		return LivingEntityType.HEROES[this.selectedHero];
	}
	
	private String selectedLevel() {
		return this.levels[this.selectedLevel];
	}
	
	private void selectHero(int n) {
		this.selectedHero += n;
		if (this.selectedHero < 0)
			this.selectedHero = LivingEntityType.HEROES.length - 1;
		else if (this.selectedHero >= LivingEntityType.HEROES.length)
			this.selectedHero = 0;
		
		this.currentLabel.setText(selectedHero().toString());
	}
	
	private void selectLevel(int n) {
		this.selectedLevel += n;
		if (this.selectedLevel < 0)
			this.selectedLevel = this.levels.length - 1;
		else if (this.selectedLevel >= this.levels.length)
			this.selectedLevel = 0;
		
		this.currentLabel.setText(selectedLevel());
	}
	
	void show() {
		this.main.main.setCenter(this.root);
		
		this.main.moneyLabel.textProperty().bind(this.game.starsProperty().asString());
		this.main.moneyImage.setImage(Controller.getImg("models", "star.png"));
	}
}