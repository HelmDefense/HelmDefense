package fr.helmdefense.model.entities.attacker;

import fr.helmdefense.model.entities.attacker.boss.TwoTowers;
import fr.helmdefense.model.entities.utils.Location;

public class MordorTower extends TwoTowers {

	public MordorTower(int x, int y) {
		super(new Location(x, y), "mordortower");

	}

	public MordorTower(Location location) {
		super(location, "mordortower");
	}

}
