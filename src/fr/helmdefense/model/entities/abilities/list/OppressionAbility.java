package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class OppressionAbility extends Ability {
	//private int duration;
	public OppressionAbility(/*int duration*/) {
		super(Tier.TIER_3);
		//this.duration = duration;
	}
	
	@ActionHandler
	public void onDirectAttack(EntityDirectAttackAction action) {
		// TODO : Appliquer un effet de slowness pendant duration sec à la victime 
	}

}
