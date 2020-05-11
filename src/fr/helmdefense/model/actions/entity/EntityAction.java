package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.entities.Entity;

public abstract class EntityAction extends Action {
	private Entity entity;
	
	public EntityAction(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
}