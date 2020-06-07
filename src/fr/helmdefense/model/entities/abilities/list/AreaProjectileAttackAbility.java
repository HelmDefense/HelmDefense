package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.utils.Tier;

public class AreaProjectileAttackAbility extends AreaAttackAbility {
	private double radius;
	
	public AreaProjectileAttackAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock,tierSpecification, 1.5d);
	}
	
	public AreaProjectileAttackAbility(Tier unlock,Tier.Specification tierSpecification, Double radius) {
		super(unlock, tierSpecification);
		this.radius = radius;
	}
	
	@ActionHandler
	public void areaProjectileDamageAbility(ProjectileEntityAttackAction action) {
		Projectile entity = action.getEntity();
		LivingEntity victim = action.getVictim();
		this.areaAttackAbility(victim.getLoc(), entity, this.radius, entity.getSource().getType().getSide(), victim);
	}
}
