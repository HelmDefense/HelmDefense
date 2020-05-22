package fr.helmdefense.model.actions.entity.projectile;

import fr.helmdefense.model.actions.entity.EntityAction;
import fr.helmdefense.model.entities.projectiles.Projectile;

public abstract class ProjectileEntityAction extends EntityAction {
	public ProjectileEntityAction(Projectile projectile) {
		super(projectile);
	}
	
	@Override
	public Projectile getEntity() {
		return (Projectile) super.getEntity();
	}
}