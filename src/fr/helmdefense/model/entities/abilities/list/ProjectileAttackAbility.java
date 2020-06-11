package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.projectile.ProjectileType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public class ProjectileAttackAbility extends AttackAbility {
	private double speed;
	private ProjectileType projectile;
	
	public ProjectileAttackAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, -1d);
	}
	
	public ProjectileAttackAbility(Tier unlock, Tier.Specification tierSpecification, Double speed) {
		this(unlock, tierSpecification, "ARROW", speed);
	}
	
	public ProjectileAttackAbility(Tier unlock, Tier.Specification tierSpecification, String projectile) {
		this(unlock, tierSpecification, projectile, -1d);
	}
	
	public ProjectileAttackAbility(Tier unlock, Tier.Specification tierSpecification, Double speed, String projectile) {
		this(unlock, tierSpecification, projectile, speed);
	}
	
	public ProjectileAttackAbility(Tier unlock, Tier.Specification tierSpecification, String projectile, Double speed) {
		super(unlock, tierSpecification);
		this.speed = speed;
		this.projectile = ProjectileType.valueOf(projectile.toUpperCase());
	}

	@Override
	protected void attack(LivingEntity enemy) {
		new Projectile(this.projectile, this.entity, enemy.getLoc(), this.speed).spawn(this.entity.getLevel());
	}
	
	@Override
	protected boolean canAttack(LivingEntity enemy, double distance) {
		return distance < this.entity.stat(Attribute.SHOOT_RANGE);
	}
	
	@Override
	protected void init() {
		if (this.speed == -1)
			this.speed = this.projectile.getData().getStats().get(Attribute.MVT_SPD);
	}
}