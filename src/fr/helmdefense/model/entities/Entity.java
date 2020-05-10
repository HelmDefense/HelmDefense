package fr.helmdefense.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.attackers.Goblin;
import fr.helmdefense.model.entities.attackers.OrcBomber;
import fr.helmdefense.model.entities.attackers.OrcWarrior;
import fr.helmdefense.model.entities.attackers.Troll;
import fr.helmdefense.model.entities.attackers.UrukHai;
import fr.helmdefense.model.entities.utils.Location;
import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.utils.YAMLLoader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

public abstract class Entity {
	private String id;
	private Location loc;
	private String name;
	private Statistic stats;
	private IntegerProperty hp;
	private IntegerProperty shield;
	private List<Ability> abilities;
	private Level level;
	
	private static int ids = 0;
	
	public Entity(Location loc, String name) {
		this.id = Integer.toString(++ids);
		this.loc = loc;
		this.name = name;
		this.stats = YAMLLoader.loadStats(name + ".tier1");
		this.hp = new SimpleIntegerProperty(stats.getHp());
		this.shield = new SimpleIntegerProperty(0);
		this.abilities = new ArrayList<Ability>();
		this.level = null;
	}
	
	public Entity(int x, int y, String name) {
		this(new Location(x, y), name);
	}
	
	public void addAbilities(Ability... abilities) {
		this.abilities.addAll(Arrays.asList(abilities));
	}
	
	public void spawn(Level lvl) {
		if (this.level != null || lvl.getEntities().contains(this))
			return;
		this.level = lvl;
		lvl.getEntities().add(this);
	}
	
	public String getId() {
		return this.id;
	}
	
	public Location getLoc() {
		return this.loc.copy();
	}
	
	public void bindX(Property<? super Number> property, Function<IntegerProperty, ObservableValue<Number>> transform) {
		property.bind(transform != null ? transform.apply(this.loc.getXProperty()) : this.loc.getXProperty());
	}
	
	public void bindX(Property<? super Number> property) {
		this.bindX(property, null);
	}
	
	public void bindY(Property<? super Number> property, Function<IntegerProperty, ObservableValue<Number>> transform) {
		property.bind(transform != null ? transform.apply(this.loc.getYProperty()) : this.loc.getYProperty());
	}
	
	public void bindY(Property<? super Number> property) {
		this.bindY(property, null);
	}
	
	public void teleport(int x, int y) {
		this.loc.setX(x);
		this.loc.setY(y);
	}
	
	public void teleport(Location loc) {
		this.teleport(loc.getX(), loc.getY());
	}
	
	public String getName() {
		return this.name;
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

	@Override
	public String toString() {
		return "Entity [id=" + id + ", loc=" + loc + ", name=" + name + ", stats=" + stats + ", hp=" + hp + ", shield="
				+ shield + ", abilities=" + abilities + ", level=" + level + "]";
	}
	
	public enum EntityList {
		ORC_WARRIOR(OrcWarrior.class),
		ORC_BOMBER(OrcBomber.class),
		GOBLIN(Goblin.class),
		URUK_HAI(UrukHai.class),
		TROLL(Troll.class);
		
		private Class<? extends Entity> type;
		
		private EntityList(Class<? extends Entity> type) {
			this.type = type;
		}
		
		public Class<? extends Entity> getType() {
			return this.type;
		}
	}
}