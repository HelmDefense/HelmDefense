package fr.helmdefense.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.GameMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class Controller implements Initializable {
	private Level level;
	
	/* Header */
	// Controls buttons
    @FXML
    private Button optionButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button speedButton;
    
    // Title
    @FXML
    private Label levelNameLabel;
    
    // Money
    @FXML
    private Label moneyLabel;
    @FXML
    private ImageView moneyImage;
    
    
    /* Left Entity Infos */
    // Title
    @FXML
    private Label entityNameLabel;
    
    // Health bar
    @FXML
    private Label entityHealthPercentLabel;
    @FXML
    private ProgressBar entityHealthBar;
    @FXML
    private Label EntityHealthBarLabel;
    @FXML
    private Label entityHealthBonusLabel;
    
    // Hp bar
    @FXML
    private Label entityHpBarLabel;
    @FXML
    private ProgressBar entityHpBar;
    @FXML
    private Label entityHpBonusLabel;
    
    // Dmg bar
    @FXML
    private Label entityDmgBarLabel;
    @FXML
    private ProgressBar entityDmgBar;
    @FXML
    private Label entityDmgBonusLabel;
    
    // Mvt spd bar
    @FXML
    private Label entityMvtSpdBarLabel;
    @FXML
    private ProgressBar entityMvtSpdBar;
    @FXML
    private Label entityMvtSpdBonusLabel;
    
    // Atk spd bar
    @FXML
    private Label entityAtkSpdBarLabel;
    @FXML
    private ProgressBar entityAtkSpdBar;
    @FXML
    private Label entityAtkSpdBonusLabel;
    
    // Atk range bar
    @FXML
    private Label entityAtkRangeBarLabel;
    @FXML
    private ProgressBar entityAtkRangeBar;
    @FXML
    private Label entityAtkRangeBonusLabel;
    
    // Dist range bar
    @FXML
    private Label entityDistRangeBarLabel;
    @FXML
    private ProgressBar entityDistRangeBar;
    @FXML
    private Label entityDistRangeBonusLabel;
    
    // Money bar
    @FXML
    private Label entityMoneyLabel;
    @FXML
    private Label entityMoneyBarLabel;
    @FXML
    private ProgressBar entityMoneyBar;
    
    // Description & Abilities
    @FXML
    private TextFlow entityDescText;
    
    /* Center */
    // Board
    @FXML
    private Pane levelPane;
    
    // Map
    @FXML
    private TilePane mapPane;
    
    /* Right Entity Infos */
    // Entity list
    @FXML
    private VBox entityIDCardList;
    
    /* Footer */
    // Right text
    @FXML
    private Label buyInfoLabel;

    @FXML
    void optionButtonAction(ActionEvent event) {
    	System.out.println("Options");
    }

    @FXML
    void pauseButtonAction(ActionEvent event) {
    	System.out.println("Pause");
    }

    @FXML
    void speedButtonAction(ActionEvent event) {
    	System.out.println("Vitesse");
    }
	
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
		
		this.mapPane.setPrefColumns(GameMap.WIDTH);
		this.mapPane.setPrefRows(GameMap.HEIGHT);
		this.level = Level.load("test_level");
		for (int y = 0; y < GameMap.HEIGHT; y++)
			for (int x = 0; x < GameMap.WIDTH; x++)
				this.mapPane.getChildren().add(
						new ImageView(Paths.get(
								System.getProperty("user.dir"),
								"assets",
								"maptiles",
								this.level.getMap().getTile(x, y) + ".png"
						).toUri().toString())
				);
	}
}