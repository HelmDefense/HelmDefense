package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.coords.Location;

public class HumanWarrior extends Defender {
	public HumanWarrior(Location loc) {
		super(loc);
	}
	
	public HumanWarrior(double x, double y) {
		this(new Location(x,y));
	}
}