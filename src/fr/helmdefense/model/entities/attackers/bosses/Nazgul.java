package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.Location;

public class Nazgul extends Boss {

	public Nazgul(Location location) {
		super(location);
	}	
	
	public Nazgul(int x, int y) {
		this(new Location(x, y));
	}
}



