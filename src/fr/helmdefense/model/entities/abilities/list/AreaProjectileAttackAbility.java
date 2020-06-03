package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class AreaProjectileAttackAbility extends Ability {
	private double radius;
	private boolean shooting;
	
	public AreaProjectileAttackAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock,tierSpecification, 0.5d);
	}
	
	public AreaProjectileAttackAbility(Tier unlock,Tier.Specification tierSpecification, Double radius) {
		super(unlock, tierSpecification);
		this.radius = radius;
		this.shooting = false;
	}
	
	@ActionHandler
	public void areaProjectileDamageAbility(ProjectileEntityAttackAction action) {
		if (this.shooting)
			return;
		
		this.shooting = true;
		LivingEntity entity = action.getEntity().getSource(), victim = action.getVictim(), testing;
		Location victimLocation = victim.getLoc();
		for (Entity target : entity.getLevel().getEntities()) {
			if (target instanceof LivingEntity
					&& (testing = (LivingEntity) target) != victim
					&& entity.isEnemy(testing)
					&& victimLocation.distance(target.getLoc()) <= this.radius)
				action.getEntity().attack(testing);
		}
		this.shooting = false;
	}
}
