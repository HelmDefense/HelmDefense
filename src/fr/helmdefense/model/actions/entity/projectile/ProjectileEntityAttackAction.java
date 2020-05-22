package fr.helmdefense.model.actions.entity.projectile;

import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.projectiles.Projectile;

public class ProjectileEntityAttackAction extends ProjectileEntityAction {
	private LivingEntity victim;
	private int hpBefore;
	
	public ProjectileEntityAttackAction(Projectile projectile, LivingEntity victim, int hpBefore) {
		super(projectile);
		this.victim = victim;
	}
	
	public LivingEntity getVictim() {
		return this.victim;
	}
	
	public int getHpBefore() {
		return this.hpBefore;
	}
	
	public int getDmg() {
		return this.victim.getHp() - this.hpBefore;
	}
}