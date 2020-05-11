package fr.helmdefense.model.entities.abilities.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.actions.Action;

public abstract class EntityAction extends Action {
	private Entity entity;
	
	public EntityAction(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
}