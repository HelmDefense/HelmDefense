package fr.helmdefense.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.attackers.Goblin;
import fr.helmdefense.model.entities.attackers.OrcWarrior;
import fr.helmdefense.model.entities.attackers.Troll;
import fr.helmdefense.model.entities.defenders.Archer;
import fr.helmdefense.model.entities.defenders.Catapult;
import fr.helmdefense.model.entities.defenders.ElvenShooter;
import fr.helmdefense.model.entities.defenders.HumanWarrior;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.view.statbar.StatBar;
import fr.helmdefense.view.statbar.StatBar.DisplayStyle;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    
    /* Left Entity Infos */
    // Title
    @FXML
    Label entityNameLabel;
    
    // Health bar
    @FXML
    Label entityHealthPercentLabel;
    @FXML
    StatBar entityHealthBar;
    @FXML
    Label entityHealthBonusLabel;
    
    // Hp bar
    @FXML
    Label entityHpLabel;
    @FXML
    StatBar entityHpBar;
    @FXML
    Label entityHpBonusLabel;
    
    // Dmg bar
    @FXML
    Label entityDmgLabel;
    @FXML
    StatBar entityDmgBar;
    @FXML
    Label entityDmgBonusLabel;
    
    // Mvt spd bar
    @FXML
    Label entityMvtSpdLabel;
    @FXML
    StatBar entityMvtSpdBar;
    @FXML
    Label entityMvtSpdBonusLabel;
    
    // Atk spd bar
    @FXML
    Label entityAtkSpdLabel;
    @FXML
    StatBar entityAtkSpdBar;
    @FXML
    Label entityAtkSpdBonusLabel;
    
    // Atk range bar
    @FXML
    Label entityAtkRangeLabel;
    @FXML
    StatBar entityAtkRangeBar;
    @FXML
    Label entityAtkRangeBonusLabel;
    
    // Dist range bar
    @FXML
    Label entityDistRangeLabel;
    @FXML
    StatBar entityDistRangeBar;
    @FXML
    Label entityDistRangeBonusLabel;
    
    // Cost bar
    @FXML
    Label entityCostLabel;
    @FXML
    StatBar entityCostBar;
    @FXML
    ImageView entityCostCoin;
    
    // Reward bar
    @FXML
    Label entityRewardLabel;
    @FXML
    StatBar entityRewardBar;
    @FXML
    ImageView entityRewardCoin;
    
    // Description & Abilities
    @FXML
    TextFlow entityDescText;
    
    // Inventory
    @FXML
    VBox inventoryList;
    
    /* Center */
    // Board
    @FXML
    Pane levelPane;
    
    // Map
    @FXML
    TilePane mapPane;
    
    /* Right Entity Infos */
    // Entity list
    @FXML
    VBox entityIDCardList;
    
    /* Footer */
    // Right text
    @FXML
    Label buyInfoLabel;

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
		setupStats();
		
		this.addIDCard(HumanWarrior.class);
		this.addIDCard(Archer.class);
		this.addIDCard(ElvenShooter.class);
		this.addIDCard(Catapult.class);
		this.mapPane.setPrefColumns(GameMap.WIDTH);
		this.mapPane.setPrefRows(GameMap.HEIGHT);
		this.level = Level.load("test_level");
		for (int y = 0; y < GameMap.HEIGHT; y++)
			for (int x = 0; x < GameMap.WIDTH; x++)
				this.mapPane.getChildren().add(getImg("maptiles", this.level.getMap().getTile(x, y) + ".png"));
		
		ListChangeListener<Entity> lcl = c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (Entity e : c.getAddedSubList()) {
						ImageView img = getImg("entities", Entities.getData(e.getClass()).getPath().replace('.', File.separatorChar) + ".png");
						img.setId(e.getId());
						e.bindX(img.translateXProperty(), x -> x.multiply(64).add(16));
						e.bindY(img.translateYProperty(), y -> y.multiply(64).add(16));
						this.levelPane.getChildren().add(img);
						img.setOnMouseClicked(i -> displayStats(e));
					}
				}
				if (c.wasRemoved()) {
					for (Entity e : c.getRemoved()) {
						this.levelPane.getChildren().remove(this.levelPane.lookup("#" + e.getId()));
					}
				}
			}
		};
		this.level.getEntities().addListener(lcl);
		MapChangeListener<Class<? extends Entity>, Integer> mcl = c -> {
			if(c.wasAdded()) {
				System.out.println("ajout" + c.getValueAdded() + " " + c.getKey());
			}
			if(c.wasRemoved()) {
				System.out.println("suppression" + c.getValueRemoved() + " " + c.getKey());
			}
		};
		this.level.getInv().getContent().addListener(mcl);
		
		this.level.startLoop();

		new OrcWarrior(0, 5).spawn(this.level);
		new HumanWarrior(2, 4).spawn(this.level);
		new Goblin(2, 5).spawn(this.level);
		new Troll(7, 5).spawn(this.level);
		new Goblin(10, 5).spawn(this.level);
	}
	
	private void setupStats() {
		setupStat(this.entityHpLabel, this.entityHpBar, this.entityHpBonusLabel);
		setupStat(this.entityDmgLabel, this.entityDmgBar, this.entityDmgBonusLabel);
		setupStat(this.entityMvtSpdLabel, this.entityMvtSpdBar, this.entityMvtSpdBonusLabel);
		setupStat(this.entityAtkSpdLabel, this.entityAtkSpdBar, this.entityAtkSpdBonusLabel);
		setupStat(this.entityAtkRangeLabel, this.entityAtkRangeBar, this.entityAtkRangeBonusLabel);
		setupStat(this.entityDistRangeLabel, this.entityDistRangeBar, this.entityDistRangeBonusLabel);
		setupStat(this.entityCostLabel, this.entityCostBar, this.entityCostCoin);
		setupStat(this.entityRewardLabel, this.entityRewardBar, this.entityRewardCoin);
	}
	
	private static void setupStat(Node... nodes) {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].managedProperty().bind(nodes[i].visibleProperty());
			if (i != 0)
				nodes[i].visibleProperty().bind(nodes[i - 1].visibleProperty());
		}
	}
	
	private void addIDCard(Class<? extends Entity> type) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/EntityIDCard.fxml"));
			loader.setController(new IDCardController(type, this));
			VBox card = loader.load();
			this.entityIDCardList.getChildren().add(card);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static ImageView getImg(String... paths) {
		return new ImageView(Paths.get(
				Paths.get(System.getProperty("user.dir"), "assets").toString(),
				paths
		).toUri().toString());
	}
	
	Level getLvl() {
		return this.level;
	}
	
	private void displayStats(Entity e) {
		// Health 
		EntityData entityData = Entities.getData(e.getClass());
		int entityHp = e.getHp(), entityMaxHp = entityData.getStats(Tier.TIER_1).getHp();
		entityNameLabel.setText(entityData.getName());
		entityHealthPercentLabel.setText(Math.round((double) entityHp / entityMaxHp * 100) + "%");
		entityHealthBar.setDisplayStyle(DisplayStyle.FULL_ROUND)
				.setMax(entityMaxHp);
		e.bindHp(entityHealthBar.valueProperty());
		entityHealthBonusLabel.setText("0%");
		
		// stats :	
		// Hp
		entityHpBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
				.setValue(entityMaxHp)
				.setMax(entityData.getStats(Tier.TIER_3).getHp());
		entityHpBonusLabel.setText("");
		if (entityHpBar.getValue() == -1)
			entityHpLabel.setVisible(false);
		else
			entityHpLabel.setVisible(true);
		
		// Damages
		
		entityDmgBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
				.setValue(entityData.getStats(Tier.TIER_1).getDmg())
				.setMax(entityData.getStats(Tier.TIER_3).getDmg());
		entityDmgBonusLabel.setText("");
		if (entityDmgBar.getValue() == -1)
			entityDmgLabel.setVisible(false);
		else
			entityDmgLabel.setVisible(true);
		
		// Movement speed
		
		entityMvtSpdBar.setDisplayStyle(DisplayStyle.VALUE)
				.setValue(entityData.getStats(Tier.TIER_1).getMvtSpd())
				.setMax(entityData.getStats(Tier.TIER_3).getMvtSpd());
		entityMvtSpdBonusLabel.setText("");
		if (entityMvtSpdBar.getValue() == -1)
			entityMvtSpdLabel.setVisible(false);
		else
			entityMvtSpdLabel.setVisible(true);
		
		// attack speed
		
		entityAtkSpdBar.setDisplayStyle(DisplayStyle.VALUE)
				.setValue(entityData.getStats(Tier.TIER_1).getAtkSpd())
				.setMax(entityData.getStats(Tier.TIER_3).getAtkSpd());
		entityAtkSpdBonusLabel.setText("");
		if (entityAtkSpdBar.getValue() == -1)
			entityAtkSpdLabel.setVisible(false);
		else
			entityAtkSpdLabel.setVisible(true);
		
		// portée attaque
		
		entityAtkRangeBar.setDisplayStyle(DisplayStyle.VALUE)
				.setValue(entityData.getStats(Tier.TIER_1).getAtkRange())
				.setMax(entityData.getStats(Tier.TIER_3).getAtkRange());
		entityAtkRangeBonusLabel.setText("");
		if (entityAtkRangeBar.getValue() == -1)
			entityAtkRangeLabel.setVisible(false);
		else
			entityAtkRangeLabel.setVisible(true);
		
		// distance range
		
		entityDistRangeBar.setDisplayStyle(DisplayStyle.VALUE)
				.setValue(entityData.getStats(Tier.TIER_1).getShootRange())
				.setMax(entityData.getStats(Tier.TIER_3).getShootRange());
		entityDistRangeBonusLabel.setText("");
		if (entityDistRangeBar.getValue() == -1)
			entityDistRangeLabel.setVisible(false);
		else
			entityDistRangeLabel.setVisible(true);
		
		// reward ( for attacker ) / cost ( for defenders )
		
	
		entityCostBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
				.setValue(entityData.getStats(Tier.TIER_1).getCost())
				.setMax(entityData.getStats(Tier.TIER_3).getCost());
		if (entityCostBar.getValue() == -1)
			entityCostLabel.setVisible(false);
		else
			entityCostLabel.setVisible(true);
		
		entityRewardBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
				.setValue(entityData.getStats(Tier.TIER_1).getReward())
				.setMax(entityData.getStats(Tier.TIER_3).getReward());
		if (entityRewardBar.getValue() == -1)
			entityRewardLabel.setVisible(false);
		else
			entityRewardLabel.setVisible(true);
	
		
		
		
	}
}