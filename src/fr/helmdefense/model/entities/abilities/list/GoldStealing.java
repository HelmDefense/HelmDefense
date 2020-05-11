package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class GoldStealing extends Ability {
	public GoldStealing() {
		super(Tier.TIER_1);
	}
	
	@ActionHandler
	public void onEntityKillAction(EntityKillAction action) {
		// Bourse = Math.max(bourse - 3,6,9 ( selon tier ), 0);
	}
}
