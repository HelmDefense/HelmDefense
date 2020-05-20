package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.coords.Location;

public abstract class Attacker extends Entity {

	public Attacker(Location loc) {
		super(loc);
	}

	public Attacker(double x, double y) {
		this(new Location(x, y));
	}

}
