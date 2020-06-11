package fr.helmdefense.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.living.special.Hero;
import fr.helmdefense.model.level.GameLoop;
import fr.helmdefense.utils.YAMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable {
	private LevelController level;
	private MenuController menu;
	private OptionsController options;
	
	Stage primaryStage;
	
	@FXML
	BorderPane main;
	
	/* Header */
	// Controls buttons
	@FXML
	HBox controlButtons;
	@FXML
	Button optionButton;
	@FXML
	Button pauseButton;
	@FXML
	VBox speedBox;
	@FXML
	Slider speedness;
	@FXML
	Button stepButton;

	// Title
	@FXML
	Label levelNameLabel;

	// Money
	@FXML
	HBox moneyBox;
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
	
	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	void optionButtonAction(ActionEvent event) {
		if (this.level != null) {
			if (this.level.inOptions)
				this.level.show();
			else {
				this.options.show();
				if (this.level.getLvl().getGameloop().isPlaying())
					this.togglePause();
			}
			
			this.level.inOptions = ! this.level.inOptions;
		}
	}

	@FXML
	void pauseButtonAction(ActionEvent event) {
		this.togglePause();
	}
	
	@FXML
	void stepButtonAction(ActionEvent event) {
		if (this.level != null)
			this.level.getLvl().getGameloop().step();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		YAMLLoader.loadEntityData();
		this.primaryStage.setTitle("Helm Defense");
		this.primaryStage.getIcons().add(getImg("models", "icon.png"));
		this.primaryStage.setMaximized(true);
		this.options = new OptionsController(this);
		this.menu = new MenuController(this);
	}
	
	void togglePause() {
		if (this.level != null) {
			GameLoop loop = this.level.getLvl().getGameloop();
			if (loop.isPlaying()) {
				loop.pause();
				this.pauseButton.setText("Play");
				this.stepButton.setDisable(false);
			}
			else {
				loop.resume();
				this.pauseButton.setText("Pause");
				this.stepButton.setDisable(true);
			}
		}
	}
	
	void startLevel(String name, Hero hero) {
		this.level = new LevelController(this, name, hero);
		this.level.start();
	}
	
	void stopLevel() {
		if (this.level != null) {
			this.level.stop();
			this.level = null;
		}
	}
	
	void restartLevel() {
		String name = this.level.getLevelName();
		Hero hero = this.level.getHero();
		this.stopLevel();
		this.startLevel(name, hero);
	}
	
	void returnToMenu() {
		this.stopLevel();
		this.menu.show();
		setNodesVisibility(false, this.main.getLeft(), this.main.getRight());
	}
	
	static void setNodesVisibility(boolean visibility, Node... nodes) {
		for (Node node : nodes)
			if (node != null)
				node.setVisible(visibility);
	}
	
	OptionsController getOptions() {
		return this.options;
	}
	
	static String pathToImgPath(String path) {
		return path.replace('.', File.separatorChar) + ".png";
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
	
	static String path(String... paths) {
		return Paths.get(System.getProperty("user.dir"), paths).toUri().toString();
	}
}