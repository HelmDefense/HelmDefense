package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.coords.Location;

public abstract class TwoTowers extends Boss {

	public TwoTowers(Location location) {
		super(location);
	}
	
	public TwoTowers(double x, double y) {
		this(new Location(x, y));
	}
}

