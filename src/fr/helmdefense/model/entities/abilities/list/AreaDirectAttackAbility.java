package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class AreaDirectAttackAbility extends Ability {
	private double radius;
	private boolean attacking;
	
	public AreaDirectAttackAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 1d);
	}
	
	public AreaDirectAttackAbility(Tier unlock, Tier.Specification tierSpecification, Double radius) {
		super(unlock, tierSpecification);
		this.radius = radius;
		this.attacking = false;
	}
	
	@ActionHandler
	public void damageAreaAbility(EntityDirectAttackAction action) {
		if (this.attacking)
			return;
		
		this.attacking = true;
		Location victimLocation = action.getVictim().getLoc();
		LivingEntity entity = (LivingEntity) action.getEntity();
		LivingEntity testing;
		for (Entity target : action.getEntity().getLevel().getEntities()) {
			if (target instanceof LivingEntity
					&& (testing = (LivingEntity) target) != action.getVictim()
					&& entity.isEnemy(testing)
					&& victimLocation.distance(target.getLoc()) <= this.radius)
				action.getEntity().attack(testing);
		}
		this.attacking = false;
	}
}
