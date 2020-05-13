package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.Location;

public abstract class TwoTowers extends Boss {

	public TwoTowers(Location location) {
		super(location);
	}
	
	public TwoTowers(int x, int y) {
		this(new Location(x, y));
	}
}

