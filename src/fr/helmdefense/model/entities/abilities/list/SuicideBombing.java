package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.abilities.AbilityAction;
import fr.helmdefense.model.entities.abilities.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.utils.Tier;

public class SuicideBombing extends Ability {

	public SuicideBombing() {
		super(Tier.TIER_1);
	}
	
	@AbilityAction
	public void onDirectAttackAction(EntityDirectAttackAction action) {
		// action.getEntity().die();
	}

}
