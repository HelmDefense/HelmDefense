package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.utils.Location;

public class Aragorn extends Heros {
	
	public Aragorn(Location loc) {
		super(loc, "aragorn");
	}
	
	public Aragorn(int x, int y) {
		this(new Location(x, y));
	}
}
