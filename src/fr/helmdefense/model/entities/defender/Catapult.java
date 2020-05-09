package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Location;

public class Catapult extends Defender {
	
	public Catapult(Location loc) {
		super(loc, "catapult");
	}
	
	public Catapult(int x, int y) {
		super(new Location(x,y), "catapult");
	}

}
