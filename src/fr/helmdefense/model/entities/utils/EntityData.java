package fr.helmdefense.model.entities.utils;

public class EntityData {
	private String name;
	private String path;
	private int hp;
	private int dmg;
	private double mvtSpd;
	private double atkSpd;
	private double atkRange;
	private double shootRange;
	private int cost;
	private int reward;
	
	public EntityData(String name, String path, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange, int cost, int reward) {
		this.name = name;
		this.path = path;
		this.hp = hp;
		this.dmg = dmg;
		this.mvtSpd = mvtSpd;
		this.atkSpd = atkSpd;
		this.atkRange = atkRange;
		this.shootRange = shootRange;
		this.cost = cost;
		this.reward = reward;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final String getPath() {
		return this.path;
	}
	
	public final int getHp() {
		return this.hp;
	}
	
	public final int getDmg() {
		return this.dmg;
	}
	
	public final double getMvtSpd() {
		return this.mvtSpd;
	}
	
	public final double getAtkSpd() {
		return this.atkSpd;
	}
	
	public final double getAtkRange() {
		return this.atkRange;
	}
	
	public final double getShootRange() {
		return this.shootRange;
	}
	
	public final int getCost() {
		return this.cost;
	}
	
	public final int getReward() {
		return this.reward;
	}

	@Override
	public String toString() {
		return "EntityData [name=" + name + ", path=" + path + ", hp=" + hp + ", dmg=" + dmg + ", mvtSpd=" + mvtSpd
				+ ", atkSpd=" + atkSpd + ", atkRange=" + atkRange + ", shootRange=" + shootRange + ", cost=" + cost
				+ ", reward=" + reward + "]";
	}
}