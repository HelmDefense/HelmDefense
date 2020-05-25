package fr.helmdefense.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Tier;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

public class IDCardController implements Initializable {
	private Class<? extends Entity> type;
	private Controller main;
	private IntegerProperty costProperty;
	
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
		this.costProperty = new SimpleIntegerProperty();
		this.costProperty.addListener((obs, o, n) -> {
			this.buyCostLabel.setText("Coût : " + this.costProperty.get());
			checkCost();
		});
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
        if (buyEntity(1))
        	this.main.getLvl().getInv().addEntity(type);
    }

    @FXML
    void buyTwoAction(ActionEvent event) {
        if (buyEntity(2))
        	this.main.getLvl().getInv().addEntity(type, 2);
    }

    @FXML
    void buyFiveAction(ActionEvent event) {
    	if ( buyEntity(5))
    		this.main.getLvl().getInv().addEntity(type, 5);
    }

    @FXML
    void buyNAction(ActionEvent event) {
    	int n = parseInt(this.buyAmountField.getText(), 0, 50);
    	updateCost(n);
    	if ( buyEntity(n))
    		this.main.getLvl().getInv().addEntity(type, n);
    	this.buyAmountField.clear();
    }
    
    public boolean buyEntity(int quantity) {
    	return this.main.getLvl().debit(Entities.getData(type).getStats().getCost() * quantity);	
    }

    // Upgrade actions
    @FXML
    void upgradeAction(ActionEvent event) {
    	EntityData data = Entities.getData(this.type);
    	Tier next = data.getTier().next();
    	if (this.main.getLvl().debit(data.getStats(next).getUnlock())) {
    		data.setTier(next);
    	}
		updateUpgradeLabel();
    }

    @FXML
    void tierAAction(ActionEvent event) {

    }

    @FXML
    void tierBAction(ActionEvent event) {

    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.entityNameLabel.setText(Entities.getData(this.type).getName());
		updateCost(1);
		
		this.chooseUpgradeBox.managedProperty().bind(chooseUpgradeBox.visibleProperty());
		chooseUpgradeBox.setVisible(false);
		
    	buyAmountField.textProperty().addListener((obs, oldValue, newValue) -> updateCost(parseInt(buyAmountField.getText(), 0, 50)));
    	
    	this.main.getLvl().purseProperty().addListener((obs, oldVal, newVal) -> {
    		checkCost();
    	});
    	updateUpgradeLabel();
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
		this.costProperty.set(Entities.getData(type).getStats().getCost() * n);
	}
	
	public void checkCost() {
		if ( this.main.getLvl().getPurse() < this.costProperty.get())
			this.buyCostLabel.setTextFill(Color.RED);
		else 
			this.buyCostLabel.setTextFill(Color.BLACK);
	}
	
	public void updateUpgradeLabel() {
		this.upgradeBeforeLabel.setText(Entities.getData(type).getTier().toString());
    	this.upgradeAfterLabel.setText(Entities.getData(type).getTier().next() + "");
	}
}