package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

/**
 * Action triggered when an entity attacks a living entity.
 * 
 * @author	indyteo
 * @see		EntityAction
 */
public class EntityDirectAttackAction extends EntityAction {
	private LivingEntity victim;
	private int hpBefore;
	
	public EntityDirectAttackAction(Entity entity, LivingEntity victim, int hpBefore) {
		super(entity);
		this.victim = victim;
		this.hpBefore = hpBefore;
	}
	
	/**
	 * Return the victim attacked by this entity.
	 * 
	 * @return	The {@link LivingEntity} attacked by
	 * 			this entity.
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
	 * 			taking the damage from the attacker.
	 */
	public int getHpBefore() {
		return this.hpBefore;
	}
	
	/**
	 * Return the amount of damage the attacker dealt to
	 * the victim
	 * 
	 * <p>Note: The value returned by this method might
	 * be {@code 0} even if the attacker dealt damage to
	 * the victim if the shield absorbed all damages.
	 * 
	 * @return	The difference between {@link LivingEntity}'s
	 * 			actual HP and old HP value.
	 */
	public int getDmg() {
		return this.victim.getHp() - this.hpBefore;
	}
}