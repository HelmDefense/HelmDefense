package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.attackers.Attacker;
import fr.helmdefense.model.entities.utils.coords.Location;

public  abstract class Boss extends Attacker {
	
	public Boss(Location location) {
		super(location);
	}
	
	public Boss(double x, double y) {
		this(new Location(x, y));
	}
	
	

}
