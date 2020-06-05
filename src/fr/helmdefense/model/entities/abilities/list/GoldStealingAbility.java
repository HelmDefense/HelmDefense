package fr.helmdefense.model.entities.abilities.list;

import java.util.ArrayList;
import java.util.List;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class GoldStealingAbility extends Ability {
	private List<Integer> costs;
	
	public GoldStealingAbility(Tier unlock, Tier.Specification tierSpecification, ArrayList<Integer> costs) {
		super(unlock, tierSpecification);
		this.costs = costs;
	}
	
	@ActionHandler
	public void onEntityKillAction(EntityKillAction action) {
		int entityTier = action.getEntity().data().getTier().getNumberTier();
		if (entityTier > 0 || entityTier < 3)
			action.getEntity().getLevel().debit(this.costs.get(entityTier - 1));
	}
}