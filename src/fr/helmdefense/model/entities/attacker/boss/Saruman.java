package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public class Saruman extends Entity {

	public Saruman(int x, int y) {
		super(new Location(x, y), "saruman");
	}

	public Saruman(Location loc) {
		super(loc, "saruman");
	}

	

}
