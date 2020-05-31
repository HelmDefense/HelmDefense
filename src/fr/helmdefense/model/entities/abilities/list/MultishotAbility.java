package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityShootAction;
import fr.helmdefense.model.entities.abilities.SpecifiedAbility;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.utils.Tier;

public class MultishotAbility extends SpecifiedAbility {
	private boolean shooting;
	private double angle;
	public MultishotAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 20d);
	}
	
	public MultishotAbility(Tier unlock, Tier.Specification tierSpecification, Double angle) {
		super(unlock, tierSpecification);
		this.angle = angle;
		this.shooting = false;
	}
	
	@ActionHandler
	public void multishotAbility(ProjectileEntityShootAction action) {
		if ( shooting )
			return;
		shooting = false;
		Projectile p = action.getEntity();
		new Projectile(p.getType(), p.getSource(), p.getTarget(), this.angle, p.getSpeed());
		new Projectile(p.getType(), p.getSource(), p.getTarget(), - this.angle, p.getSpeed());
		shooting = true;
	}
}
