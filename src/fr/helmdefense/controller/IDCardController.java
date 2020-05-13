package fr.helmdefense.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Entities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Button buyTwoButton;
    @FXML
    private Button buyFiveButton;
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
	
	// Buy actions
    @FXML
    void buyOneAction(ActionEvent event) {
    	
    }

    @FXML
    void buyTwoAction(ActionEvent event) {
    	
    }

    @FXML
    void buyFiveAction(ActionEvent event) {

    }

    @FXML
    void buyNAction(ActionEvent event) {

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
		this.buyCostLabel.setText(Integer.valueOf(Entities.getData(type).getCost()).toString());
		//affichage du prix de 2 unité dans le Label "buyCostLabel" en passant sur le bouton x2
		buyTwoButton.setOnMouseEntered(e -> this.buyCostLabel.setText(Integer.valueOf(Entities.getData(type).getCost()*2).toString()));
		buyTwoButton.setOnMouseExited(e -> this.buyCostLabel.setText(Integer.valueOf(Entities.getData(type).getCost()).toString()));
		//affichage du prix de 5 unité dans le Label "buyCostLabel" en passant sur le bouton x5
		buyFiveButton.setOnMouseEntered(e -> this.buyCostLabel.setText(Integer.valueOf(Entities.getData(type).getCost()*5).toString()));
		buyFiveButton.setOnMouseExited(e -> this.buyCostLabel.setText(Integer.valueOf(Entities.getData(type).getCost()).toString()));
	}
}