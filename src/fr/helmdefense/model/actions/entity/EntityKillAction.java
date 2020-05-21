package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

public class EntityKillAction extends EntityAction {
	private LivingEntity victim;
	
	public EntityKillAction(Entity entity, LivingEntity victim) {
		super(entity);
		this.victim = victim;
	}
	
	public LivingEntity getVictim() {
		return this.victim;
	}
}