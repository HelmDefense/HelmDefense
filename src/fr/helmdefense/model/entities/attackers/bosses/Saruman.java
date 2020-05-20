package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Saruman extends Boss {

	public Saruman(Location loc) {
		super(loc);
	}
	
	public Saruman(double x, double y) {
		this(new Location(x, y));
	}
}
