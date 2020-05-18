package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Catapult extends Defender {
	
	public Catapult(Location loc) {
		super(loc);
	}
	
	public Catapult(double x, double y) {
		this(new Location(x,y));
	}

}
