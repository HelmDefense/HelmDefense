package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class AttackRangeBonusAbility extends Ability {
	private double range;
	
	public AttackRangeBonusAbility(Tier unlock, Specification tierSpecification) {
		this(unlock, tierSpecification, 0.5d);
	}
	
	public AttackRangeBonusAbility(Tier unlock, Specification tierSpecification, Double range) {
		super(unlock, tierSpecification);
		this.range = range;
	}
	
	public void rangeBonus(Entity entity) {
		AttributeModifier rangeBonus = entity.getModifier(this.getClass().getSimpleName());
		if ( rangeBonus == null)
			rangeBonus = new AttributeModifier(Attribute.ATK_RANGE, Operation.ADD, this.range);
	}

}
