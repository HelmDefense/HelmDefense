package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class GoldStealingAbility extends Ability {
	public GoldStealingAbility(Tier unlock) {
		super(unlock);
	}
	
	@ActionHandler
	public void onEntityKillAction(EntityKillAction action) {
		action.getEntity().getLevel().debit(3);
		}
}
