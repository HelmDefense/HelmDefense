package fr.helmdefense.model.entities.utils.coords;

public class Hitbox implements Coords {
	private Location center;
	private Size size;
	private boolean locationLocked;
	
	public Hitbox(Location center, Size size) {
		this.center = center;
		this.size = size;
		this.locationLocked = false;
	}
	
	public Hitbox(Location center, double width, double height) {
		this(center, new Size(width, height));
	}
	
	public Hitbox(double minX, double minY, double maxX, double maxY) {
		this(new Location((minX + maxX) / 2, (minY + maxY) / 2), maxX - minX, maxY - minY);
	}
	
	public Hitbox(Coords min, Coords max) {
		this(min.getX(), min.getY(), max.getX(), max.getY());
	}
	
	public void lockLocation() {
		this.locationLocked = true;
	}
	
	public boolean overlaps(Hitbox other) {
		return this.minX() < other.maxX() && this.maxX() > other.minX()
				&& this.minY() < other.maxY() && this.maxY() > other.minY();
	}
	
	public boolean contains(double x, double y) {
		return x >= this.minX() && x < this.maxX() && y >= this.minY() && y < this.maxY();
	}
	
	public boolean contains(Coords coords) {
		return this.contains(coords.getX(), coords.getY());
	}
	
	public Location getCenter() {
		return this.locationLocked ? this.center.copy() : this.center;
	}
	
	public boolean isLocationLocked() {
		return this.locationLocked;
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
		return this.center.getX() - this.size.getWidth() / 2;
	}
	
	public double minY() {
		return this.center.getY() - this.size.getHeight() / 2;
	}
	
	public double maxX() {
		return this.center.getX() + this.size.getWidth() / 2;
	}
	
	public double maxY() {
		return this.center.getY() + this.size.getHeight() / 2;
	}
	
	public Size getSize() {
		return this.size;
	}
	
	public boolean equals(Hitbox other) {
		return this.center.equals(other.getCenter()) && this.size.equals(other.getSize());
	}

	@Override
	public String toString() {
		return "Hitbox [center=" + center + ", size=" + size + "]";
	}
	
	public static class Size {
		private double width;
		private double height;
		
		public Size(double width, double height) {
			this.width = width;
			this.height = height;
		}
		
		public double getVolume() {
			return this.width * this.height;
		}

		public double getWidth() {
			return this.width;
		}
		
		public double getHeight() {
			return this.height;
		}
		
		public boolean equals(Size other) {
			return this.width == other.getWidth()
					&& this.height == other.getHeight();
		}

		@Override
		public String toString() {
			return "Size [width=" + width + ", height=" + height + "]";
		}
	}
}