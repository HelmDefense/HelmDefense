package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class IsengardTower extends TwoTowers {
	
	public IsengardTower(Location location) {
		super(location, "isengard-tower");
	}
	
	public IsengardTower(int x, int y) {
		this(new Location(x, y));
	}

	
	
	
}
