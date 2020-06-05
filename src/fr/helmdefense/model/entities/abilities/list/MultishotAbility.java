package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityShootAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.utils.Tier;

public class MultishotAbility extends Ability {
	private double angle;
	private boolean shooting;
	
	public MultishotAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 15d);
	}
	
	public MultishotAbility(Tier unlock, Tier.Specification tierSpecification, Double angle) {
		super(unlock, tierSpecification);
		this.angle = angle;
		this.shooting = false;
	}
	
	@ActionHandler
	public void multishotAbility(ProjectileEntityShootAction action) {
		if (this.shooting)
			return;
		
		this.shooting = true;
		Projectile p = action.getEntity();
		new Projectile(p.getType(), p.getSource(), p.getTarget(), this.angle, p.getSpeed()).spawn(p.getSource().getLevel());
		new Projectile(p.getType(), p.getSource(), p.getTarget(), - this.angle, p.getSpeed()).spawn(p.getSource().getLevel());
		this.shooting = false;
	}
}