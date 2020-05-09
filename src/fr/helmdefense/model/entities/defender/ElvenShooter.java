package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Location;

public class ElvenShooter extends Defender {

	public ElvenShooter(Location loc) {
		super(loc, "elven");
	}
	
	public ElvenShooter(int x, int y) {
		super(new Location(x,y), "elven");
	}

}
