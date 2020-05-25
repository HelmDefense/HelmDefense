package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.entities.Entity;

/**
 * Super-type for all entity-related actions.
 * 
 * @author	indyteo
 * @see		Action
 */
public abstract class EntityAction extends Action {
	private Entity entity;
	
	public EntityAction(Entity entity) {
		this.entity = entity;
	}
	
	/**
	 * Return the entity involved in this action.
	 * 
	 * @return	The {@link Entity} involved in this
	 * 			action.
	 */
	public Entity getEntity() {
		return this.entity;
	}
}