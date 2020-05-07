package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Statistic;

public abstract class Defender extends Entity {
	private int cost;

	public Defender(int x, int y, Statistic stats) {
		super(x, y, stats);
		this.cost = 0;
	}
	
	public Defender(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		super(x, y, hp, dmg, mvtSpd, atkSpd, atkRange, shootRange);
		this.cost = 0;
	}
}
