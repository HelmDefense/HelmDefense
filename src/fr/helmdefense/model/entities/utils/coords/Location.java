package fr.helmdefense.model.entities.utils.coords;

import fr.helmdefense.model.map.GameMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Location implements Coords {
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	
	public Location(Coords coords) {
		this(coords.getX(), coords.getY());
	}
	
	public Location(double x, double y) {
		this.xProperty = new SimpleDoubleProperty(x);
		this.yProperty = new SimpleDoubleProperty(y);
	}
	
	public Location add(double x, double y) {
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
		return this;
	}
	
	public Location add(Coords coords) {
		return this.add(coords.getX(), coords.getY());
	}
	
	public Location subtract(double x, double y) {
		this.setX(this.getX() - x);
		this.setY(this.getY() - y);
		return this;
	}
	
	public Location subtract(Coords coords) {
		return this.subtract(coords.getX(), coords.getY());
	}
	
	public Location multiply(double factor) {
		this.setX(this.getX() * factor);
		this.setY(this.getY() * factor);
		return this;
	}
	
	public Location round() {
		this.setX(this.getRoundX());
		this.setY(this.getRoundY());
		return this;
	}
	
	public Location center() {
		this.setX(this.getCenterX());
		this.setY(this.getCenterY());
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
	
	public double distance(Location loc) {
		return Math.sqrt(Math.pow(this.getX() - loc.getX(), 2) + Math.pow(this.getY() - loc.getY(), 2));
	}
	
	public boolean isInMap() {
		return this.getX() >= 0 && this.getX() < GameMap.WIDTH && this.getY() >= 0 && this.getY() < GameMap.HEIGHT;
	}

	@Override
	public final double getX() {
		return this.xProperty.get();
	}

	@Override
	public final double getY() {
		return this.yProperty.get();
	}

	public final double getPixelX() {
		return this.getX() * GameMap.TILE_SIZE;
	}

	public final double getPixelY() {
		return this.getY() * GameMap.TILE_SIZE;
	}

	public final double getRoundX() {
		return Math.floor(this.getX());
	}

	public final double getRoundY() {
		return Math.floor(this.getY());
	}

	public final double getCenterX() {
		return this.getRoundX() + 0.5;
	}

	public final double getCenterY() {
		return this.getRoundY() + 0.5;
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
	
	public boolean equalsCenter(Location loc) {
		return this.copy().center().equals(loc.copy().center());
	}

	@Override
	public String toString() {
		return "Location [xProperty=" + xProperty + ", yProperty=" + yProperty + "]";
	}
}