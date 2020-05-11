package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityProjectileAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class AreaProjectileDamageAbility extends Ability {

	public AreaProjectileDamageAbility(Tier unlock) {
		super(unlock);
	}
	
	@ActionHandler
	public void dmgAreaAbility(EntityProjectileAttackAction action) {
		
	}

}
