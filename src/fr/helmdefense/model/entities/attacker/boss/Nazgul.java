package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class Nazgul extends Boss {

	public Nazgul(Location location) {
		super(location, "nazgul");
	}	
	
	public Nazgul(int x, int y) {
		this(new Location(x, y));
	}
}



