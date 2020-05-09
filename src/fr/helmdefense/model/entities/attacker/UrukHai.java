package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class UrukHai extends Attacker {

	public UrukHai(int x, int y) {
		super(new Location(x, y), "urukhai");
	}

	public UrukHai(Location loc) {
		super(loc, "urukhai");
		
	}

	
}
