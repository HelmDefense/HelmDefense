package fr.helmdefense.model.entities.defender;

import fr.helmdefense.model.entities.Entity;

public abstract class Defender extends Entity {
	private int cost;
	
	public Defender(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange, int cost) {
		super(x, y, hp, dmg, mvtSpd, atkSpd, atkRange, shootRange);
		this.cost = cost;
	}
}
