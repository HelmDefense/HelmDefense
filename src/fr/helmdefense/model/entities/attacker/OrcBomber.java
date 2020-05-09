package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class OrcBomber extends Attacker {

	public OrcBomber(int x, int y) {
		super(new Location(x, y), "orc-bomber");
	}

	public OrcBomber(Location loc) {
		super(loc,"orc-bomber");
	}
}
