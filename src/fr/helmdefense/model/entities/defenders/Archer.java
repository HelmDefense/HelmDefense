package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Archer extends Defender {
	
	public Archer(Location loc) {
		super(loc);
	}
	
	public Archer(double x, double y) {
		this(new Location(x,y));
	}


}
