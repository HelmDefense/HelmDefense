package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.utils.Location;

public class OrcBomber extends Attacker {

	public OrcBomber(Location loc) {
		super(loc,"orc-bomber");
	}
	
	public OrcBomber(int x, int y) {
		this(new Location(x, y));
	}
}