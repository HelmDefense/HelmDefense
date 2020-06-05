package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class ShootRangeBonusAbility extends Ability {
	private double range;
	
	public ShootRangeBonusAbility(Tier unlock, Specification tierSpecification) {
		this(unlock, tierSpecification, 2d);
	}
	
	public ShootRangeBonusAbility(Tier unlock, Specification tierSpecification, Double range) {
		super(unlock, tierSpecification);
		this.range = range;
	}
	
	@ActionHandler
	public void rangeBonus(EntitySpawnAction action) {
		action.getEntity().getModifiers().add(new AttributeModifier(Attribute.SHOOT_RANGE, Operation.ADD, this.range));
	}
}