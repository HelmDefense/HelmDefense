package fr.helmdefense.model.actions.entity.projectile;

import fr.helmdefense.model.entities.projectiles.Projectile;

/**
 * Action triggered when a projectile fails.
 * 
 * @author	indyteo
 * @see		ProjectileEntityAction
 */
public class ProjectileEntityFailAction extends ProjectileEntityAction {
	public ProjectileEntityFailAction(Projectile projectile) {
		super(projectile);
	}
}