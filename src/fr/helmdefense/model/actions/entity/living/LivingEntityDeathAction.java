package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.DamageCause;

/**
 * Action triggered when a living entity dies.
 * 
 * @author	indyteo
 * @see		LivingEntityAction
 */
public class LivingEntityDeathAction extends LivingEntityAction {
	private DamageCause killer;
	
	public LivingEntityDeathAction(LivingEntity entity, DamageCause killer) {
		super(entity);
		this.killer = killer;
	}
	
	/**
	 * Return the killer.
	 * 
	 * @return	The {@link DamageCause} that killed the
	 * 			{@link LivingEntity} involved in this
	 * 			action.
	 */
	public DamageCause getAttacker() {
		return this.killer;
	}
}