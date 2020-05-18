package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.coords.Location;

public abstract class Hero extends Defender {

	public Hero(double x, double y) {
		this(new Location(x, y));
	}
	
	public Hero(Location loc) {
		super(loc);
	}

}
