package fr.helmdefense.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Tier;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

public class IDCardController implements Initializable {
	private LivingEntityType type;
	private LevelController main;
	private IntegerProperty costProperty;
	private int selectedImage;

	// Infos
	@FXML
	private Label entityNameLabel;

	// Buy
	@FXML
	private Label buyCostLabel;
	@FXML
	private TextField buyAmountField;

	// Upgrade
	@FXML
	private Label upgradeCostLabel;
	@FXML
	private Label upgradeBeforeLabel;
	@FXML
	private Button upgradeButton;
	@FXML
	private Label upgradeAfterLabel;
	@FXML
	private ImageView upgradeBImage;
	@FXML
	private ImageView upgradeAImage;
	@FXML
	private TextFlow upgradeDescText;
	@FXML
	private HBox chooseUpgradeBox;

	public IDCardController(LivingEntityType type, LevelController main) {
		this.type = type;
		this.main = main;
		this.costProperty = new SimpleIntegerProperty();
		this.costProperty.addListener((obs, o, n) -> {
			this.buyCostLabel.setText("Coût : " + this.costProperty.get());
			checkCost();
		});
	}

	// OnMouse event
	@FXML
	void buyTwoMouseEntered(MouseEvent event) {
		updateCost(2);
	}

	@FXML
	void buyTwoMouseExited(MouseEvent event) {
		updateCost(1);
	}

	@FXML
	void buyFiveMouseEntered(MouseEvent event) {
		updateCost(5);
	}

	@FXML
	void buyFiveMouseExited(MouseEvent event) {
		updateCost(1);
	}

	@FXML
	void buyNMouseEntered(MouseEvent event) {
		updateCost(parseInt(buyAmountField.getText(), 0, 50));
	}

	@FXML
	void buyNMouseExited(MouseEvent event) {
		updateCost(1);
	}

	// Buy actions
    @FXML
    void buyOneAction(ActionEvent event) {
        if (buyEntity(1)) {
        	this.main.getLvl().getInv().addEntity(type);
        	this.main.leftPane.getSelectionModel().select(this.main.inventoryTab);
        }
    }

    @FXML
    void buyTwoAction(ActionEvent event) {
        if (buyEntity(2)) {
        	this.main.getLvl().getInv().addEntity(type, 2);
        	this.main.leftPane.getSelectionModel().select(this.main.inventoryTab);
        	
        }
    }

    @FXML
    void buyFiveAction(ActionEvent event) {
    	if (buyEntity(5)) {
    		this.main.getLvl().getInv().addEntity(type, 5);
    		this.main.leftPane.getSelectionModel().select(this.main.inventoryTab);
    	}
    }

    @FXML
    void buyNAction(ActionEvent event) {
    	int n = parseInt(this.buyAmountField.getText(), 0, 50);
    	updateCost(n);
    	if (n != 0 && buyEntity(n)) {
    		this.main.getLvl().getInv().addEntity(type, n);
    		this.main.leftPane.getSelectionModel().select(this.main.inventoryTab);
    	}
    	this.buyAmountField.clear();
    }
    
    public boolean buyEntity(int quantity) {
    	return this.main.getLvl().debit((int) (type.getData().getStats().get(Attribute.COST) * quantity));
    }

	// Upgrade actions
	@FXML
	private void upgradeAction(ActionEvent event) {
		EntityData data = this.type.getData();
		Tier next = data.getTier().next();
		if (this.main.getLvl().debit((int) data.getStats(next).get(Attribute.UNLOCK))) {
			data.setTier(next);
			this.main.manageStats(data, null);
			updateUpgradeLabel();
			updateCost(1);
		}
		if (data.getTier() == Tier.TIER_3) {
			this.chooseUpgradeBox.setVisible(true);
		} else {
			this.chooseUpgradeBox.setVisible(false);
		}
	}

	@FXML
	private void upgradeAMouseClicked(MouseEvent event) {
		if (! this.main.upgradeVBox.isVisible()) {
			this.selectedImage = 0;
			this.main.upgradeNameLabel.setText(null);
			this.main.upgradeImage.setImage(this.upgradeAImage.getImage());
			this.main.upgradeVBox.setVisible(true);
			this.main.unlockUpgradeButton.setDisable(false);

			if (this.type.getData().getTierSpecification() == Tier.Specification.DEFAULT)
				this.main.unlockUpgradeButton.setOnMouseClicked(c -> {
					this.type.getData().setTierSpecification(Tier.Specification.A);
					this.main.unlockUpgradeButton.setDisable(true);
				});
			else
				this.main.unlockUpgradeButton.setDisable(true);
		}
	}

	@FXML
	private void upgradeAMouseEntered(MouseEvent event) {
		if(! this.main.upgradeVBox.isVisible()) {
			manageImage(52, 32);
			Tooltip.install(this.upgradeAImage, new Tooltip(this.type.getData().getName() + " upgrade A"));
		}
	}

