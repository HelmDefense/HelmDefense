package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.actions.entity.EntityAction;
import fr.helmdefense.model.entities.LivingEntity;

public abstract class LivingEntityAction extends EntityAction {
	public LivingEntityAction(LivingEntity entity) {
		super(entity);
	}
	
	@Override
	public LivingEntity getEntity() {
		return (LivingEntity) super.getEntity();
	}
}