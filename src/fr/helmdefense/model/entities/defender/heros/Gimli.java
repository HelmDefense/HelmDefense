package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.utils.Location;

public class Gimli extends Heros {
	
	public Gimli(Location loc) {
		super(loc, "gimli");
	}
	
	public Gimli(int x, int y) {
		this(new Location(x, y));
	}
}
