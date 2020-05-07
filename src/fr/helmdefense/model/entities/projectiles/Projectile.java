package fr.helmdefense.model.entities.projectiles;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;
import fr.helmdefense.model.entities.utils.Statistic;

public class Projectile extends Entity {
	public Projectile(Location loc, Statistic stats) {
		super(loc, stats);
	}
}