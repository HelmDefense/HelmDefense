package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.Location;

public abstract class Elven extends Defender {

	public Elven(Location loc) {
		super(loc, "elven");
	}
	
	public Elven(int x, int y) {
		this(new Location(x,y));
	}
}
