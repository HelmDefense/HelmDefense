package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;

public class AreaDirectAttackAbility extends AreaAttackAbility {
	private double radius;
	
	public AreaDirectAttackAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 1d);
	}
	
	public AreaDirectAttackAbility(Tier unlock, Tier.Specification tierSpecification, Double radius) {
		super(unlock, tierSpecification);
		this.radius = radius;
	}
	
	@ActionHandler
	public void damageAreaAbility(EntityDirectAttackAction action) {
		Entity entity = action.getEntity();
		LivingEntity victim = action.getVictim();
		this.areaAttackAbility(victim.getLoc(), entity, this.radius, ((LivingEntity) entity).getType().getSide(), victim);
	}
}