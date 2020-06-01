package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.actions.entity.EntityAction;
import fr.helmdefense.model.entities.living.LivingEntity;

/**
 * Super-type for all living entity-related actions.
 * 
 * @author	indyteo
 * @see		EntityAction
 */
public abstract class LivingEntityAction extends EntityAction {
	public LivingEntityAction(LivingEntity entity) {
		super(entity);
	}
	
	/**
	 * Return the living entity involved in this action
	 * 
	 * @return	The {@link LivingEntity} involved in
	 * 			this action.
	 */
	@Override
	public LivingEntity getEntity() {
		return (LivingEntity) super.getEntity();
	}
}