package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.coords.Location;

public class OrcBomber extends Attacker {

	public OrcBomber(Location loc) {
		super(loc);
	}
	
	public OrcBomber(double x, double y) {
		this(new Location(x, y));
	}
}