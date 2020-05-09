package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.attacker.Attacker;
import fr.helmdefense.model.entities.utils.Location;

public class Boss extends Attacker {

	public Boss(int x, int y, String name) {
		super(x, y, "boss." + name);
	}
	
	public Boss(Location location, String name) {
		super(location, "boss." + name);
	}

}
