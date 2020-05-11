package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.Location;

public class ElvenShooter extends Elven {

	public ElvenShooter(Location loc) {
		super(loc);
	}
	
	public ElvenShooter(int x, int y) {
		this(new Location(x,y));
	}

}
