package fr.helmdefense.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.living.special.Hero;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.utils.yaml.YAML;
import fr.helmdefense.view.statbar.StatBar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuController implements Initializable {
	private Controller main;
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
	@FXML
	Button quit;
	
	public MenuController(Controller main) {
		this.main = main;
		this.levels = Paths.get(System.getProperty("user.dir"), YAML.SAVES_FOLDER).toFile().list((file, name) -> ! name.equalsIgnoreCase("Troll Level"));
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
		this.main.getOptions().show();
	}
	
	@FXML
	void quitAction(ActionEvent event) {
		Platform.exit();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Center initialization
		this.heroInfo.managedProperty().bind(this.heroInfo.visibleProperty());
		this.heroInfo.visibleProperty().addListener((obs, o, n) -> this.levelInfo.setVisible(! n.booleanValue()));
		this.levelInfo.managedProperty().bind(this.levelInfo.visibleProperty());
		this.levelInfo.visibleProperty().addListener((obs, o, n) -> this.heroInfo.setVisible(! n.booleanValue()));
		this.levelInfo.setVisible(false);
		
		// Arrows initialization
		this.previousArrow.scaleYProperty().bind(this.previousArrow.scaleXProperty());
		this.previousArrow.setOnMouseEntered(event -> this.previousArrow.setScaleX(1.2));
		this.previousArrow.setOnMouseExited(event -> this.previousArrow.setScaleX(1));
		
		this.nextArrow.scaleYProperty().bind(this.nextArrow.scaleXProperty());
		this.nextArrow.setOnMouseEntered(event -> this.nextArrow.setScaleX(1.2));
		this.nextArrow.setOnMouseExited(event -> this.nextArrow.setScaleX(1));
		
		// Upgrades initialization
		this.upgradeHp.setOnMouseClicked(event -> {
			if (this.main.getGame().removeStar())
				this.selectedHero().upgradeHp();
		});
		this.upgradeHp.setOnMouseEntered(event -> this.upgradeHp.setOpacity(1));
		this.upgradeHp.setOnMouseExited(event -> this.upgradeHp.setOpacity(0.75));
		
		this.upgradeDmg.setOnMouseClicked(event -> {
			if (this.main.getGame().removeStar())
				this.selectedHero().upgradeDmg();
		});
		this.upgradeDmg.setOnMouseEntered(event -> this.upgradeDmg.setOpacity(1));
		this.upgradeDmg.setOnMouseExited(event -> this.upgradeDmg.setOpacity(0.75));
	}
	
	private Hero selectedHero() {
		return this.main.getGame().getHero(LivingEntityType.HEROES[this.selectedHero]);
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
		
		this.currentLabel.setText(this.selectedHero().getType().toString());
		this.currentImage.setImage(Controller.getImg("entities", Controller.pathToImgPath(this.selectedHero().data().getPath())));
		
		this.hpBar.valueProperty().bind(this.selectedHero().hpUpgradeProperty().add(1).multiply(this.selectedHero().stat(Attribute.HP)));
		this.hpBar.setMax(this.selectedHero().stat(Attribute.HP) * (1 + Hero.MAXIMUM_UPGRADE));
		this.dmgBar.valueProperty().bind(this.selectedHero().dmgUpgradeProperty().add(1).multiply(this.selectedHero().stat(Attribute.DMG)));
		this.dmgBar.setMax(this.selectedHero().stat(Attribute.DMG) * (1 + Hero.MAXIMUM_UPGRADE));
	}
	
	private void selectLevel(int n) {
		this.selectedLevel += n;
		if (this.selectedLevel < 0)
			this.selectedLevel = this.levels.length - 1;
		else if (this.selectedLevel >= this.levels.length)
			this.selectedLevel = 0;
		
		this.currentLabel.setText(this.selectedLevel());
		this.currentImage.setImage(new Image(Controller.path(YAML.SAVES_FOLDER, this.selectedLevel(), "icon.png")));
	}
	
	void show() {
		this.main.main.setCenter(this.root);
		
		Controller.setNodesVisibility(false, this.main.controlButtons, this.main.buyInfoLabel);
		this.main.primaryStage.titleProperty().unbind();
		this.main.primaryStage.setTitle("HelmDefense");
		this.main.levelNameLabel.textProperty().unbind();
		this.main.levelNameLabel.setText("Lancez un niveau pour jouer !");
		
		this.main.moneyBox.setVisible(true);
		this.main.moneyLabel.textProperty().bind(this.main.getGame().starsProperty().asString());
		this.main.moneyImage.setImage(Controller.getImg("models", "star.png"));
		
		this.main.creditsLabel.setOnMouseClicked(event -> this.main.startLevel("Troll Level", this.selectedHero()));
	}
}