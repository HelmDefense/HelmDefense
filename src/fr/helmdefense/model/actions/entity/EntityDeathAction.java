package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

public class EntityDeathAction extends EntityAction {
	private Entity attacker;
	
	public EntityDeathAction(LivingEntity entity, Entity attacker) {
		super(entity);
		this.attacker = attacker;
	}
	
	public Entity getAttacker() {
		return this.attacker;
	}
}