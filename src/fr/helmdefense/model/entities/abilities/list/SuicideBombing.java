package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class SuicideBombing extends Ability {

	public SuicideBombing(Tier unlock, Tier.Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void onDirectAttackAction(EntityDirectAttackAction action) {
		// action.getEntity().die();
	}

}
