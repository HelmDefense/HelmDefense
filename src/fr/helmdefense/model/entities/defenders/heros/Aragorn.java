package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Aragorn extends Hero {
	
	public Aragorn(Location loc) {
		super(loc);
	}
	
	public Aragorn(double x, double y) {
		this(new Location(x, y));
	}
}
