package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.attackers.Attacker;
import fr.helmdefense.model.entities.utils.Location;

public  abstract class Boss extends Attacker {
	
	public Boss(Location location) {
		super(location);
	}
	
	public Boss(int x, int y) {
		this(new Location(x, y));
	}
	
	

}
