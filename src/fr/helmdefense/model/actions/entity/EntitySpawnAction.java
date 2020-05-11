package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public class EntitySpawnAction extends EntityAction {
	public EntitySpawnAction(Entity entity) {
		super(entity);
	}
	
	public Location getSpawn() {
		return this.getEntity().getLoc();
	}
}