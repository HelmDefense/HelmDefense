package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;

public class TauntAbility extends Ability {

	public TauntAbility(Tier unlock, Tier.Specification tierSpecification) {
		super(unlock, tierSpecification);
	}

	@ActionHandler
	public void onEntitySpawnAction(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity) {
			((LivingEntity) action.getEntity()).setTaunt(true);
		}
	}
}
