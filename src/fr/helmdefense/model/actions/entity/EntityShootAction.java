package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.projectiles.Projectile;

public class EntityShootAction extends EntityAction {
	private Projectile proj;
	
	public EntityShootAction(LivingEntity entity, Projectile proj) {
		super(entity);
		this.proj = proj;
	}
	
	public Projectile getProj() {
		return this.proj;
	}
}