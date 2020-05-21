package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.projectiles.Projectile;

public class EntityProjectileAttackAction extends EntityAction {
	private LivingEntity victim;
	private Projectile proj;
	private int hpBefore;
	
	public EntityProjectileAttackAction(LivingEntity entity, LivingEntity victim, Projectile proj, int hpBefore) {
		super(entity);
		this.victim = victim;
		this.proj = proj;
	}
	
	public Entity getVictim() {
		return this.victim;
	}
	
	public Projectile getProj() {
		return this.proj;
	}
	
	public int getHpBefore() {
		return this.hpBefore;
	}
	
	public int getDmg() {
		return this.victim.getHp() - this.hpBefore;
	}
}