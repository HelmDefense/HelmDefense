package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class Grond extends Boss {

	public Grond(int x, int y) {
		super(new Location(x,y), "grond");
	}

	public Grond(Location location) {
		super(location, "grond");
	}


}
