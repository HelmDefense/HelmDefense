package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public class BountyHuntingAbility extends Ability {
	public BountyHuntingAbility(Tier unlock, Tier.Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void onEntityKillAction(EntityKillAction action) {
		action.getEntity().getLevel().earnCoins((int) action.getVictim().stat(Attribute.REWARD));
	}
}