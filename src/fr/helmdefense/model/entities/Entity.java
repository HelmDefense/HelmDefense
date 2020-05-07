package fr.helmdefense.model.entities;

import fr.helmdefense.model.entities.utils.Location;
import fr.helmdefense.model.entities.utils.Statistic;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Entity {
	private Location loc;
	private Statistic stats;
	private IntegerProperty hp;
	private IntegerProperty shield;
	
	public Entity(Location loc, Statistic stats) {
		this.loc = loc;
		this.stats = stats;
		this.hp = new SimpleIntegerProperty(stats.getHp());
		this.shield = new SimpleIntegerProperty(0);
	}
	
	public Entity(int x, int y, Statistic stats) {
		this(new Location(x, y), stats);
	}
	
	public Entity(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		this(x, y, new Statistic(hp, dmg, mvtSpd, atkSpd, atkRange, shootRange));
	}
	
	public Location getLoc() {
		return this.loc.copy();
	}
	
	public void bindX(Property<? super Number> property) {
		property.bind(this.loc.getXProperty());
	}
	
	public void bindY(Property<? super Number> property) {
		property.bind(this.loc.getYProperty());
	}
	
	public void teleport(int x, int y) {
		this.loc.setX(x);
		this.loc.setY(y);
	}
	
	public void teleport(Location loc) {
		this.teleport(loc.getX(), loc.getY());
	}

	public Statistic getStats() {
		return this.stats;
	}
	
	public final int getHp() {
		return this.hp.get();
	}
	
	public void looseHp(int amount, boolean ignoreShield) {
		if (! ignoreShield) {
			int shield = this.getShield();
			this.shield.set(shield - amount);
			if (this.hasShield())
				amount = 0;
			else {
				this.shield.set(0);
				amount -= shield;
			}
		}
		this.hp.set(this.getHp() - amount);
		
		if (! this.isAlive()) {
			// TODO Kill entity
		}
	}
	
	public void looseHp(int amount) {
		this.looseHp(amount, false);
	}
	
	public void gainHp(int amount, boolean ignoreShield) {
		this.hp.set(this.getHp() + amount);
		if (this.getHp() > this.stats.getHp()) {
			if (! ignoreShield)
				this.shield.set(this.getShield() + this.getHp() - this.stats.getHp());
			this.hp.set(this.stats.getHp());
		}
	}
	
	public void gainHp(int amount) {
		this.gainHp(amount, true);
	}
	
	public boolean isAlive() {
		return this.getHp() > 0;
	}
	
	public boolean hasShield() {
		return this.getShield() > 0;
	}
	
	public int getShield() {
		return this.shield.get();
	}
}