package fr.helmdefense.model.actions.entity.projectile;

import fr.helmdefense.model.actions.entity.EntityAction;
import fr.helmdefense.model.entities.projectile.Projectile;

/**
 * Super-type for all projectile-related actions.
 * 
 * @author	indyteo
 * @see		EntityAction
 */
public abstract class ProjectileEntityAction extends EntityAction {
	public ProjectileEntityAction(Projectile projectile) {
		super(projectile);
	}
	
	/**
	 * Return the projectile involved in this action.
	 * 
	 * @return	The {@link Projectile} involved in this
	 * 			action.
	 */
	@Override
	public Projectile getEntity() {
		return (Projectile) super.getEntity();
	}
}