package fr.helmdefense.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.HelmDefense;
import fr.helmdefense.model.entities.living.special.Hero;
import fr.helmdefense.utils.yaml.YAMLLoader;
import fr.helmdefense.utils.yaml.YAMLWriter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable {
	private LevelController level;
	private MenuController menu;
	private OptionsController options;
	private HelmDefense game;
	private Gamemode gamemode;
	private StringProperty windowNameProperty;
	private StringProperty levelNameProperty;
	private StringProperty waveNameProperty;
	private IntegerProperty levelLivesProperty;
	
	Stage primaryStage;
	
	@FXML
	BorderPane main;
	
	/* Header */
	// Controls buttons
	@FXML
	HBox controlButtons;
	@FXML
	HBox levelControlButtons;
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
		YAMLLoader.loadEntityData();
		
		this.primaryStage = primaryStage;
		this.windowNameProperty = new SimpleStringProperty("HelmDefense");
		this.levelNameProperty = new SimpleStringProperty();
		this.waveNameProperty = new SimpleStringProperty();
		this.levelLivesProperty = new SimpleIntegerProperty();
		this.game = new HelmDefense();
		this.primaryStage.setTitle("Helm Defense");
		this.primaryStage.getIcons().add(getImg("models", "icon.png"));
		this.primaryStage.setMaximized(true);
	}

	@FXML
	void optionButtonAction(ActionEvent event) {
		if (this.hasLevelLoaded()) {
			if (this.level.inOptions) {
				this.level.show();
				this.optionButton.setText("Options");
			}
			else {
				this.options.show();
				this.optionButton.setText("Retour");
				this.pause();
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
		if (this.hasLevelLoaded())
			this.level.getLvl().getGameloop().step();
	}
	
	public void closeHandler() {
		YAMLWriter.saveOptions(this.options.getSelectedDifficulty(), this.speedness.getValue(), this.getGamemode());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.options = new OptionsController(this);
		this.menu = new MenuController(this);
		
		this.options.setDifficulty(YAMLLoader.loadDifficulty());
		this.speedness.setValue(YAMLLoader.loadSpeedness());
		this.options.setGamemode(YAMLLoader.loadGamemode());
		YAMLLoader.applyControls();
		
		this.main.addEventFilter(KeyEvent.ANY, event -> {
			if (this.hasLevelLoaded() && ! this.level.inOptions)
				this.level.handleKeyboardInput(event);
			else
				this.options.handleKeyboardInput(event);
			
			event.consume();
		});
	}
	
	void togglePause() {
		if (this.hasLevelLoaded()) {
			if (this.level.getLvl().getGameloop().isPlaying()) {
				this.pause();
			}
			else {
				this.resume();
			}
		}
	}
	
	void pause() {
		if (this.hasLevelLoaded()) {
			this.level.getLvl().getGameloop().pause();
			this.pauseButton.setText("Play");
			this.stepButton.setDisable(false);
		}
	}
	
	void resume() {
		if (this.hasLevelLoaded()) {
			this.level.getLvl().getGameloop().resume();
			this.pauseButton.setText("Pause");
			this.stepButton.setDisable(true);
		}
	}
	
	void startLevel(String name, Hero hero) {
		this.level = new LevelController(this, name, hero);
		this.level.start();
		this.level.getLvl().setDifficulty(this.options.getSelectedDifficulty());
		this.resume();
	}
	
	void stopLevel() {
		if (this.hasLevelLoaded()) {
			this.level.stop();
			this.level = null;
		}
	}
	
	void restartLevel() {
		if (this.hasLevelLoaded()) {
			String name = this.level.getLevelName();
			Hero hero = this.level.getHero();
			this.stopLevel();
			this.startLevel(name, hero);
			this.resume();
		}
	}
	
	void returnToMenu() {
		this.stopLevel();
		this.menu.show();
		setNodesVisibility(false, this.main.getLeft(), this.main.getRight());
	}
	
	boolean hasLevelLoaded() {
		return this.level != null;
	}
	
	static void setNodesVisibility(boolean visibility, Node... nodes) {
		for (Node node : nodes)
			if (node != null)
				node.setVisible(visibility);
	}
	
	OptionsController getOptions() {
		return this.options;
	}
	
	HelmDefense getGame() {
		return this.game;
	}
	
	Gamemode getGamemode() {
		return this.gamemode;
	}
	
	void setGamemode(Gamemode mode) {
		this.gamemode = mode;
		switch (mode) {
		case NORMAL:
			this.stepButton.setVisible(false);
			break;
		case DEMO:
			this.stepButton.setVisible(true);
			break;
		}
	}
	
	final String getWindowName() {
		return this.windowNameProperty.get();
	}
	
	final void setWindowName(String windowName) {
		this.windowNameProperty.set(windowName);
	}
	
	final StringProperty windowNameProperty() {
		return this.windowNameProperty;
	}
	
	final String getLevelName() {
		return this.levelNameProperty.get();
	}
	
	final void setLevelName(String levelName) {
		this.levelNameProperty.set(levelName);
	}
	
	final StringProperty levelNameProperty() {
		return this.levelNameProperty;
	}
	
	final String getWaveName() {
		return this.waveNameProperty.get();
	}
	
	final void setWaveName(String waveName) {
		this.waveNameProperty.set(waveName);
	}
	
	final StringProperty waveNameProperty() {
		return this.waveNameProperty;
	}
	
	final int getLevelLives() {
		return this.levelLivesProperty.get();
	}
	
	final void setLevelLives(int levelLives) {
		this.levelLivesProperty.set(levelLives);
	}
	
	final IntegerProperty levelLivesProperty() {
		return this.levelLivesProperty;
	}
	
	static String pathToImgPath(String path) {
		return pathToImgPath(path, "");
	}
	
	static String pathToImgPath(String path, String end) {
		return path.replace('.', File.separatorChar) + end + ".png";
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
	
	public enum Gamemode {
		NORMAL("Normal"),
		DEMO("Démonstration");
		
		private String name;
		
		public static final Gamemode DEFAULT = NORMAL;
		
		private Gamemode(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}
}