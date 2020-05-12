package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.Location;

public class Saruman extends Boss {

	public Saruman(Location loc) {
		super(loc);
	}
	
	public Saruman(int x, int y) {
		this(new Location(x, y));
	}
}
