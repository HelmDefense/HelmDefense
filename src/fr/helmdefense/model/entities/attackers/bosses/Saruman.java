package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public class Saruman extends Entity {

	public Saruman(Location loc) {
		super(loc, "saruman");
	}
	
	public Saruman(int x, int y) {
		this(new Location(x, y));
	}
}
