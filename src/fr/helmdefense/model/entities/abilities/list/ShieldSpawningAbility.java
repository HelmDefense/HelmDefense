package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public class ShieldSpawningAbility extends Ability {
	private int factor;

	public ShieldSpawningAbility(Tier unlock, Integer factor) {
		super(unlock);
		this.factor = factor;
	}
	
	@ActionHandler
	public void onSpawnAction(EntitySpawnAction action) {
		LivingEntity source = (LivingEntity) action.getEntity();
		if(!source.hasShield()) {
			source.gainHp((int) action.getEntity().stat(Attribute.HP) / factor, false);
		}
	}

}
