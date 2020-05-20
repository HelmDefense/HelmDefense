package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Gimli extends Hero {
	
	public Gimli(Location loc) {
		super(loc);
	}
	
	public Gimli(double x, double y) {
		this(new Location(x, y));
	}
}
