package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
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
	
	public void rangeBonus(Entity entity) {
		AttributeModifier rangeBonus = entity.getModifier(this.getClass().getSimpleName());
		if ( rangeBonus == null)
			rangeBonus = new AttributeModifier(Attribute.SHOOT_RANGE, Operation.ADD, this.range);
	}

}
