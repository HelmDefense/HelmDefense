package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class BombExplodeOnDeathAbility extends AreaAttackAbility {
	public BombExplodeOnDeathAbility(Tier unlock, Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	public void onLivingEntityDeathAction(LivingEntityDeathAction action) {
		LivingEntity source = (LivingEntity) action.getEntity();
		if (action.getAttacker() != action.getEntity())
			this.areaAttackAbility(source.getLoc(), source, source.stat(Attribute.SHOOT_RANGE), source.getType().getSide());
	}
}