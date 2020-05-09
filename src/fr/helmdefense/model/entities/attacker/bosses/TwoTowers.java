package fr.helmdefense.model.entities.attacker.bosses;

import fr.helmdefense.model.entities.utils.Location;

public abstract class TwoTowers extends Boss {

	public TwoTowers(Location location, String name) {
		super(location, name);
	}
	
	public TwoTowers(int x, int y, String name) {
		this(new Location(x, y), name);
	}
}

