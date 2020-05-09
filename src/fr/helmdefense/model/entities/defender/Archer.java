package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Location;

public class Archer extends Defender {
	
	public Archer(Location loc) {
		super(loc, "archer");
	}
	
	public Archer(int x, int y) {
		this(new Location(x,y));
	}


}
