package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.Location;

public class Catapult extends Defender {
	
	public Catapult(Location loc) {
		super(loc);
	}
	
	public Catapult(int x, int y) {
		this(new Location(x,y));
	}

}
