package fr.helmdefense.model.entities.utils;

import fr.helmdefense.model.map.GameMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Location {
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	
	public Location(double x, double y) {
		this.xProperty = new SimpleDoubleProperty(x);
		this.yProperty = new SimpleDoubleProperty(y);
	}
	
	public Location add(double x, double y) {
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
		return this;
	}
	
	public Location add(Location loc) {
		return this.add(loc.getX(), loc.getY());
	}
	
	public Location subtract(double x, double y) {
		this.setX(this.getX() - x);
		this.setY(this.getY() - y);
		return this;
	}
	
	public Location subtract(Location loc) {
		return this.subtract(loc.getX(), loc.getY());
	}
	
	public Location multiply(double factor) {
		this.setX(this.getX() * factor);
		this.setY(this.getY() * factor);
		return this;
	}
	
	public Location zero() {
		this.setX(0);
		this.setY(0);
		return this;
	}
	
	public Location copy() {
		return new Location(this.getX(), this.getY());
	}
	
	public boolean isInMap() {
		return this.getX() >= 0 && this.getX() < GameMap.WIDTH && this.getY() >= 0 && this.getY() < GameMap.HEIGHT;
	}

	public final double getX() {
		return this.xProperty.get();
	}

	public final double getY() {
		return this.yProperty.get();
	}

	public final double getPixelX() {
		return this.xProperty.get() * GameMap.TILE_SIZE;
	}

	public final double getPixelY() {
		return this.yProperty.get() * GameMap.TILE_SIZE;
	}
	
	public final Location setX(double x) {
		this.xProperty.set(x);
		return this;
	}
	
	public final Location setY(double y) {
		this.yProperty.set(y);
		return this;
	}
	
	public final DoubleProperty xProperty() {
		return this.xProperty;
	}
	
	public final DoubleProperty yProperty() {
		return this.yProperty;
	}
	
	public boolean equals(Location loc) {
		return this.getX() == loc.getX() && this.getY() == loc.getY();
	}

	@Override
	public String toString() {
		return "Location [xProperty=" + xProperty + ", yProperty=" + yProperty + "]";
	}
}