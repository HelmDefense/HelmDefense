package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Location;

public abstract class Hero extends Defender {

	public Hero(int x, int y) {
		this(new Location(x, y));
	}
	
	public Hero(Location loc) {
		super(loc);
	}

}
