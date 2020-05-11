package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class BurnAbility extends Ability {

	public BurnAbility(Tier unlock) {
		super(unlock);
	}
	
	@ActionHandler
	public void effectBurnAbility(EntityDirectAttackAction action) {
		
	}

}
