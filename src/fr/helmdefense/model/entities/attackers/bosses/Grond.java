package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Grond extends Boss {

	public Grond(Location location) {
		super(location);
	}
	
	public Grond(double x, double y) {
		this(new Location(x,y));
	}


}
