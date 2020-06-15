package fr.helmdefense.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.game.GameLooseAction;
import fr.helmdefense.model.actions.game.GameNewWaveAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.game.GameWinAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.living.special.Door;
import fr.helmdefense.model.entities.living.special.Hero;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Hitbox;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.Dir;
import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.view.inventory.InventoryView;
import fr.helmdefense.view.inventory.item.InventoryItem;
import fr.helmdefense.view.statbar.StatBar;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class LevelController implements Initializable, ActionListener {
	private Controller main;
	private Level level;
	private String levelName;
	private Hero hero;
	private Circle atkRange;
	private Circle shootRange;
	private IntegerProperty selectedAmountProperty;
	private Set<Controls> controlsPressed;
	private LivingEntity selectedEntity;
	
	boolean inOptions;
	StackPane boardPane;
	Pane levelPane;
	TilePane mapPane;
	ScrollPane rightPane;
	VBox entityIDCardList;
	AnchorPane leftPane;
	VBox gameRecapBox;
	Label gameEndStatus;
	Button gameFinishButton;
	
	/* Left Entity Infos */
	@FXML
	TabPane tabPane;
	@FXML
	Tab statsTab;
	@FXML
	Tab inventoryTab;

	// Title
	@FXML
	Label entityNameLabel;
	@FXML
	VBox statsBox;

	// Health bar
	@FXML
	HBox entityHealthBarBox;
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
	
	// Interaction buttons
	@FXML
	HBox interactionBox;
	@FXML
	Button sellingButton;
	@FXML
	Button pickupButton;

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
	
	public LevelController(Controller main, String levelName, Hero hero) {
		this.main = main;
		this.level = Level.load(this.levelName = levelName);
		this.hero = hero;
		this.selectedAmountProperty = new SimpleIntegerProperty();
		this.controlsPressed = new HashSet<Controls>();
		
		this.mapPane = new TilePane();
		this.levelPane = new Pane(this.mapPane);
		this.levelPane.setPrefSize(1024, 704);
		
		this.gameEndStatus = new Label("Fin de partie");
		this.gameEndStatus.setFont(new Font(24));
		this.gameFinishButton = new Button("Terminer");
		this.gameFinishButton.setOnAction(event -> this.main.returnToMenu());
		this.gameRecapBox = new VBox(30, this.gameEndStatus, this.gameFinishButton);
		this.gameRecapBox.managedProperty().bind(this.gameRecapBox.visibleProperty());
		this.gameRecapBox.setVisible(false);
		this.gameRecapBox.setPadding(new Insets(15));
		this.gameRecapBox.setStyle("-fx-background-color: -fx-color;");
		this.gameRecapBox.setMaxSize(200, 150);
		this.gameRecapBox.setAlignment(Pos.CENTER);
		this.boardPane = new StackPane(this.levelPane, this.gameRecapBox);
		
		this.entityIDCardList = new VBox(25);
		this.entityIDCardList.setAlignment(Pos.TOP_CENTER);
		this.entityIDCardList.setPadding(new Insets(15));
		this.rightPane = new ScrollPane(this.entityIDCardList);
		this.rightPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.rightPane.managedProperty().bind(this.rightPane.visibleProperty());
		this.main.main.setRight(this.rightPane);
		
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fr/helmdefense/view/LeftPane.fxml"));
			loader.setController(this);
			this.main.main.setLeft(this.leftPane = loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.leftPane.managedProperty().bind(this.leftPane.visibleProperty());
		this.show();

		System.out.println("Level " + levelName + " loaded with hero " + this.hero.getType());
	}
	
	@FXML
	void sellingAction(ActionEvent event) {
		if (this.selectedEntity != null) {
			this.level.earnCoins((int) (this.selectedEntity.stat(Attribute.COST) * this.selectedEntity.getHp() / this.selectedEntity.stat(Attribute.HP)));
			this.selectedEntity.dispawn();
		}
	}
	
	@FXML
	void pickupAction(ActionEvent event) {
		if (this.selectedEntity != null && this.selectedEntity.getHp() == this.selectedEntity.stat(Attribute.HP)) {
			this.level.getInv().addEntity(this.selectedEntity.getType());
			this.selectedEntity.dispawn();
		}
	}
	
	void handleKeyboardInput(KeyEvent event) {
		Controls control = Controls.fromKeyCode(event.getCode());
		boolean pressed = event.getEventType() == KeyEvent.KEY_PRESSED;
		
		if (pressed) {
			if (this.controlsPressed.add(control)) {
				switch (control) {
				case TOGGLE_PAUSE:
					this.main.togglePause();
					break;
				case HERO_POWER:
					if (this.level.getGameloop().isPlaying())
						this.hero.usePower();
					break;
				default:
					break;
				}
			}
		}
		else
			this.controlsPressed.remove(control);
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.controlsPressed.contains(Controls.HERO_UP))
			this.hero.move(Dir.N);
		if (this.controlsPressed.contains(Controls.HERO_DOWN))
			this.hero.move(Dir.S);
		if (this.controlsPressed.contains(Controls.HERO_LEFT))
			this.hero.move(Dir.W);
		if (this.controlsPressed.contains(Controls.HERO_RIGHT))
			this.hero.move(Dir.E);
	}
	
	@ActionHandler
	public void onNewWave(GameNewWaveAction action) {
		if (action.getNewWave() == null)
			return;
		
		this.main.setWaveName(action.getNewWave().getName());
	}
	
	@ActionHandler
	public void onLoose(GameLooseAction action) {
		this.main.setWaveName("Défaite");
		this.gameEndStatus.setText("Défaite");
		this.gameRecapBox.setVisible(true);
	}
	
	@ActionHandler
	public void onWin(GameWinAction action) {
		this.main.setWaveName("Victoire");
		this.gameEndStatus.setText("Victoire");
		this.gameRecapBox.setVisible(true);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Component & window initialization
		this.upgradeVBox.setVisible(false);
		this.returnUpgradeButton.setOnMouseClicked(c -> this.upgradeVBox.setVisible(false));
		this.main.controlButtons.setVisible(true);
		this.main.primaryStage.titleProperty().bind(Bindings.concat(this.main.waveNameProperty(), " - ", this.main.levelNameProperty(), " (Défenses ", this.main.levelLivesProperty(), "%) - ", this.main.windowNameProperty()));
		this.main.levelNameLabel.textProperty().bind(Bindings.concat(this.main.levelNameProperty(), " (Défenses ", this.main.levelLivesProperty(), "%) - ", this.main.waveNameProperty()));
		this.main.setLevelName(this.level.getName());
		this.main.levelLivesProperty().bind(this.level.livesProperty().multiply(100).divide(this.level.getTotalLives()));
		this.main.setWaveName("Préparation");
		this.main.buyInfoLabel.setVisible(true);
		this.main.buyInfoLabel.setText("Sélectionnez une entité dans l'inventaire pour la placer");
		this.level.getGameloop().speednessProperty().bind(this.main.speedness.valueProperty());
		this.stopDisplayStats();
		this.setup();
		
		// Board view
		this.boardPane.setClip(new Rectangle(0, 0, GameMap.WIDTH * GameMap.TILE_SIZE, GameMap.HEIGHT * GameMap.TILE_SIZE));
		
		// Money loading
		this.main.moneyBox.setVisible(true);
		this.main.moneyLabel.textProperty().bind(this.level.purseProperty().asString());
		this.main.moneyImage.setImage(Controller.getImg("models", "coin.png"));
		
		// Right side loading
		for (LivingEntityType type : LivingEntityType.CLASSIC_DEFENDERS)
			this.addIDCard(type);
		
		// Map loading
		this.mapPane.setPrefColumns(GameMap.WIDTH);
		this.mapPane.setPrefRows(GameMap.HEIGHT);
		for (int y = 0; y < GameMap.HEIGHT; y++)
			for (int x = 0; x < GameMap.WIDTH; x++)
				this.mapPane.getChildren().add(Controller.getImgView("maptiles", this.level.getMap().getTile(x, y) + ".png"));
		
		// Entity listener
		SetChangeListener<Entity> scl = c -> {
			if (c.wasAdded()) {
				Entity e = c.getElementAdded();
				ImageView img = Controller.getImgView("entities", Controller.pathToImgPath(e.data().getPath()));
				img.setId(e.getId());
				img.visibleProperty().bind(e.visibleProperty());
				img.translateXProperty().bind(e.xProperty().multiply(GameMap.TILE_SIZE).subtract(img.getImage().getWidth() / 2));
				img.translateYProperty().bind(e.yProperty().multiply(GameMap.TILE_SIZE).subtract(img.getImage().getHeight() / 2));
				if (e instanceof LivingEntity) 
					img.setOnMouseClicked(event -> {
						if (this.selectedEntity == e)
							this.stopDisplayStats();
						else
							this.displayStats((LivingEntity) e);
					});
				else if (e instanceof Projectile)
					img.setRotate(((Projectile) e).angle() + 45);
				this.levelPane.getChildren().add(img);
			}
			if (c.wasRemoved()) {
				if (this.selectedEntity == c.getElementRemoved())
					this.stopDisplayStats();
				this.levelPane.getChildren().remove(this.levelPane.lookup("#" + c.getElementRemoved().getId()));
			}
		};
		this.level.getEntities().addListener(scl);
		
		// Inventory listener
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
		
		// Placing listener
		this.levelPane.setOnMouseClicked(event -> {
			if (this.main.getGamemode() == Controller.Gamemode.NORMAL && ! this.level.getGameloop().isPlaying())
				return;
			
			InventoryItem item = (InventoryItem) this.inventory.getToggleGroup().getSelectedToggle();
			if (item != null) {
				LivingEntityType entity = item.getValue();
				Location loc = new Location(event.getX() / GameMap.TILE_SIZE, event.getY() / GameMap.TILE_SIZE);
				Hitbox hitbox = new Hitbox(loc, entity.getData().getSize());
				
				// Check if it overlaps other hitbox
				for (Entity e : this.level.getEntities()) 
					if (e.getHitbox().overlaps(hitbox)) 
						return;
				// Check if it is out of the map
				if (! loc.isInMap(hitbox.getSize()))
					return;
				
				new LivingEntity(entity, loc).spawn(this.level);
				this.level.getInv().removeEntity(entity);
			}
		});
		
		// Inventory selection listener
		this.inventory.getToggleGroup().selectedToggleProperty().addListener((obs, o, n) -> {
			if (n != null) {
				InventoryItem item = (InventoryItem) n;
				this.updateBuyInfoLabel(item.getAmount(), item.getValue().getData().getName());
				this.selectedAmountProperty.bind(item.amountProperty());
			}
			else 
				this.main.buyInfoLabel.setText("Sélectionnez une entité dans l'inventaire pour la placer");
		});
		this.selectedAmountProperty.addListener((obs, o, n) -> {
			updateBuyInfoLabel(n.intValue(), ((InventoryItem) this.inventory.getToggleGroup().getSelectedToggle()).getValue().getData().getName());
		});
	}

	private void setup() {
		this.atkRange = new Circle();
		this.shootRange = new Circle();
		bindNodes(this.entityHpLabel, this.entityHpBar, this.entityHpBonusLabel);
		bindNodes(this.entityDmgLabel, this.entityDmgBar, this.entityDmgBonusLabel);
		bindNodes(this.entityMvtSpdLabel, this.entityMvtSpdBar, this.entityMvtSpdBonusLabel);
		bindNodes(this.entityAtkSpdLabel, this.entityAtkSpdBar, this.entityAtkSpdBonusLabel);
		bindNodes(this.entityAtkRangeLabel, this.entityAtkRangeBar, this.entityAtkRangeBonusLabel, this.atkRange);
		bindNodes(this.entityDistRangeLabel, this.entityDistRangeBar, this.entityDistRangeBonusLabel, this.shootRange);
		bindNodes(this.entityCostLabel, this.entityCostBar, this.entityCostCoin);
		bindNodes(this.entityRewardLabel, this.entityRewardBar, this.entityRewardCoin);

		this.atkRange.setStroke(Color.RED);
		this.atkRange.setFill(RadialGradient.valueOf("radial-gradient(center 50% 50%, radius 100%, transparent 25%, red 100%)"));
		this.shootRange.setStroke(Color.AQUA);
		this.shootRange.setFill(RadialGradient.valueOf("radial-gradient(center 50% 50%, radius 100%, transparent 25%, aqua 100%)"));
		this.levelPane.getChildren().add(this.atkRange);
		this.levelPane.getChildren().add(this.shootRange);
	}

	private static void bindNodes(Node... nodes) {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].managedProperty().bind(nodes[i].visibleProperty());
			if (i != 0)
				nodes[i].visibleProperty().bind(nodes[i - 1].visibleProperty());
		}
	}

	private void addIDCard(LivingEntityType type) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fr/helmdefense/view/EntityIDCard.fxml"));
			loader.setController(new IDCardController(type, this));
			VBox card = loader.load();
			this.entityIDCardList.getChildren().add(card);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateBuyInfoLabel(int amount, String entityName) {
		this.main.buyInfoLabel.setText(entityName + " x" + amount + " - Clic gauche pour placer");
	}
	
	protected void displayStats(EntityData data) {
		if (this.selectedEntity == null)
			this.manageStats(data, null);
		else
			this.displayStats(this.selectedEntity);
	}

	private void displayStats(LivingEntity e) {
		this.selectedEntity = e;
		
		// Current HP
		EntityData data = e.data();
		int entityMaxHp = (int) e.stat(Attribute.HP);
		if (entityMaxHp == -1 && e instanceof Door)
			entityMaxHp = ((Door) e).getInitialHp();
		this.entityNameLabel.setText(data.getName());
		IntegerBinding health = e.hpProperty().multiply(100).divide(entityMaxHp);
		this.entityHealthPercentLabel.textProperty().bind(health.asString().concat("%"));

		this.entityHealthBar.setMax(entityMaxHp).valueProperty().bind(e.hpProperty());
		this.entityHealthBonusLabel.textProperty().bind(Bindings.concat("+ ", e.shieldProperty()));
		
		this.interactionBox.setVisible(e.getType().getSide() == EntitySide.DEFENDER && e.getType().getSubType() == LivingEntityType.SubType.CLASSIC);
		this.sellingButton.textProperty().bind(Bindings.concat("Revendre (", e.hpProperty().multiply((int) e.stat(Attribute.COST)).divide(entityMaxHp), ")"));
		this.pickupButton.disableProperty().bind(health.lessThan(100));
		
		// Statistics
		this.manageStats(data, e);

		// Display range
		this.atkRange.setRadius(e.stat(Attribute.ATK_RANGE) * GameMap.TILE_SIZE);
		this.atkRange.translateXProperty().bind(e.xProperty().multiply(GameMap.TILE_SIZE));
		this.atkRange.translateYProperty().bind(e.yProperty().multiply(GameMap.TILE_SIZE));
		
		this.shootRange.setRadius(e.stat(Attribute.SHOOT_RANGE) * GameMap.TILE_SIZE);
		this.shootRange.translateXProperty().bind(e.xProperty().multiply(GameMap.TILE_SIZE));
		this.shootRange.translateYProperty().bind(e.yProperty().multiply(GameMap.TILE_SIZE));
	}
	
	protected void manageStats(EntityData data, LivingEntity entity) {
		Controller.setNodesVisibility(true, this.entityHealthBarBox, this.statsBox, this.entityAtkRangeLabel,
				this.entityDistRangeLabel);
		
		dispStat(this.entityHpBar, this.entityHpLabel, this.entityHpBonusLabel, Attribute.HP, data, entity);
		dispStat(this.entityDmgBar, this.entityDmgLabel, this.entityDmgBonusLabel, Attribute.DMG, data, entity);
		dispStat(this.entityMvtSpdBar, this.entityMvtSpdLabel, this.entityMvtSpdBonusLabel, Attribute.MVT_SPD, data, entity);
		dispStat(this.entityAtkSpdBar, this.entityAtkSpdLabel, this.entityAtkSpdBonusLabel, Attribute.ATK_SPD, data, entity);
		dispStat(this.entityAtkRangeBar, this.entityAtkRangeLabel, this.entityAtkRangeBonusLabel, Attribute.ATK_RANGE, data, entity);
		dispStat(this.entityDistRangeBar, this.entityDistRangeLabel, this.entityDistRangeBonusLabel, Attribute.SHOOT_RANGE, data, entity);
		dispStat(this.entityCostBar, this.entityCostLabel, null, Attribute.COST, data, entity);
		dispStat(this.entityRewardBar, this.entityRewardLabel, null, Attribute.REWARD, data, entity);

		this.tabPane.getSelectionModel().select(this.statsTab);
	}
	
	private static void dispStat(StatBar bar, Label label, Label bonus, Attribute attr, EntityData data, LivingEntity entity) {
		bar.setValue(data.getStats().get(attr));
		if (data.getStats(Tier.TIER_3) != null)
			bar.setMax(data.getStats(Tier.TIER_3).get(attr));
		else
			bar.setMax(data.getStats().get(attr));
		if (bonus != null && entity != null) {
			int bonusValue = (int) Math.round((entity.stat(attr) - bar.getValue()) * 100 / bar.getValue());
			String bonusTxt = null;
			if (bonusValue < 0)
				bonusTxt = "- " + (- bonusValue) + "%";
			else if (bonusValue > 0)
				bonusTxt = "+ " + bonusValue + "%";
			bonus.setText(bonusTxt);
		}
		
		if (bar.getValue() == -1)
			label.setVisible(false);
		else
			label.setVisible(true);
	}
	
	private void stopDisplayStats() {
		this.selectedEntity = null;
		this.entityNameLabel.setText("Sélectionnez une entité");
		Controller.setNodesVisibility(false, this.entityHealthBarBox, this.statsBox, this.entityAtkRangeLabel,
				this.entityDistRangeLabel);
	}
	
	void show() {
		this.main.main.setCenter(this.boardPane);
		Controller.setNodesVisibility(true, this.main.main.getRight(), this.main.main.getLeft(), this.main.buyInfoLabel,
				this.main.moneyBox, this.main.levelControlButtons);
		
		this.main.creditsLabel.setOnMouseClicked(null);
	}
	
	public void start() {
		this.hero.teleport(GameMap.WIDTH / 2, GameMap.HEIGHT / 2);
		this.hero.spawn(this.level);
		Actions.registerListeners(this);
		
		this.main.getGame().startLevel(this.level);
	}
	
	public void stop() {
		this.hero.dispawn();
		this.level.end();
		this.level = null;
	}

	Level getLvl() {
		return this.level;
	}
	
	String getLevelName() {
		return this.levelName;
	}
	
	Hero getHero() {
		return this.hero;
	}
}