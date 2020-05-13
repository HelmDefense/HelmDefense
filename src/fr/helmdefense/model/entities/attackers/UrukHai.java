package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.Location;

public class UrukHai extends Attacker {

	public UrukHai(Location loc) {
		super(loc);	
	}
	
	public UrukHai(int x, int y) {
		this(new Location(x, y));
	}

	
}
