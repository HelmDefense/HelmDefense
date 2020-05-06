package fr.helmdefense.model.entities.defender.heros;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Statistic;

public class Aragorn extends Entity {

	public Aragorn(int x, int y, Statistic stats) {
		super(x, y, stats);
	}
	
	public Aragorn(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		super(x, y, hp, dmg, mvtSpd, atkSpd, atkRange, shootRange);
	}
}
