package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public class SuicideBombingAbility extends AreaAttackAbility {
	public SuicideBombingAbility(Tier unlock, Tier.Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void onDirectAttackAction(EntityDirectAttackAction action) {
		if (action.getEntity() instanceof LivingEntity) {
			LivingEntity source = (LivingEntity) action.getEntity();
			this.areaAttackAbility(source.getLoc(), source, source.stat(Attribute.SHOOT_RANGE), source.getType().getSide(), action.getVictim());
			source.looseHp(source.getHp(), source, true);
		}
	}
}