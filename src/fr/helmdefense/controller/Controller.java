package fr.helmdefense.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.defenders.Archer;
import fr.helmdefense.model.entities.defenders.Catapult;
import fr.helmdefense.model.entities.defenders.ElvenShooter;
import fr.helmdefense.model.entities.defenders.HumanWarrior;
import fr.helmdefense.model.entities.projectiles.Projectile;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.view.inventory.InventoryView;
import fr.helmdefense.view.inventory.item.InventoryItem;
import fr.helmdefense.view.statbar.StatBar;
import fr.helmdefense.view.statbar.StatBar.DisplayStyle;
import javafx.beans.property.IntegerProperty;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;

public class Controller implements Initializable {
	private Level level;
	private Circle atkRange;
	private Circle shootRange;
	
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
    @FXML
    VBox upgradeVBox;
    @FXML
    Label upgradeNameLabel;
    @FXML
    ImageView upgradeImage;
    @FXML
    TextFlow upgradeDescText;
    @FXML
    Button returnUpgradeButton;
    @FXML
    Button unlockUpgradeButton;
    
    // Inventory
    @FXML
    InventoryView inventory;
    
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
		this.level = Level.load("troll");
		this.upgradeVBox.setVisible(false);
		this.returnUpgradeButton.setOnMouseClicked(c -> this.upgradeVBox.setVisible(false));
		
		setupStats();
		Rectangle clip = new Rectangle(0, 0, GameMap.WIDTH * GameMap.TILE_SIZE, GameMap.HEIGHT * GameMap.TILE_SIZE);
		this.levelPane.setClip(clip);
		
		this.addIDCard(HumanWarrior.class);
		this.addIDCard(Archer.class);
		this.addIDCard(ElvenShooter.class);
		this.addIDCard(Catapult.class);
		
		this.mapPane.setPrefColumns(GameMap.WIDTH);
		this.mapPane.setPrefRows(GameMap.HEIGHT);
		for (int y = 0; y < GameMap.HEIGHT; y++)
			for (int x = 0; x < GameMap.WIDTH; x++)
				this.mapPane.getChildren().add(getImg("maptiles", this.level.getMap().getTile(x, y) + ".png"));
		
		ListChangeListener<Entity> lcl = c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (Entity e : c.getAddedSubList()) {
						ImageView img = getImg("entities", e.data().getPath().replace('.', File.separatorChar) + ".png");
						img.setId(e.getId());
						img.translateXProperty().bind(e.xProperty().multiply(GameMap.TILE_SIZE).subtract(img.getImage().getWidth() / 2));
						img.translateYProperty().bind(e.yProperty().multiply(GameMap.TILE_SIZE).subtract(img.getImage().getHeight() / 2));
						if (e instanceof LivingEntity)
							img.setOnMouseClicked(i -> displayStats((LivingEntity) e));
						else if (e instanceof Projectile)
							img.setRotate(((Projectile) e).angle() + 45);
						this.levelPane.getChildren().add(img);
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
		
		MapChangeListener<Class<? extends Entity>, IntegerProperty> mcl = c -> {
			if(c.wasRemoved()) {
				inventory.getItems().remove(inventory.getItem(Entities.getData(c.getKey()).getPath()));
			}
			if(c.wasAdded()) {
				InventoryItem item = new InventoryItem(Entities.getData(c.getKey()).getPath(), 0);
				item.amountProperty().bind(c.getValueAdded());
				item.setOnMouseClicked(e -> {
					this.level.getInv().removeEntity(c.getKey());
				});
				inventory.getItems().add(item);
			}
		};
		this.level.getInv().getContent().addListener(mcl);
		this.moneyLabel.textProperty().bind(this.level.purseProperty().asString());
		this.level.startLoop();

		new HumanWarrior(2.5, 4.5).spawn(this.level);
		new Archer(10.5, 7.5).spawn(this.level);
		new ElvenShooter(8.5, 2.5).spawn(this.level);
	}
	
	private void setupStats() {
		this.atkRange = new Circle();
		this.shootRange = new Circle();
		setupStat(this.entityHpLabel, this.entityHpBar, this.entityHpBonusLabel);
		setupStat(this.entityDmgLabel, this.entityDmgBar, this.entityDmgBonusLabel);
		setupStat(this.entityMvtSpdLabel, this.entityMvtSpdBar, this.entityMvtSpdBonusLabel);
		setupStat(this.entityAtkSpdLabel, this.entityAtkSpdBar, this.entityAtkSpdBonusLabel);
		setupStat(this.entityAtkRangeLabel, this.entityAtkRangeBar, this.entityAtkRangeBonusLabel, this.atkRange);
		setupStat(this.entityDistRangeLabel, this.entityDistRangeBar, this.entityDistRangeBonusLabel, this.shootRange);
		setupStat(this.entityCostLabel, this.entityCostBar, this.entityCostCoin);
		setupStat(this.entityRewardLabel, this.entityRewardBar, this.entityRewardCoin);
		
		this.atkRange.setStroke(Color.RED);
		this.atkRange.setFill(RadialGradient.valueOf("radial-gradient(center 50% 50%, radius 100%, transparent 25%, red 100%)"));
		this.shootRange.setStroke(Color.AQUA);
		this.shootRange.setFill(RadialGradient.valueOf("radial-gradient(center 50% 50%, radius 100%, transparent 25%, aqua 100%)"));
		this.levelPane.getChildren().add(this.atkRange);
		this.levelPane.getChildren().add(this.shootRange);
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
		return new ImageView(imgPath(paths));
	}
	
