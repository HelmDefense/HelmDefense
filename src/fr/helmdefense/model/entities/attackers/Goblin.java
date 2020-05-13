package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.Location;

public class Goblin extends Attacker {

	public Goblin(Location loc) {
		super(loc);
	}
	
	public Goblin(int x, int y) {
		this(new Location(x, y));
	}
}
