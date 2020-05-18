package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Goblin extends Attacker {

	public Goblin(Location loc) {
		super(loc);
	}
	
	public Goblin(double x, double y) {
		this(new Location(x, y));
	}
}
