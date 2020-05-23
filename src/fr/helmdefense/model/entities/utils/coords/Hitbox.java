package fr.helmdefense.model.entities.utils.coords;

public class Hitbox implements Coords {
	private Location center;
	private double width;
	private double height;
	
	public Hitbox(Location center, double width, double height) {
		this.center = center;
		this.width = width;
		this.height = height;
	}
	
	public Hitbox(double minX, double minY, double maxX, double maxY) {
		this(new Location((minX + maxX) / 2, (minY + maxY) / 2), maxX - minX, maxY - minY);
	}
	
	public Hitbox(Coords min, Coords max) {
		this(min.getX(), min.getY(), max.getX(), max.getY());
	}
	
	public boolean overlaps(Hitbox other) {
		return this.minX() < other.maxX() && this.maxX() > other.minX()
				&& this.minY() < other.maxY() && this.maxY() > other.minY();
	}
	
	public boolean contains(double x, double y) {
		return x >= this.minX() && x < this.maxX()
                && y >= this.minY() && y < this.maxY();
	}
	
	public boolean contains(Coords coords) {
		return this.contains(coords.getX(), coords.getY());
	}
	
	public double getVolume() {
		return this.width * this.height;
	}
	
	public Location getCenter() {
		return this.center;
	}
	
	@Override
	public double getX() {
		return this.center.getX();
	}

	@Override
	public double getY() {
		return this.center.getY();
	}
	
	public double minX() {
		return this.center.getX() - this.width / 2;
	}
	
	public double minY() {
		return this.center.getY() - this.height / 2;
	}
	
	public double maxX() {
		return this.center.getX() + this.width / 2;
	}
	
	public double maxY() {
		return this.center.getY() + this.height / 2;
	}
	
	public void setCenter(Location center, boolean replace) {
		if (replace)
			this.center = center;
		else
			this.center.setX(center.getX()).setY(center.getY());
	}
	
	public void setCenter(Location center) {
		this.setCenter(center, false);
	}
	
	public void setCenter(double x, double y, boolean replace) {
		if (replace)
			this.center = new Location(x, y);
		else
			this.center.setX(x).setY(y);
	}
	
	public void setCenter(double x, double y) {
		this.setCenter(x, y, false);
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public boolean equals(Hitbox other) {
		return this.center.equals(other.getCenter())
				&& this.width == other.getWidth()
				&& this.height == other.getHeight();
	}

	@Override
	public String toString() {
		return "Hitbox [center=" + center + ", width=" + width + ", height=" + height + "]";
	}
}