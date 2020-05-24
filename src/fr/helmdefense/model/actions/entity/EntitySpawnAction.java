package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.coords.Location;

/**
 * Action triggered when an entity spawns.
 * 
 * @author	indyteo
 * @see		EntityAction
 */
public class EntitySpawnAction extends EntityAction {
	public EntitySpawnAction(Entity entity) {
		super(entity);
	}
	
	/**
	 * Return the location where the entity spawned.
	 * 
	 * @return	The {@link Location} of the entity
	 * 			at the moment of the spawn.
	 */
	public Location getSpawn() {
		return this.getEntity().getLoc();
	}
}