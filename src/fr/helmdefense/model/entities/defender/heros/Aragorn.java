package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.utils.Location;

public class Aragorn extends Heros {
	
	public Aragorn(Location loc) {
		super(loc, "aragorn");
	}
	
	public Aragorn(int x, int y) {
		super(new Location(x, y), "aragorn");
	}
}
