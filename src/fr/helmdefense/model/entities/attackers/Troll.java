package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Troll extends Attacker {

	public Troll(Location loc) {
		super(loc);
	}
	
	public Troll(double x, double y) {
		this(new Location(x, y));
	}
}
