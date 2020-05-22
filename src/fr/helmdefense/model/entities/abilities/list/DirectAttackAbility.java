package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;

public class DirectAttackAbility extends AttackAbility {
	public DirectAttackAbility(Tier unlock) {
		super(unlock);
	}
	
	@Override
	protected void attack(LivingEntity enemy) {
		this.entity.attack(enemy);	
	}
	
	@Override
	protected void init() {
		this.range = this.entity.data().getStats(Tier.TIER_1).getAtkRange();
	}
}