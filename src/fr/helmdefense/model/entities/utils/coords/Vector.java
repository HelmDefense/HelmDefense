package fr.helmdefense.model.entities.utils.coords;

public class Vector implements Coords {
	private double x;
	private double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Location start, Location end) {
		this(end.getX() - start.getX(), end.getY() - start.getY());
	}
	
	public Vector multiply(double factor) {
		this.x *= factor;
		this.y *= factor;
		return this;
	}
	
	public double length() {
		return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}
}