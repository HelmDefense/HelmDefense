package fr.helmdefense.model.entities.living;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDamagedAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.DamageCause;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class LivingEntity extends Entity {
	private ReadOnlyIntegerWrapper hpProperty;
	private ReadOnlyIntegerWrapper shieldProperty;
	private int flags;
	
	public static final int ALL = Integer.MAX_VALUE;
	public static final int TAUNT = 1;
	public static final int FIRE = 2;
	public static final int POISON = 4;
	public static final int IMMOBILE = 8;
	
	public LivingEntity(LivingEntityType type, Location loc) {
		super(type, loc);
		this.hpProperty = new ReadOnlyIntegerWrapper((int) this.stat(Attribute.HP));
		this.shieldProperty = new ReadOnlyIntegerWrapper(0);
		this.flags = 0;
	}
	
	public LivingEntity(LivingEntityType type, double x, double y) {
		this(type, new Location(x, y));
	}
	
	@Override
	public void spawn(Level lvl) {
		this.setHp((int) this.stat(Attribute.HP));
		super.spawn(lvl);
		this.setHp((int) this.stat(Attribute.HP));
	}
	
	public final int getHp() {
		return this.hpProperty.get();
	}
	
	public int looseHp(int amount, DamageCause cause, boolean ignoreShield) {
		if (! this.isAlive() || amount < 0)
			return -1;
		
		if (! ignoreShield) {
			int shield = this.getShield();
			this.setShield(shield - amount);
			if (this.hasShield())
				amount = 0;
			else {
				this.setShield(0);
				amount -= shield;
			}
		}
		LivingEntityDamagedAction damage = new LivingEntityDamagedAction(this, cause, this.getHp(), amount);
		
		this.setHp(this.getHp() - amount);
		// System.out.println("Entity #" + this.getId() + " (" + this.getType() + "): " + amount + " dmg from Entity #" + cause.getId() + " (" + cause.getType() + ")");
		
		this.triggerAbilities(damage);
		
		if (! this.isAlive()) {
			LivingEntityDeathAction death = new LivingEntityDeathAction(this, cause);
			this.triggerAbilities(death);
			
			this.delete();
			
			if (cause instanceof Entity) {
				EntityKillAction kill = new EntityKillAction((Entity) cause, this);
				if (cause instanceof Projectile)
					((Projectile) cause).getSource().triggerAbilities(kill);
				else
					((Entity) cause).triggerAbilities(kill);
			}
		}
		
		return amount;
	}
	
	public int looseHp(int amount, DamageCause cause) {
		return this.looseHp(amount, cause, false);
	}
	
	public void gainHp(int amount, boolean ignoreShield) {
		if (! this.isAlive() || amount < 0)
			return;
		
		this.setHp(this.getHp() + amount);
		if (this.getHp() > this.stat(Attribute.HP)) {
			if (! ignoreShield)
				this.setShield(this.getShield() + this.getHp() - (int) this.stat(Attribute.HP));
			this.setHp((int) this.stat(Attribute.HP));
		}
	}
	
	public void gainHp(int amount) {
		this.gainHp(amount, true);
	}
	
	protected final void setHp(int hp) {
		this.hpProperty.set(hp);
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
	
	protected final void setShield(int shield) {
		this.shieldProperty.set(shield);
	}
	
	public ReadOnlyIntegerProperty shieldProperty() {
		return this.shieldProperty.getReadOnlyProperty();
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
	
	public void toggleFlags(int flags) {
		this.flags ^= flags;
	}
	
	public boolean testFlags(int flags) {
		return (this.flags & flags) != 0;
	}

	@Override
	public LivingEntityType getType() {
		return (LivingEntityType) super.getType();
	}
	
	@ActionHandler
	public void tickDamage(GameTickAction action) {
		if (this.testFlags(FIRE | POISON) && action.getTicks() % TickDamageCause.TICK_DAMAGE_FREQUENCE == 0)
			TickDamageCause.attackWithInstance(this);
	}
	
	public boolean isEnemy(LivingEntity other) {
		return this.getType().getSide().isEnemy(other.getType().getSide());
	}
	
	public static class TickDamageCause implements DamageCause {
		private static TickDamageCause instance;
		
		public static final int TICK_DAMAGE = 50;
		public static final double TICK_DAMAGE_FREQUENCE = 5;
		
		private TickDamageCause() {}
		
		public static TickDamageCause getInstance() {
			if (instance == null)
				instance = new TickDamageCause();
			
			return instance;
		}
		
		@Override
		public void attack(LivingEntity victim) {
			victim.looseHp(TICK_DAMAGE, this);
		}
		
		public static void attackWithInstance(LivingEntity victim) {
			getInstance().attack(victim);
		}
	}
	
	public static class DispawnDamageCause implements DamageCause {
		private static DispawnDamageCause instance;
		
		private DispawnDamageCause() {}
		
		public static DispawnDamageCause getInstance() {
			if (instance == null)
				instance = new DispawnDamageCause();
			
			return instance;
		}
		
		@Override
		public void attack(LivingEntity victim) {
			victim.looseHp(victim.getHp(), this, true);
		}
		
		public static void attackWithInstance(LivingEntity victim) {
			getInstance().attack(victim);
		}
	}
}