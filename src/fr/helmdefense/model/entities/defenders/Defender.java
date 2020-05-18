package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.coords.Location;

public abstract class Defender extends Entity {
	
	public Defender(Location loc) {
		super(loc);
	}
	
	public Defender(double x, double y) {
		this(new Location(x,y));
	}
}
