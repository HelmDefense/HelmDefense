package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.projectiles.Projectile;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.Tier;

public class ProjectileAttackAbility extends AttackAbility {
	private double speed;
	
	public ProjectileAttackAbility(Tier unlock) {
		this(unlock, -1d);
	}
	
	public ProjectileAttackAbility(Tier unlock, Double speed) {
		super(unlock);
		this.speed = speed;
	}

	@Override
	protected void attack(LivingEntity enemy) {
		new Projectile(this.entity, enemy.getLoc(), this.speed).spawn(this.entity.getLevel());
	}
	
	@Override
	protected void init() {
		this.range = this.entity.stat(Attribute.SHOOT_RANGE);
		if (this.speed == -1)
			this.speed = Entities.getData(Projectile.class).getStats().getMvtSpd();
	}
}