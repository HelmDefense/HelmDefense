package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.defender.Defender;
import fr.helmdefense.model.entities.utils.Location;


public class Legolas extends Defender {
	
	public Legolas(Location loc) {
		super(loc, "legolas");
	}
	
	public Legolas(int x, int y) {
		super(new Location(x, y), "legolas");
	}
}
