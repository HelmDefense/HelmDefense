package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.Location;

public class Grond extends Boss {

	public Grond(Location location) {
		super(location, "grond");
	}
	
	public Grond(int x, int y) {
		this(new Location(x,y));
	}


}
