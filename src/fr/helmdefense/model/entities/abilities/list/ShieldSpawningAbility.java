package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public class ShieldSpawningAbility extends Ability {
	private double factor;

	public ShieldSpawningAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 0.5d);
	}
	
	public ShieldSpawningAbility(Tier unlock, Tier.Specification tierSpecification, Double factor) {
		super(unlock, tierSpecification);
		this.factor = factor;
	}
	
	@ActionHandler
	public void onSpawnAction(EntitySpawnAction action) {
		LivingEntity source = (LivingEntity) action.getEntity();
		if (! source.hasShield()) {
			source.gainHp((int) (action.getEntity().stat(Attribute.HP) * this.factor), false);
		}
	}
}