package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.DamageCause;

/**
 * Action triggered when a living entity receive damage.
 * 
 * @author	indyteo
 * @see		LivingEntityAction
 */
public class LivingEntityDamagedAction extends LivingEntityAction {
	private DamageCause attacker;
	private int hpBefore;
	private int dmg;
	
	public LivingEntityDamagedAction(LivingEntity entity, DamageCause attacker, int hpBefore, int dmg) {
		super(entity);
		this.attacker = attacker;
		this.hpBefore = hpBefore;
		this.dmg = dmg;
	}
	
	/**
	 * Return the attacker.
	 * 
	 * @return	The {@link DamageCause} that inflict damage
	 * 			to the {@link LivingEntity} involved in
	 * 			this action.
	 */
	public DamageCause getAttacker() {
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
	 * @return	The damage dealt by the attacker to the victim.
	 */
	public int getDmg() {
		return this.dmg;
	}
}