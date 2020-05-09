package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.attacker.Attacker;
import fr.helmdefense.model.entities.utils.Location;

public  abstract class Bosses extends Attacker {
	
	public Bosses(Location location, String name) {
		super(location, "boss." + name);
	}
	
	public Bosses(int x, int y, String name) {
		this(new Location(x, y), name);
	}
	
	

}
