package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;

public class OppressionAbility extends Ability {
	//private int duration;
	private double value;
	private int id;
	
	public OppressionAbility(Tier unlock, Tier.Specification tierSpecification, /*Integer duration, */Double value) {
		super(unlock, tierSpecification);
		//this.duration = duration;
		this.value = value;
	}
	
	@ActionHandler
	public void onDirectAttack(EntityDirectAttackAction action) {
		LivingEntity victim = action.getVictim();
		if (victim.getModifier(this.id) == null) {
			AttributeModifier modifier = new AttributeModifier(Attribute.ATK_SPD, Operation.MULTIPLY, this.value);
			this.id = modifier.getId();
			victim.getModifiers().add(modifier);
			// TODO : Appliquer un effet de slowness pendant duration sec à la victime
		}
	}
}