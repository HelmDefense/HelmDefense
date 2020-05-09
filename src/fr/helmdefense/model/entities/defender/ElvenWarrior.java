package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.utils.Location;

public class ElvenWarrior extends Defender {

	
	public ElvenWarrior(Location loc) {
		super(loc, "elven");
	}
	
	public ElvenWarrior(int x, int y) {
		this(new Location(x,y));
	}

}
