package fr.helmdefense.model.entities.abilities.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.projectiles.Projectile;

public class EntityShootAction extends EntityAction {
	private Projectile proj;
	
	public EntityShootAction(Entity entity, Projectile proj) {
		super(entity);
		this.proj = proj;
	}
	
	public Projectile getProj() {
		return this.proj;
	}
}