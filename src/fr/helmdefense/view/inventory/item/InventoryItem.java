package fr.helmdefense.view.inventory.item;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class InventoryItem extends StackPane implements Initializable {
	private StringProperty imgProperty;
	private IntegerProperty amountProperty;
	
	@FXML
	private ImageView imageView;
	@FXML
	private Label label;
	
	public InventoryItem(String img, int amount) {
		this.imgProperty = new SimpleStringProperty();
		this.amountProperty = new SimpleIntegerProperty();

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("InventoryItem.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		this.setImg(img);
		this.setAmount(amount);
	}
	
	public InventoryItem() {
		this("", 0);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.managedProperty().bind(this.visibleProperty());
		this.label.textProperty().bind(this.amountProperty.asString());
		this.imgProperty.addListener((obs, o, n) -> {
			this.imageView.setImage(new Image(Paths.get(
					System.getProperty("user.dir"),
					"assets",
					"entities",
					this.getImg().replace('.', File.separatorChar) + ".png"
			).toUri().toString()));
		});
	}
	
	public final String getImg() {
		return this.imgProperty.get();
	}
	
	public final void setImg(String img) {
		this.imgProperty.set(img);
	}
	
	public final StringProperty imgProperty() {
		return this.imgProperty;
	}
	
	public final int getAmount() {
		return this.amountProperty.get();
	}
	
	public final void setAmount(int amount) {
		this.amountProperty.set(amount);
	}
	
	public final IntegerProperty amountProperty() {
		return this.amountProperty;
	}
}