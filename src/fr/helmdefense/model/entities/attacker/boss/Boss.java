package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.attacker.Attacker;
import fr.helmdefense.model.entities.utils.Location;

public class Boss extends Attacker {
	
	public Boss(Location location, String name) {
		super(location, "boss." + name);
	}
	
	public Boss(int x, int y, String name) {
		this(new Location(x, y), name);
	}
	
	

}
