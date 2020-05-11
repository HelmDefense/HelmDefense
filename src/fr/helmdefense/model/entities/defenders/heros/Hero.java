package fr.helmdefense.model.entities.defenders.heros;

import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Location;

public abstract class Hero extends Defender {

	public Hero(int x, int y, String name) {
		this(new Location(x, y), name);
	}
	
	public Hero(Location loc, String name) {
		super(loc, "heros." + name);
	}

}
