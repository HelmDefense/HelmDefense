package fr.helmdefense.model.actions.entity.projectile;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.projectile.Projectile;

/**
 * Action triggered when a projectile hurts a living entity.
 * 
 * @author	indyteo
 * @see		ProjectileEntityAction
 */
public class ProjectileEntityAttackAction extends ProjectileEntityAction {
	private LivingEntity victim;
	private int hpBefore;
	private int dmg;
	
	public ProjectileEntityAttackAction(Projectile projectile, LivingEntity victim, int hpBefore, int dmg) {
		super(projectile);
		this.victim = victim;
		this.dmg = dmg;
	}
	
	/**
	 * Return the victim hurt by the projectile.
	 * 
	 * @return	The {@link LivingEntity} the projectile
	 * 			enter in collision with.
	 */
	public LivingEntity getVictim() {
		return this.victim;
	}
	
	/**
	 * Return the HP of the victim before the attack.
	 * 
	 * <p>Note: At the moment this action is triggered,
	 * the victim already took the damage, but the death
	 * check didn't occured yet. Restoring this value as
	 * actual HP should avoid the entity's possible death.
	 * 
	 * @return	The HP of the {@link LivingEntity} before
	 * 			taking the damage from the projectile.
	 */
	public int getHpBefore() {
		return this.hpBefore;
	}
	
	/**
	 * Return the amount of damage the projectile dealt to
	 * the victim
	 * 
	 * <p>Note: The value returned by this method might
	 * be {@code 0} even if the projectile dealt damages to
	 * the victim if the shield absorbed all damages.
	 * 
	 * @return	The damage dealt by the attacker to the victim.
	 */
	public int getDmg() {
		return this.dmg;
	}
}