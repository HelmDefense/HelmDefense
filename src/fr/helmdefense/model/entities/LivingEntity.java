package fr.helmdefense.model.entities;

import fr.helmdefense.model.actions.entity.EntityDamagedAction;
import fr.helmdefense.model.actions.entity.EntityDeathAction;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public abstract class LivingEntity extends Entity {
	private ReadOnlyIntegerWrapper hpProperty;
	private ReadOnlyIntegerWrapper shieldProperty;
	
	public LivingEntity(Location loc) {
		super(loc);
		this.hpProperty = new ReadOnlyIntegerWrapper(this.data().getStats(Tier.TIER_1).getHp());
		this.shieldProperty = new ReadOnlyIntegerWrapper(0);
	}
	
	public LivingEntity(double x, double y) {
		this(new Location(x, y));
	}
	
	public void attack(LivingEntity victim) {
		EntityDirectAttackAction attack = new EntityDirectAttackAction(this, victim, victim.getHp());
		
		victim.looseHp(this.data().getStats(Tier.TIER_1).getDmg(), this);
		
		this.triggerAbilities(attack);
	}
	
	public final int getHp() {
		return this.hpProperty.get();
	}
	
	public void looseHp(int amount, LivingEntity cause, boolean ignoreShield) {
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
			
			this.getLevel().getEntities().remove(this);
			this.unregisterAbilities();
			
			cause.triggerAbilities(kill);
			this.triggerAbilities(death);
		}
	}
	
	public void looseHp(int amount, LivingEntity cause) {
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
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [hpProperty=" + hpProperty + ", shieldProperty=" + shieldProperty + "]";
	}
}