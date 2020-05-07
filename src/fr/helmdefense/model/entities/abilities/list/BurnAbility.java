package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.abilities.AbilityAction;
import fr.helmdefense.model.entities.abilities.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.utils.Tier;

public class BurnAbility extends Ability {

	public BurnAbility(Tier unlock) {
		super(unlock);
	}
	
	@AbilityAction
	public void effectBurnAbility(EntityDirectAttackAction action) {
		
	}

}
