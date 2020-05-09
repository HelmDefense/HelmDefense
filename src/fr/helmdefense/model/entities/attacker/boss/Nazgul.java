package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class Nazgul extends Boss {

	public Nazgul(int x, int y) {
		super(x, y, "nazgul");
	}

	public Nazgul(Location location) {
		super(location, "nazgul");
	}	
}



