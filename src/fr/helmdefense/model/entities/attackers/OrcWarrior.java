package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.utils.Location;

public class OrcWarrior extends Attacker{

	public OrcWarrior(Location loc) {
		super(loc);
	}
	
	public OrcWarrior(int x, int y) {
		this(new Location(x, y));

	}

}
