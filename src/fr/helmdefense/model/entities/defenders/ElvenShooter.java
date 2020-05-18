package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.coords.Location;

public class ElvenShooter extends Elven {

	public ElvenShooter(Location loc) {
		super(loc);
	}
	
	public ElvenShooter(double x, double y) {
		this(new Location(x,y));
	}

}
