package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

/**
 * Action triggered when a living entity receive damage.
 * 
 * @author	indyteo
 * @see		LivingEntityAction
 */
public class LivingEntityDamagedAction extends LivingEntityAction {
	private Entity attacker;
	private int hpBefore;
	
	public LivingEntityDamagedAction(LivingEntity entity, Entity attacker, int hpBefore) {
		super(entity);
		this.attacker = attacker;
		this.hpBefore = hpBefore;
	}
	
	/**
	 * Return the attacker.
	 * 
	 * @return	The {@link Entity} that inflict damage
	 * 			to the {@link LivingEntity} involved in
	 * 			this action.
	 */
	public Entity getAttacker() {
		return this.attacker;
	}
	
	/**
	 * Return the HP of the victim before the attack.
	 * 
	 * <p>Note: At the moment this action is trigger,
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
		return this.getEntity().getHp() - this.hpBefore;
	}
}