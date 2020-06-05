package fr.helmdefense.model.entities.living;

import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDamagedAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.coords.Location;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class LivingEntity extends Entity {
	private ReadOnlyIntegerWrapper hpProperty;
	private ReadOnlyIntegerWrapper shieldProperty;
	private int flags;
	
	public static final int TAUNT = 1;
	public static final int FIRE = 2;
	public static final int POISON = 4;
	
	public LivingEntity(LivingEntityType type, Location loc) {
		super(type, loc);
		this.hpProperty = new ReadOnlyIntegerWrapper((int) this.stat(Attribute.HP));
		this.shieldProperty = new ReadOnlyIntegerWrapper(0);
		this.flags = 0;
	}
	
	public LivingEntity(LivingEntityType type, double x, double y) {
		this(type, new Location(x, y));
	}
	
	public final int getHp() {
		return this.hpProperty.get();
	}
	
	public int looseHp(int amount, Entity cause, boolean ignoreShield) {
		if (! this.isAlive())
			return -1;
		
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
		LivingEntityDamagedAction damage = new LivingEntityDamagedAction(this, cause, this.getHp(), amount);
		
		this.hpProperty.set(this.getHp() - amount);
		// System.out.println("Entity #" + this.getId() + " (" + this.getType() + "): " + amount + " dmg from Entity #" + cause.getId() + " (" + cause.getType() + ")");
		
		this.triggerAbilities(damage);
		
		if (! this.isAlive()) {
			EntityKillAction kill = new EntityKillAction(cause, this);
			LivingEntityDeathAction death = new LivingEntityDeathAction(this, cause);
			
			this.delete();
			
			cause.triggerAbilities(kill);
			this.triggerAbilities(death);
		}
		
		return amount;
	}
	
	public int looseHp(int amount, Entity cause) {
		return this.looseHp(amount, cause, false);
	}
	
	public void gainHp(int amount, boolean ignoreShield) {
		if (! this.isAlive())
			return;
		
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
	
	public int getFlags() {
		return this.flags;
	}
	
	public void addFlags(int flags) {
		this.flags |= flags;
	}
	
	public void removeFlags(int flags) {
		this.flags &= ~flags;
	}
	
	public void reverseFlags(int flags) {
		this.flags ^= flags;
	}
	
	public boolean testFlags(int flags) {
		return (this.flags & flags) != 0;
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
	public LivingEntityType getType() {
		return (LivingEntityType) super.getType();
	}
	
	public boolean isEnemy(LivingEntity other) {
		return this.getType().getSide().isEnemy(other.getType().getSide());
	}
}