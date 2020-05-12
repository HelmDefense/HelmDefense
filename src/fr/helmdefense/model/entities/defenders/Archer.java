package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.Location;

public class Archer extends Defender {
	
	public Archer(Location loc) {
		super(loc);
	}
	
	public Archer(int x, int y) {
		this(new Location(x,y));
	}


}
