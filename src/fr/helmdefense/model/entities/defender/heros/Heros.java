package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Location;

public abstract class Heros extends Defender {

	public Heros(int x, int y, String name) {
		this(new Location(x, y), name);
	}
	
	public Heros(Location loc, String name) {
		super(loc, "heros." + name);
	}

}
