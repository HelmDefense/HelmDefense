package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class OrcWarrior extends Attacker{
	
	public OrcWarrior(int x, int y) {
		super(new Location(x, y), "orc-warrior");

	}

	public OrcWarrior(Location loc) {
		super(loc, "orc-warrior");
	}

}
