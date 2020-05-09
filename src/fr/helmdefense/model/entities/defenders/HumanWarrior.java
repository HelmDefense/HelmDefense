package fr.helmdefense.model.entities.defenders;

import fr.helmdefense.model.entities.utils.Location;

public class HumanWarrior extends Defender {
	
	public HumanWarrior(Location loc) {
		super(loc, "human-warrior");
	}
	
	public HumanWarrior(int x, int y) {
		this(new Location(x,y));
	}
	
	

}
