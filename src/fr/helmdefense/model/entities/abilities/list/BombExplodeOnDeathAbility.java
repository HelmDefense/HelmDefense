package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class BombExplodeOnDeathAbility extends AreaAttackAbility {
	public BombExplodeOnDeathAbility(Tier unlock, Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void onLivingEntityDeathAction(LivingEntityDeathAction action) {
		LivingEntity source = action.getEntity();
		if (action.getAttacker() != source)
			this.areaAttackAbility(source.getLoc(), source, source.stat(Attribute.SHOOT_RANGE), source.getType().getSide());
	}
}