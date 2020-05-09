package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.Location;

public class Troll extends Attacker {

	public Troll(Location loc) {
		super(loc, "troll");
	}
	
	public Troll(int x, int y) {
		this(new Location(x, y));
	}
}
