package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class OrcWarrior extends Attacker{

	public OrcWarrior(Location loc) {
		super(loc, "orc-warrior");
	}
	
	public OrcWarrior(int x, int y) {
		this(new Location(x, y));

	}

}
