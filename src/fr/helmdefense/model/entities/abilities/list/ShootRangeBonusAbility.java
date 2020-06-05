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
		AttributeModifier rangeBonus = action.getEntity().getModifier(this.getClass().getSimpleName());
		if ( rangeBonus == null)
			rangeBonus = new AttributeModifier("ShootRangeBonusAbility", Attribute.SHOOT_RANGE, Operation.ADD, this.range);
		action.getEntity().getModifiers().add(rangeBonus);
		*/
	}

}
