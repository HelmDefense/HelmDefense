package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

public class EntityDirectAttackAction extends EntityAction {
	private LivingEntity victim;
	private int hpBefore;
	
	public EntityDirectAttackAction(LivingEntity entity, LivingEntity victim, int hpBefore) {
		super(entity);
		this.victim = victim;
		this.hpBefore = hpBefore;
	}
	
	public Entity getVictim() {
		return this.victim;
	}
	
	public int getHpBefore() {
		return this.hpBefore;
	}
	
	public int getDmg() {
		return this.victim.getHp() - this.hpBefore;
	}
}