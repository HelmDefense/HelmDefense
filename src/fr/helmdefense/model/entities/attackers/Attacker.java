package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public abstract class Attacker extends Entity {

	public Attacker(Location loc, String name) {
		super(loc, "attackers." + name);
	}

	public Attacker(int x, int y, String name) {
		this(new Location(x, y), name);
	}

}
