package fr.helmdefense.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Hitbox;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.view.inventory.InventoryView;
import fr.helmdefense.view.inventory.item.InventoryItem;
import fr.helmdefense.view.statbar.StatBar;
import fr.helmdefense.view.statbar.StatBar.DisplayStyle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	private IntegerProperty selectedAmountProperty;

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
	@FXML
	TabPane leftPane;
	@FXML
	Tab statsTab;
	@FXML
	Tab inventoryTab;

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
		this.selectedAmountProperty = new SimpleIntegerProperty();
		this.level = Level.load("troll");
		this.upgradeVBox.setVisible(false);
		this.returnUpgradeButton.setOnMouseClicked(c -> this.upgradeVBox.setVisible(false));
		setupStats();

		Rectangle clip = new Rectangle(0, 0, GameMap.WIDTH * GameMap.TILE_SIZE, GameMap.HEIGHT * GameMap.TILE_SIZE);
		this.levelPane.setClip(clip);

		this.addIDCard(LivingEntityType.HUMAN_WARRIOR);
		this.addIDCard(LivingEntityType.ARCHER);
		this.addIDCard(LivingEntityType.ELVEN);
		this.addIDCard(LivingEntityType.CATAPULT);

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

		MapChangeListener<LivingEntityType, IntegerProperty> mcl = c -> {
			if (c.wasRemoved()) {
				this.inventory.getItems().remove(this.inventory.getItem(c.getKey().getData().getPath()));
				this.inventory.getToggleGroup().selectToggle(null);
			}
			if (c.wasAdded()) {
				InventoryItem item = new InventoryItem(c.getKey().getData().getPath(), 0);
				item.amountProperty().bind(c.getValueAdded());
				item.setValue(c.getKey());
				item.setOnMouseClicked(e -> {
					if (item.isSelected())
						this.inventory.getToggleGroup().selectToggle(null);
					else
						this.inventory.getToggleGroup().selectToggle(item);
				});
				this.inventory.getItems().add(item);
			}
		};
		this.level.getInv().getContent().addListener(mcl);

		this.levelPane.setOnMouseClicked(event -> {
			InventoryItem item = (InventoryItem) this.inventory.getToggleGroup().getSelectedToggle();
			if ( item != null ) {
				LivingEntityType entity = item.getValue();
				Location loc = new Location(event.getX() / GameMap.TILE_SIZE, event.getY() / GameMap.TILE_SIZE);
				Hitbox hitbox = new Hitbox(loc, entity.getData().getSize());
				for (Entity e : this.level.getEntities()) 
					if (e.getHitbox().overlaps(hitbox)) 
						return;
				new LivingEntity(entity, loc).spawn(this.level);
				this.level.getInv().removeEntity(entity);
			}
		});
		
		this.inventory.getToggleGroup().selectedToggleProperty().addListener((obs, o, n) -> {
			if ( n != null) {
				InventoryItem item = (InventoryItem) n;
				updateBuyInfoLabel(item.getAmount(), item.getValue().getData().getName());
				this.selectedAmountProperty.bind(item.amountProperty());
			}
			else 
				this.buyInfoLabel.setText("Sélectionnez une entité dans l'inventaire pour la placer");
		});

		this.selectedAmountProperty.addListener((obs, o, n) -> {
			updateBuyInfoLabel(n.intValue(), ((InventoryItem)this.inventory.getToggleGroup().getSelectedToggle()).getValue().getData().getName());
		});

		this.moneyLabel.textProperty().bind(this.level.purseProperty().asString());
		this.level.startLoop();

		new LivingEntity(LivingEntityType.HUMAN_WARRIOR, 2.5, 4.5).spawn(this.level);
		new LivingEntity(LivingEntityType.ARCHER, 10.5, 7.5).spawn(this.level);
		new LivingEntity(LivingEntityType.ELVEN, 8.5, 2.5).spawn(this.level);
	}

	public void updateBuyInfoLabel(int amount, String entityName) {
		this.buyInfoLabel.setText(entityName + " x" + amount + " - Clic gauche pour placer");
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

	private void addIDCard(LivingEntityType type) {
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
		int entityMaxHp = (int) e.stat(Attribute.HP);
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
			.setValue(entityData.getStats().get(Attribute.HP))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.HP));
		this.entityHpBonusLabel.setText("");
		if (this.entityHpBar.getValue() == -1)
			this.entityHpLabel.setVisible(false);
		else
			this.entityHpLabel.setVisible(true);

		// Damages
		this.entityDmgBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
			.setValue(entityData.getStats().get(Attribute.DMG))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.DMG));
		this.entityDmgBonusLabel.setText("");
		if (this.entityDmgBar.getValue() == -1)
			this.entityDmgLabel.setVisible(false);
		else
			this.entityDmgLabel.setVisible(true);

		// Movement speed
		this.entityMvtSpdBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().get(Attribute.MVT_SPD))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.MVT_SPD));
		this.entityMvtSpdBonusLabel.setText("");
		if (this.entityMvtSpdBar.getValue() == -1)
			this.entityMvtSpdLabel.setVisible(false);
		else
			this.entityMvtSpdLabel.setVisible(true);

		// attack speed
		this.entityAtkSpdBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().get(Attribute.ATK_SPD))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.ATK_SPD));
		this.entityAtkSpdBonusLabel.setText("");
		if (this.entityAtkSpdBar.getValue() == -1)
			this.entityAtkSpdLabel.setVisible(false);
		else
			this.entityAtkSpdLabel.setVisible(true);

		// attack range
		this.entityAtkRangeBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().get(Attribute.ATK_RANGE))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.ATK_RANGE));
		this.entityAtkRangeBonusLabel.setText("");
		if (this.entityAtkRangeBar.getValue() == -1)
			this.entityAtkRangeLabel.setVisible(false);
		else
			this.entityAtkRangeLabel.setVisible(true);

		// distance range
		this.entityDistRangeBar.setDisplayStyle(DisplayStyle.VALUE)
			.setValue(entityData.getStats().get(Attribute.SHOOT_RANGE))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.SHOOT_RANGE));
		this.entityDistRangeBonusLabel.setText("");
		if (this.entityDistRangeBar.getValue() == -1)
			this.entityDistRangeLabel.setVisible(false);
		else
			this.entityDistRangeLabel.setVisible(true);

		// cost ( for defenders )
		this.entityCostBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
			.setValue(entityData.getStats().get(Attribute.COST))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.COST));
		if (this.entityCostBar.getValue() == -1)
			this.entityCostLabel.setVisible(false);
		else
			this.entityCostLabel.setVisible(true);

		//reward ( for attacker )
		this.entityRewardBar.setDisplayStyle(DisplayStyle.VALUE_ROUND)
			.setValue(entityData.getStats().get(Attribute.REWARD))
			.setMax(entityData.getStats(Tier.TIER_3).get(Attribute.REWARD));
		if (this.entityRewardBar.getValue() == -1)
			this.entityRewardLabel.setVisible(false);
		else
			this.entityRewardLabel.setVisible(true);
		
		this.leftPane.getSelectionModel().select(this.statsTab);
	}
}