package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public class Attacker extends Entity {

	public Attacker(Location loc, String name) {
		super(loc, "attacker." + name);
	}

	public Attacker(int x, int y, String name) {
		super(new Location(x, y),"attacker." + name);
	}

}
