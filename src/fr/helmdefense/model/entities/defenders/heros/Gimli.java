package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.utils.Location;

public class Gimli extends Hero {
	
	public Gimli(Location loc) {
		super(loc);
	}
	
	public Gimli(int x, int y) {
		this(new Location(x, y));
	}
}
