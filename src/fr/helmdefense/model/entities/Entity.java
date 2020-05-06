package fr.helmdefense.model.entities;

import fr.helmdefense.model.entities.utils.Statistic;

public abstract class Entity {
	private int x;
	private int y;
	private Statistic stats;
	
	public Entity(int x, int y, Statistic stats) {
		this.x = x;
		this.y = y;
		this.stats = stats;
	}
	
	public Entity(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		this.x = x;
		this.y = y;
		this.stats = new Statistic(hp, dmg, mvtSpd, atkSpd, atkRange, shootRange);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Statistic getStats() {
		return stats;
	}
}