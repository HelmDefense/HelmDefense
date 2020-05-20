package fr.helmdefense.model.entities.utils.coords;

public class Vector implements Coords {
	private double x;
	private double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Coords start, Coords end) {
		this(end.getX() - start.getX(), end.getY() - start.getY());
	}
	
	public Vector(Coords coords) {
		this(coords.getX(), coords.getY());
	}
	
	public Vector add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector add(Vector vect) {
		return this.add(vect.getX(), vect.getY());
	}
	
	public Vector subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector subtract(Vector vect) {
		return this.subtract(vect.getX(), vect.getY());
	}
	
	public Vector multiply(double factor) {
		this.x *= factor;
		this.y *= factor;
		return this;
	}
	
	public Vector divide(double factor) {
		this.x /= factor;
		this.y /= factor;
		return this;
	}
	
	public Vector negate() {
		return this.multiply(-1);
	}
	
	public Vector copy() {
		return new Vector(this);
	}
	
	public double length() {
		return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
	}
	
	public Location toLocation() {
		return new Location(this);
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}
}