package fr.helmdefense.model.entities.utils;

public class Statistic {
	private int hp;
	private int dmg;
	private double mvtSpd;
	private double atkSpd;
	private double atkRange;
	private double shootRange;
	
	public Statistic(int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		this.hp = hp;
		this.dmg = dmg;
		this.mvtSpd = mvtSpd;
		this.atkSpd = atkSpd;
		this.atkRange = atkRange;
		this.shootRange = shootRange;
	}

	public int getHp() {
		return hp;
	}

	public int getDmg() {
		return dmg;
	}

	public double getMvtSpd() {
		return mvtSpd;
	}

	public double getAtkSpd() {
		return atkSpd;
	}

	public double getAtkRange() {
		return atkRange;
	}

	public double getShootRange() {
		return shootRange;
	}
}