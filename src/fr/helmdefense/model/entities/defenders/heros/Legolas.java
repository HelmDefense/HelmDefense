package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.utils.coords.Location;


public class Legolas extends Hero {
	
	public Legolas(Location loc) {
		super(loc);
	}
	
	public Legolas(double x, double y) {
		this(new Location(x, y));
	}
}
