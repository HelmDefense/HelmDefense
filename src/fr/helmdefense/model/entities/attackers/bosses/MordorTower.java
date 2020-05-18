package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.coords.Location;

public class MordorTower extends TwoTowers {

	public MordorTower(Location location) {
		super(location);
	}
	
	public MordorTower(double x, double y) {
		this(new Location(x, y));
	}
}
