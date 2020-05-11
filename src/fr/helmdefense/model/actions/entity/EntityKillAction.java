package fr.helmdefense.model.entities.abilities.actions.entity;

import fr.helmdefense.model.entities.Entity;

public class EntityKillAction extends EntityAction {
	private Entity victim;
	
	public EntityKillAction(Entity entity, Entity victim) {
		super(entity);
		this.victim = victim;
	}
	
	public Entity getVictim() {
		return this.victim;
	}
}