	@FXML
	private void upgradeAMouseExited(MouseEvent event) {
		restoreSelected();
	}

	@FXML
	private void upgradeBMouseClicked(MouseEvent event) {
		if (! this.main.upgradeVBox.isVisible()) {
			this.selectedImage = 1;
			this.main.upgradeNameLabel.setText(null);
			this.main.upgradeImage.setImage(this.upgradeBImage.getImage());
			this.main.upgradeVBox.setVisible(true);
			this.main.unlockUpgradeButton.setDisable(false);

			if (this.type.getData().getTierSpecification() == Tier.Specification.DEFAULT)
				this.main.unlockUpgradeButton.setOnMouseClicked(c -> {
					this.type.getData().setTierSpecification(Tier.Specification.B);
					this.main.unlockUpgradeButton.setDisable(true);
				});
			else
				this.main.unlockUpgradeButton.setDisable(true);
		}
	}

	@FXML
	private void upgradeBMouseEntered(MouseEvent event) {
		if (! this.main.upgradeVBox.isVisible()) {
			manageImage(32, 52);
			Tooltip.install(this.upgradeBImage, new Tooltip(this.type.getData().getName() + " upgrade B"));
		}
	}

	@FXML
	private void upgradeBMouseExited(MouseEvent event) {
		restoreSelected();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.selectedImage = -1;
		this.entityNameLabel.setText(this.type.getData().getName());
		updateCost(1);

		this.chooseUpgradeBox.managedProperty().bind(this.chooseUpgradeBox.visibleProperty());
		this.chooseUpgradeBox.setVisible(false);

		this.buyAmountField.textProperty()
		.addListener((obs, oldValue, newValue) -> updateCost(parseInt(this.buyAmountField.getText(), 0, 50)));

		this.main.getLvl().purseProperty().addListener((obs, oldVal, newVal) -> {
			checkCost();
		});
		updateUpgradeLabel();
		String path = this.type.getData().getPath();
		String name = path.substring(path.indexOf('.') + 1);
		this.upgradeAImage.setImage(new Image(Controller.imgPath("models", name + "_a.png")));
		this.upgradeBImage.setImage(new Image(Controller.imgPath("models", name + "_b.png")));
		this.main.upgradeVBox.visibleProperty().addListener((obs, o, n) -> {
			if(!n.booleanValue())
				this.selectedImage = -1;
		});
	}

	public static int parseInt(String str, int def, int min, int max) {
		int n;
		try {
			n = Integer.parseInt(str);
			if (n < min || n > max)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			n = def;
		}
		return n;
	}

	public static int parseInt(String str, int def) {
		return parseInt(str, def, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public static int parseInt(String str, int min, int max) {
		return parseInt(str, 0, min, max);
	}

	public static int parseInt(String str) {
		return parseInt(str, 0);
	}

	private void updateCost(int n) {
		this.costProperty.set((int) (this.type.getData().getStats().get(Attribute.COST) * n));
	}

	public void checkCost() {
		if (this.main.getLvl().getPurse() < this.costProperty.get())
			this.buyCostLabel.setTextFill(Color.RED);
		else
			this.buyCostLabel.setTextFill(Color.BLACK);
	}

	public void updateUpgradeLabel() {
		EntityData data = this.type.getData();
		Tier next = data.getTier().next();
		if (next == null) {
			this.upgradeCostLabel.setText("Niveau max");
			this.upgradeBeforeLabel.setText("");
			this.upgradeAfterLabel.setText("");
			this.upgradeButton.setText("Tier 3");
			this.upgradeButton.setDisable(true);
		} else {
			this.upgradeBeforeLabel.setText(data.getTier().toString());
			this.upgradeAfterLabel.setText(next + "");
			this.upgradeCostLabel.setText("Coût : " + data.getStats(next).get(Attribute.UNLOCK));
		}
	}

	private void manageImage(double dimA,double dimB) {
		this.upgradeAImage.setFitHeight(dimA);
		this.upgradeAImage.setFitWidth(dimA);
		this.upgradeBImage.setFitHeight(dimB);
		this.upgradeBImage.setFitWidth(dimB);
		if(dimA < dimB) {
			this.upgradeAImage.setOpacity(0.5);
			this.upgradeBImage.setOpacity(1);
		}
		else {
			this.upgradeBImage.setOpacity(0.5);
			this.upgradeAImage.setOpacity(1);
		}
	}

	private void manageImageDefault() {
		manageImage(48, 48);
		this.upgradeBImage.setOpacity(1);
		this.upgradeAImage.setOpacity(1);
	}

	private void restoreSelected() {
		switch(this.selectedImage) {
		case 0:
			manageImage(52, 32);
			break;
		case 1:
			manageImage(32, 52);
			break;
		default:
			manageImageDefault();
		}
	}
}