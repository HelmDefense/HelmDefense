package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.abilities.AbilityAction;
import fr.helmdefense.model.entities.abilities.actions.entity.EntityDamageAction;
import fr.helmdefense.model.entities.utils.Tier;

public class AreaDirectDamageAbility extends Ability {

	public AreaDirectDamageAbility(Tier unlock) {
		super(unlock);
		
	}
	
	@AbilityAction
	public void dmgAreaAbitily(EntityDamageAction action) {
		
	}

}
