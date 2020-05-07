package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.abilities.AbilityAction;
import fr.helmdefense.model.entities.abilities.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.utils.Tier;

public class OppressionAbility extends Ability {

	public OppressionAbility() {
		super(Tier.TIER_3);
		
	}
	
	@AbilityAction
	public void oppression(EntityDirectAttackAction action) {
		
	}

}
