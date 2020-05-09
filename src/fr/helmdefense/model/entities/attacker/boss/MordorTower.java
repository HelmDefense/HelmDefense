package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.utils.Location;

public class MordorTower extends Boss {

	public MordorTower(int x, int y) {
		super(new Location(x, y), "mordortower");

	}

	public MordorTower(Location location) {
		super(location, "mordortower");

	}

	

}
