package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.utils.Location;


public class Legolas extends Heros {
	
	public Legolas(Location loc) {
		super(loc, "legolas");
	}
	
	public Legolas(int x, int y) {
		this(new Location(x, y));
	}
}
