package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class TwoTowers extends Boss {

	public TwoTowers(int x, int y, String name) {
		super(new Location(x, y), "twotowers." + name);
		
	}

	public TwoTowers(Location location, String name) {
		super(location, "twotowers." + name);
		
	}

	
}