	public static String imgPath(String... paths) {
		return Paths.get(
				Paths.get(System.getProperty("user.dir"), "assets").toString(),
				paths
		).toUri().toString();
	}
	
	Level getLvl() {
		return this.level;
	}
	
	private void displayStats(LivingEntity e) {
		// Current HP
		EntityData entityData = e.data();
		int entityMaxHp = entityData.getStats().getHp();
		entityNameLabel.setText(entityData.getName());
		entityHealthPercentLabel.textProperty().bind(e.hpProperty().multiply(100).divide(entityMaxHp).asString().concat("%"));
		
		entityHealthBar.setDisplayStyle(DisplayStyle.FULL_ROUND)
				.setMax(entityMaxHp)
				.valueProperty().bind(e.hpProperty());
		entityHealthBonusLabel.setText("+ " + e.getShield());
		e.shieldProperty().addListener((obs, o, n) -> entityHealthBonusLabel.setText("+ " + n));
		
		// Statistics
		manageStats(entityData);

		// Display range
		this.atkRange.setRadius((entityAtkRangeBar.getValue() + 0.5) * GameMap.TILE_SIZE);
		this.atkRange.translateXProperty().bind(e.xProperty().multiply(GameMap.TILE_SIZE));
		this.atkRange.translateYProperty().bind(e.yProperty().multiply(GameMap.TILE_SIZE));
		
		this.shootRange.setRadius((entityDistRangeBar.getValue() + 0.5) * GameMap.TILE_SIZE);
		this.shootRange.translateXProperty().bind(e.xProperty().multiply(GameMap.TILE_SIZE));
		this.shootRange.translateYProperty().bind(e.yProperty().multiply(GameMap.TILE_SIZE));	
	}
	
	protected void manageStats(EntityData entityData) {
		// HP
		this.entityHpBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
			.setValue(entityData.getStats().getHp())
			.setMax(entityData.getStats(Tier.TIER_3).getHp());
		this.entityHpBonusLabel.setText("");
		if (this.entityHpBar.getValue() == -1)
			this.entityHpLabel.setVisible(false);
		else
			this.entityHpLabel.setVisible(true);

		// Damages
		this.entityDmgBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
			.setValue(entityData.getStats().getDmg())
			.setMax(entityData.getStats(Tier.TIER_3).getDmg());
		this.entityDmgBonusLabel.setText("");
		if (this.entityDmgBar.getValue() == -1)
			this.entityDmgLabel.setVisible(false);
		else
			this.entityDmgLabel.setVisible(true);

		// Movement speed
		this.entityMvtSpdBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().getMvtSpd())
			.setMax(entityData.getStats(Tier.TIER_3).getMvtSpd());
		this.entityMvtSpdBonusLabel.setText("");
		if (this.entityMvtSpdBar.getValue() == -1)
			this.entityMvtSpdLabel.setVisible(false);
		else
			this.entityMvtSpdLabel.setVisible(true);

		// attack speed
		this.entityAtkSpdBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().getAtkSpd())
			.setMax(entityData.getStats(Tier.TIER_3).getAtkSpd());
		this.entityAtkSpdBonusLabel.setText("");
		if (this.entityAtkSpdBar.getValue() == -1)
			this.entityAtkSpdLabel.setVisible(false);
		else
			this.entityAtkSpdLabel.setVisible(true);

		// attack range
		this.entityAtkRangeBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().getAtkRange())
			.setMax(entityData.getStats(Tier.TIER_3).getAtkRange());
		this.entityAtkRangeBonusLabel.setText("");
		if (this.entityAtkRangeBar.getValue() == -1)
			this.entityAtkRangeLabel.setVisible(false);
		else
			this.entityAtkRangeLabel.setVisible(true);

		// distance range
		this.entityDistRangeBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().getShootRange())
			.setMax(entityData.getStats(Tier.TIER_3).getShootRange());
		this.entityDistRangeBonusLabel.setText("");
		if (this.entityDistRangeBar.getValue() == -1)
			this.entityDistRangeLabel.setVisible(false);
		else
			this.entityDistRangeLabel.setVisible(true);

		// cost ( for defenders )
		this.entityCostBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
			.setValue(entityData.getStats().getCost())
			.setMax(entityData.getStats(Tier.TIER_3).getCost());
		if (this.entityCostBar.getValue() == -1)
			this.entityCostLabel.setVisible(false);
		else
			this.entityCostLabel.setVisible(true);

		//reward ( for attacker )
		this.entityRewardBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
			.setValue(entityData.getStats().getReward())
			.setMax(entityData.getStats(Tier.TIER_3).getReward());
		if (this.entityRewardBar.getValue() == -1)
			this.entityRewardLabel.setVisible(false);
		else
			this.entityRewardLabel.setVisible(true);
	}
}