package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public abstract class Defender extends Entity {
	
	public Defender(Location loc) {
		super(loc);
	}
	
	public Defender(int x, int y) {
		this(new Location(x,y));
	}
}
