package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;	
import fr.helmdefense.model.entities.abilities.SpecifiedAbility;
import fr.helmdefense.model.entities.attackers.Attacker;
import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Tier;

public class AreaProjectileAttackAbility extends SpecifiedAbility {
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
		if ( shooting )
			return;
		
		shooting = true;
		Entity entity = action.getEntity().getSource();
		Entity victim = action.getVictim();
		for (Entity target : entity.getLevel().getEntities()) {
			if ( target != entity 
					&& victim.getLoc().distance(target.getLoc()) <= radius 
					&& entity instanceof Attacker ? target instanceof Defender : target instanceof Attacker )
				action.getEntity().attack((LivingEntity)victim);
		}
		shooting = false;
	}
}
