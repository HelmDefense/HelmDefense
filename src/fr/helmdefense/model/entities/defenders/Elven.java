package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.coords.Location;

public abstract class Elven extends Defender {

	public Elven(Location loc) {
		super(loc);
	}
	
	public Elven(double x, double y) {
		this(new Location(x,y));
	}
}
