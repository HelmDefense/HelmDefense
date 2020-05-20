package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.coords.Location;

public class ElvenWarrior extends Elven {
	
	public ElvenWarrior(Location loc) {
		super(loc);
	}
	
	public ElvenWarrior(double x, double y) {
		this(new Location(x,y));
	}

}
