package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.projectiles.Projectile;
import fr.helmdefense.model.entities.utils.Tier;

public class ProjectileAttackAbility extends AttackAbility {

	public ProjectileAttackAbility(Tier unlock) {
		super(unlock);
	}

	@Override
	protected void attack(LivingEntity enemy) {
		new Projectile(this.entity, enemy.getLoc()).spawn(this.entity.getLevel());
	}
	
	@Override
	protected void init() {
		super.range = this.entity.data().getStats(Tier.TIER_1).getShootRange();
	}

}
