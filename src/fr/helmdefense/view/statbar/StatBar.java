package fr.helmdefense.view.statbar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class StatBar extends StackPane implements Initializable {
	private DoubleProperty maxProperty;
	private DoubleProperty valueProperty;
	private StringProperty colorProperty;
	private ObjectProperty<DisplayStyle> displayStyleProperty;
	
	@FXML
	private ProgressBar bar;
	@FXML
	private Label label;
	
	public StatBar(DisplayStyle displayStyle, double max, double value, String color) {
		this.displayStyleProperty = new SimpleObjectProperty<DisplayStyle>(displayStyle);
		this.maxProperty = new SimpleDoubleProperty();
		this.valueProperty = new SimpleDoubleProperty();
		this.colorProperty = new SimpleStringProperty();
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StatBar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        this.setMax(max);
        this.setValue(value);
        this.setColor(color);
	}
	
	public StatBar(double max, double value, String color) {
		this(DisplayStyle.EMPTY, max, value, color);
	}
	
	public StatBar(double max, double value) {
		this(max, value, "#4db8ff");
	}
	
	public StatBar() {
		this(1, 0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.bar.progressProperty().bind(this.valueProperty.divide(this.maxProperty));
		this.bar.prefHeightProperty().bind(this.prefHeightProperty());
		this.bar.prefWidthProperty().bind(this.prefWidthProperty());
		
		ChangeListener<Object> cl = (obs, o, n) -> {
			this.label.setText(this.getDisplayStyle().style(this.getValue(), this.getMax()));
		};
		this.valueProperty.addListener(cl);
		this.maxProperty.addListener(cl);
		this.displayStyleProperty.addListener(cl);
		
		this.colorProperty.addListener((obs, o, n) -> this.bar.setStyle("-fx-accent: " + n));
	}
	
	public final double getValue() {
		return this.valueProperty.get();
	}
	
	public final StatBar setValue(double value) {
		this.valueProperty.set(value);
		return this;
	}
	
	public final DoubleProperty valueProperty() {
		return this.valueProperty;
	}
	
	public final double getMax() {
		return this.maxProperty.get();
	}
	
	public final StatBar setMax(double max) {
		this.maxProperty.set(max);
		return this;
	}
	
	public final DoubleProperty maxProperty() {
		return this.maxProperty;
	}
	
	public final String getColor() {
		return this.colorProperty.get();
	}
	
	public final StatBar setColor(String color) {
		this.colorProperty.set(color);
		return this;
	}
	
	public final StringProperty colorProperty() {
		return this.colorProperty;
	}
	
	public DisplayStyle getDisplayStyle() {
		return this.displayStyleProperty.get();
	}
	
	public StatBar setDisplayStyle(DisplayStyle displayStyle) {
		this.displayStyleProperty.set(displayStyle);
		return this;
	}
	
	public ObjectProperty<DisplayStyle> displayStyleProperty() {
		return this.displayStyleProperty;
	}
	
	public enum DisplayStyle {
		FULL("%vd / %md"),
		FULL_ROUND("%v / %m"),
		VALUE("%vd"),
		VALUE_ROUND("%v"),
		PERCENT("%p %"),
		EMPTY("");
		
		private String style;
		
		private DisplayStyle(String style) {
			this.style = style;
		}
		
		public String style(double value, double max) {
			return this.style
					.replaceAll("%vd", Double.toString(value))
					.replaceAll("%v", Long.toString(Math.round(value)))
					.replaceAll("%md", Double.toString(max))
					.replaceAll("%m", Long.toString(Math.round(max)))
					.replaceAll("%p", Long.toString(Math.round(value / max * 100)));
		}
	}
}