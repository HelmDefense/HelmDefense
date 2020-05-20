package fr.helmdefense.model.entities;

import java.util.List;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.actions.entity.EntityDamagedAction;
import fr.helmdefense.model.actions.entity.EntityDeathAction;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.actions.entity.EntityMoveAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public abstract class Entity {
	private String id;
	private Location loc;
	private ReadOnlyIntegerWrapper hpProperty;
	private ReadOnlyIntegerWrapper shieldProperty;
	private List<Ability> abilities;
	private Level level;
	
	private static int ids = 0;
	
	public Entity(Location loc) {
		this.id = "E" + (++ids);
		this.loc = loc;
		this.hpProperty = new ReadOnlyIntegerWrapper(this.data().getStats(Tier.TIER_1).getHp());
		this.shieldProperty = new ReadOnlyIntegerWrapper(0);
		this.abilities = this.data().instanciateAbilities();
		Actions.registerListeners(this.abilities);
		this.level = null;
	}
	
	public Entity(double x, double y) {
		this(new Location(x, y));
	}
	
	public void spawn(Level lvl) {
		if (this.level != null || lvl.getEntities().contains(this))
			return;
		this.level = lvl;
		lvl.getEntities().add(this);
	}
	
	public void attack(Entity victim) {
		EntityDirectAttackAction attack = new EntityDirectAttackAction(this, victim, victim.getHp());
		
		victim.looseHp(this.data().getStats(Tier.TIER_1).getDmg(), this);
		
		this.triggerAbilities(attack);
	}
	
	public void triggerAbilities(Action action) {
		Actions.trigger(action, this.abilities);
	}
	
	public String getId() {
		return this.id;
	}
	
	public Location getLoc() {
		return this.loc.copy();
	}
	
	public ReadOnlyDoubleProperty xProperty() {
		return this.loc.xProperty().getReadOnlyProperty();
	}
	
	public ReadOnlyDoubleProperty yProperty() {
		return this.loc.yProperty().getReadOnlyProperty();
	}
	
	public void teleport(double x, double y) {
		EntityMoveAction action = new EntityMoveAction(this, this.getLoc());
		
		this.loc.setX(x);
		this.loc.setY(y);
		
		this.triggerAbilities(action);
	}
	
	public void teleport(Location loc) {
		this.teleport(loc.getX(), loc.getY());
	}
	
	public final int getHp() {
		return this.hpProperty.get();
	}
	
	public void looseHp(int amount, Entity cause, boolean ignoreShield) {
		EntityDamagedAction damage = new EntityDamagedAction(this, cause, this.getHp());
		
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
		
		this.triggerAbilities(damage);
		
		if (! this.isAlive()) {
			EntityKillAction kill = new EntityKillAction(cause, this);
			EntityDeathAction death = new EntityDeathAction(this, cause);
			
			this.level.getEntities().remove(this);
			Actions.unregisterListeners(this.abilities);
			
			cause.triggerAbilities(kill);
			this.triggerAbilities(death);
		}
	}
	
	public void looseHp(int amount, Entity cause) {
		this.looseHp(amount, cause, false);
	}
	
	public void gainHp(int amount, boolean ignoreShield) {
		this.hpProperty.set(this.getHp() + amount);
		if (this.getHp() > this.data().getStats(Tier.TIER_1).getHp()) {
			if (! ignoreShield)
				this.shieldProperty.set(this.getShield() + this.getHp() - this.data().getStats(Tier.TIER_1).getHp());
			this.hpProperty.set(this.data().getStats(Tier.TIER_1).getHp());
		}
	}
	
	public void gainHp(int amount) {
		this.gainHp(amount, true);
	}
	
	public ReadOnlyIntegerProperty hpProperty() {
		return this.hpProperty.getReadOnlyProperty();
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
	
	public ReadOnlyIntegerProperty shieldProperty() {
		return this.shieldProperty.getReadOnlyProperty();
	}
	
	public EntityData data() {
		return Entities.getData(this.getClass());
	}
	public Level getLevel() {
		return level;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", loc=" + loc + ", hpProperty=" + hpProperty
				+ ", shieldProperty=" + shieldProperty + ", abilities=" + abilities + "]";
	}
}