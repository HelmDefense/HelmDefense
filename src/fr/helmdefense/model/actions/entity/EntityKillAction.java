package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.living.LivingEntity;

/**
 * Action triggered when an entity kills a living entity.
 * 
 * @author	indyteo
 * @see		EntityAction
 */
public class EntityKillAction extends EntityAction {
	private LivingEntity victim;
	
	public EntityKillAction(Entity entity, LivingEntity victim) {
		super(entity);
		this.victim = victim;
	}
	
	/**
	 * Return the victim.
	 * 
	 * @return	The {@link LivingEntity} that was killed
	 * 			by the {@link Entity} involved in this
	 * 			action.
	 */
	public LivingEntity getVictim() {
		return this.victim;
	}
}