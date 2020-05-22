package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

public class LivingEntityDeathAction extends LivingEntityAction {
	private Entity killer;
	
	public LivingEntityDeathAction(LivingEntity entity, Entity killer) {
		super(entity);
		this.killer = killer;
	}
	
	public Entity getAttacker() {
		return this.killer;
	}
}