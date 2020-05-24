package fr.helmdefense.model.actions.entity.projectile;

import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.projectiles.Projectile;

/**
 * Action triggered when a projectile hurts a living entity.
 * 
 * @author	indyteo
 * @see		ProjectileEntityAction
 */
public class ProjectileEntityAttackAction extends ProjectileEntityAction {
	private LivingEntity victim;
	private int hpBefore;
	
	public ProjectileEntityAttackAction(Projectile projectile, LivingEntity victim, int hpBefore) {
		super(projectile);
		this.victim = victim;
	}
	
	/**
	 * Return the victim hurted by the projectile.
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
	 * <p>Note : At the moment this action is trigger,
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
	 * <p>Note : The value returned by this method might
	 * be {@code 0} even if the projectile dealt damage to
	 * the victim if the shield absorbed all damages.
	 * 
	 * @return	The difference between {@link LivingEntity}'s
	 * 			actual HP and old HP value.
	 */
	public int getDmg() {
		return this.victim.getHp() - this.hpBefore;
	}
}