package fr.helmdefense.model.entities.projectiles;

import fr.helmdefense.model.actions.entity.EntityProjectileAttackAction;
import fr.helmdefense.model.actions.entity.EntityProjectileFailAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class Projectile extends Entity {
	private Entity source;
	
	public Projectile(Entity source, Location loc) {
		super(loc);
		this.source = source;
	}
	
	@Override
	public void attack(Entity victim) {
		EntityProjectileAttackAction attack = new EntityProjectileAttackAction(this.source, victim, this, victim.getHp());
		
		victim.looseHp((int) (this.source.data().getStats(Tier.TIER_1).getDmg() * Statistic.SHOOT_FACTOR), this.source);
		
		this.source.triggerAbilities(attack);
	}
	
	public void fail() {
		EntityProjectileFailAction action = new EntityProjectileFailAction(this.source, this);
		
		this.source.triggerAbilities(action);
	}
}