package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.coords.Location;

public class OrcWarrior extends Attacker{

	public OrcWarrior(Location loc) {
		super(loc);
	}
	
	public OrcWarrior(double x, double y) {
		this(new Location(x, y));

	}

}
