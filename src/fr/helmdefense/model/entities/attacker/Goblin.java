package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class Goblin extends Attacker {

	public Goblin(Location loc) {
		super(loc, "goblin");
	}
	
	public Goblin(int x, int y) {
		this(new Location(x, y));
	}
}
