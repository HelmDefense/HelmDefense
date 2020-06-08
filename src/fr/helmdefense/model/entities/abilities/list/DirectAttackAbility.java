package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public class DirectAttackAbility extends AttackAbility {
	public DirectAttackAbility(Tier unlock, Tier.Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@Override
	protected void attack(LivingEntity enemy) {
		this.entity.attack(enemy);	
	}

	@Override
	protected boolean canAttack(LivingEntity enemy, double distance) {
		return distance < this.entity.stat(Attribute.ATK_RANGE);
	}
}