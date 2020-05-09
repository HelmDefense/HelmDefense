package fr.helmdefense.model.entities.attackers.bosses;

import fr.helmdefense.model.entities.utils.Location;

public class MordorTower extends TwoTowers {

	public MordorTower(Location location) {
		super(location, "mordor-tower");
	}
	
	public MordorTower(int x, int y) {
		this(new Location(x, y));
	}
}
