package fr.helmdefense.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.Location;
import fr.helmdefense.model.level.Level;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

public abstract class Entity {
	private String id;
	private Location loc;
	private IntegerProperty hpProperty;
	private IntegerProperty shieldProperty;
	private List<Ability> abilities;
	private Level level;
	
	private static int ids = 0;
	
	public Entity(Location loc) {
		this.id = "E" + (++ids);
		this.loc = loc;
		this.hpProperty = new SimpleIntegerProperty(Entities.getData(this.getClass()).getHp());
		this.shieldProperty = new SimpleIntegerProperty(0);
		this.abilities = new ArrayList<Ability>();
		this.level = null;
	}
	
	public Entity(int x, int y) {
		this(new Location(x, y));
	}
	
	public void addAbilities(Ability... abilities) {
		this.abilities.addAll(Arrays.asList(abilities));
		Actions.registerListener(abilities);
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
	
	public final int getHp() {
		return this.hpProperty.get();
	}
	
	public void looseHp(int amount, boolean ignoreShield) {
		if (! ignoreShield) {
			int shield = this.getShield();
			this.shieldProperty.set(shield - amount);
			if (this.hasShield())
				amount = 0;
			else {
				this.shieldProperty.set(0);
				amount -= shield;
			}
		}
		this.hpProperty.set(this.getHp() - amount);
		
		if (! this.isAlive()) {
			// TODO Kill entity
		}
	}
	
	public void looseHp(int amount) {
		this.looseHp(amount, false);
	}
	
	public void gainHp(int amount, boolean ignoreShield) {
		this.hpProperty.set(this.getHp() + amount);
		if (this.getHp() > Entities.getData(this.getClass()).getHp()) {
			if (! ignoreShield)
				this.shieldProperty.set(this.getShield() + this.getHp() - Entities.getData(this.getClass()).getHp());
			this.hpProperty.set(Entities.getData(this.getClass()).getHp());
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
		return this.shieldProperty.get();
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + ", loc=" + loc + ", hpProperty=" + hpProperty + ", shieldProperty=" + shieldProperty
				+ ", abilities=" + abilities + "]";
	}
}