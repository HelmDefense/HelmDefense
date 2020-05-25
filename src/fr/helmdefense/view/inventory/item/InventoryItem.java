package fr.helmdefense.view.inventory.item;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import fr.helmdefense.model.entities.Entity;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class InventoryItem extends StackPane implements Initializable, Toggle {
	private StringProperty imgProperty;
	private IntegerProperty amountProperty;
	private ObjectProperty<Class<? extends Entity>> valueProperty;
	private ObjectProperty<ToggleGroup> toggleGroupProperty;
	private BooleanProperty selectedProperty;
	
	@FXML
	private ImageView imageView;
	@FXML
	private Label label;
	@FXML
	private ImageView checkMark;
	
	public InventoryItem(String img, int amount) {
		this.imgProperty = new SimpleStringProperty();
		this.amountProperty = new SimpleIntegerProperty();
		this.valueProperty = new SimpleObjectProperty<Class<? extends Entity>>();
		this.toggleGroupProperty = new SimpleObjectProperty<ToggleGroup>();
		this.selectedProperty = new SimpleBooleanProperty();

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
		
		this.scaleXProperty().bindBidirectional(this.scaleYProperty());
		this.checkMark.visibleProperty().bind(this.selectedProperty);
		this.selectedProperty.addListener((obs, o, n) -> {
			if (n.booleanValue())
				this.setScaleX(0.85);
			else
				this.setScaleX(1);
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
	
	public final Class<? extends Entity> getValue() {
		return this.valueProperty.get();
	}
	
	public final void setValue(Class<? extends Entity> value) {
		this.valueProperty.set(value);
	}
	
	public final ObjectProperty<Class<? extends Entity>> valueProperty() {
		return this.valueProperty;
	}

	@Override
	public ToggleGroup getToggleGroup() {
		return this.toggleGroupProperty.get();
	}

	@Override
	public boolean isSelected() {
		return this.selectedProperty.get();
	}

	@Override
	public BooleanProperty selectedProperty() {
		return this.selectedProperty;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selectedProperty.set(selected);
	}

	@Override
	public void setToggleGroup(ToggleGroup toggleGroup) {
		this.toggleGroupProperty.set(toggleGroup);
	}

	@Override
	public ObjectProperty<ToggleGroup> toggleGroupProperty() {
		return this.toggleGroupProperty;
	}
}