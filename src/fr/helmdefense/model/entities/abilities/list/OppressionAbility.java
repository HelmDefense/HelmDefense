package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;

public class OppressionAbility extends Ability {
	private int duration;
	private double value;
	
	public OppressionAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 10);
	}
	
	public OppressionAbility(Tier unlock, Tier.Specification tierSpecification, Integer duration) {
		this(unlock, tierSpecification, duration, -0.5d);
	}
	
	public OppressionAbility(Tier unlock, Tier.Specification tierSpecification, Double value) {
		this(unlock, tierSpecification, 10, value);
	}
	
	public OppressionAbility(Tier unlock, Tier.Specification tierSpecification, Integer duration, Double value) {
		super(unlock, tierSpecification);
		this.duration = duration;
		this.value = value;
	}
	
	@ActionHandler
	public void onDirectAttack(EntityDirectAttackAction action) {
		Entity source = action.getEntity();
		AttributeModifier modifier = source.getModifier(this.getClass().getSimpleName());
		if (modifier == null) {
			modifier = new AttributeModifier(this.getClass().getSimpleName(), Attribute.ATK_SPD, Operation.MULTIPLY, this.value, source.getLevel().getTicks(), this.duration);
			source.getModifiers().add(modifier);
		}
		else
			modifier.setStart(source.getLevel().getTicks());
	}
}