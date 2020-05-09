package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class UrukHai extends Attacker {

	public UrukHai(Location loc) {
		super(loc, "uruk-hai");	
	}
	
	public UrukHai(int x, int y) {
		this(new Location(x, y));
	}

	
}
