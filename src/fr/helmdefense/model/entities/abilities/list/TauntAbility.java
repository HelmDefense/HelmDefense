package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;

public class TauntAbility extends Ability {

	public TauntAbility(Tier unlock) {
		super(unlock);
	}

	@ActionHandler
	public void onEntitySpawnAction(EntitySpawnAction action) {
		if(action.getEntity() instanceof LivingEntity) {
			((LivingEntity)action.getEntity()).setTaunt(true);
		}
	}
}
