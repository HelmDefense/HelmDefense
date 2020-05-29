package fr.helmdefense.model.entities;

import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDamagedAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.coords.Location;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public abstract class LivingEntity extends Entity {
	private ReadOnlyIntegerWrapper hpProperty;
	private ReadOnlyIntegerWrapper shieldProperty;
	private boolean taunt;
	
	public LivingEntity(Location loc) {
		super(loc);
		this.hpProperty = new ReadOnlyIntegerWrapper((int) this.stat(Attribute.HP));
		this.shieldProperty = new ReadOnlyIntegerWrapper(0);
		this.taunt = false;
	}
	
	public LivingEntity(double x, double y) {
		this(new Location(x, y));
	}
	
	public final int getHp() {
		return this.hpProperty.get();
	}
	
	public void looseHp(int amount, Entity cause, boolean ignoreShield) {
		LivingEntityDamagedAction damage = new LivingEntityDamagedAction(this, cause, this.getHp());
		
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
			LivingEntityDeathAction death = new LivingEntityDeathAction(this, cause);
			
			this.getLevel().getEntities().remove(this);
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
		if (this.getHp() > this.stat(Attribute.HP)) {
			if (! ignoreShield)
				this.shieldProperty.set(this.getShield() + this.getHp() - (int) this.stat(Attribute.HP));
			this.hpProperty.set((int) this.stat(Attribute.HP));
		}
	}
	
	public void gainHp(int amount) {
		this.gainHp(amount, true);
	}
	
	public ReadOnlyIntegerProperty hpProperty() {
		return this.hpProperty.getReadOnlyProperty();
	}
	
	public void setTaunt(boolean taunt) {
		this.taunt = taunt;
	}
	
	public boolean isTaunting() {
		return this.taunt;
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
}