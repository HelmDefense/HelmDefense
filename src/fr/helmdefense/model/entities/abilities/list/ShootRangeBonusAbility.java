package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class ShootRangeBonusAbility extends Ability {
	private double range;
	
	public ShootRangeBonusAbility(Tier unlock, Specification tierSpecification) {
		this(unlock, tierSpecification, 10d);
	}
	
	public ShootRangeBonusAbility(Tier unlock, Specification tierSpecification, Double range) {
		super(unlock, tierSpecification);
		this.range = range;
	}
	
	@ActionHandler
	public void rangeBonus(/* EntityUpgradeAction action */ ) {
		/*
		action.getEntity().getModifiers().add(new AttributeModifier(Attribute.SHOOT_RANGE, Operation.ADD, this.range);
		*/
	}

}
