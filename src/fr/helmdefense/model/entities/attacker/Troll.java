package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class Troll extends Attacker {

	public Troll(int x, int y) {
		super(new Location(x, y), "troll");
	}

	public Troll(Location loc) {
		super(loc, "troll");
	}
}
