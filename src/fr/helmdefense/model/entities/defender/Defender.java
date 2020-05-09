package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public abstract class Defender extends Entity {
	
	public Defender(Location loc, String name) {
		super(loc, "defenders." + name);
	}
	
	public Defender(int x, int y, String name) {
		this(new Location(x,y), "defenders." + name);
	}
}
