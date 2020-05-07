package fr.helmdefense.model.entities.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Location {
	private IntegerProperty xProperty;
	private IntegerProperty yProperty;
	
	public Location(int x, int y) {
		this.xProperty = new SimpleIntegerProperty(x);
		this.yProperty = new SimpleIntegerProperty(y);
	}
	
	public Location copy() {
		return new Location(this.getX(), this.getY());
	}

	public final int getX() {
		return this.xProperty.get();
	}

	public final int getY() {
		return this.yProperty.get();
	}
	
	public final void setX(int x) {
		this.xProperty.set(x);
	}
	
	public final void setY(int y) {
		this.yProperty.set(y);
	}
	
	public final IntegerProperty getXProperty() {
		return this.xProperty;
	}
	
	public final IntegerProperty getYProperty() {
		return this.yProperty;
	}
}