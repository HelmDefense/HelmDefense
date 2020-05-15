package fr.helmdefense.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.Tier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;

public class IDCardController implements Initializable {
	private Class<? extends Entity> type;
	private Controller main;
	
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
    private Label upgradeAfterLabel;
    @FXML
    private TextFlow upgradeDescText;
	@FXML
    private HBox chooseUpgradeBox;

	public IDCardController(Class<? extends Entity> type, Controller main) {
		this.type = type;
		this.main = main;
	}
	
	//OnMouse event
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
    	this.main.getLvl().getInv().addEntity(type);
    }

    @FXML
    void buyTwoAction(ActionEvent event) {
        this.main.getLvl().getInv().addEntity(type, 2);
    }

    @FXML
    void buyFiveAction(ActionEvent event) {
        this.main.getLvl().getInv().addEntity(type, 5);
    }

    @FXML
    void buyNAction(ActionEvent event) {
    	int n = parseInt(buyAmountField.getText(), 0, 50);
    	updateCost(n);
        this.main.getLvl().getInv().addEntity(type, n);
    	this.buyAmountField.clear();
    }

    // Upgrade actions
    @FXML
    void upgradeAction(ActionEvent event) {

    }

    @FXML
    void tierAAction(ActionEvent event) {

    }

    @FXML
    void tierBAction(ActionEvent event) {

    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.entityNameLabel.setText(type.getSimpleName());
		updateCost(1);
		this.chooseUpgradeBox.managedProperty().bind(chooseUpgradeBox.visibleProperty());
		chooseUpgradeBox.setVisible(false);
    	buyAmountField.textProperty().addListener((obs, oldValue, newValue) -> updateCost(parseInt(buyAmountField.getText(), 0, 50)));
	}
	
	public static int parseInt(String str, int def, int min, int max) {
		int n;
		try {
			n = Integer.parseInt(str);
			if(n < min || n > max)
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
		this.buyCostLabel.setText("Coût : " + Integer.toString(Entities.getData(type).getStats(Tier.TIER_1).getCost() * n));
	}
}