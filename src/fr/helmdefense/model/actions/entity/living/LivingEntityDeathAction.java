package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

/**
 * Action triggered when a living entity dies.
 * 
 * @author	indyteo
 * @see		LivingEntityAction
 */
public class LivingEntityDeathAction extends LivingEntityAction {
	private Entity killer;
	
	public LivingEntityDeathAction(LivingEntity entity, Entity killer) {
		super(entity);
		this.killer = killer;
	}
	
	/**
	 * Return the killer.
	 * 
	 * @return	The {@link Entity} that killed the
	 * 			{@link LivingEntity} involved in this
	 * 			action.
	 */
	public Entity getAttacker() {
		return this.killer;
	}
}