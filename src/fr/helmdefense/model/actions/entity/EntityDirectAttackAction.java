package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;

public class EntityDirectAttackAction extends EntityAction {
	private Entity victim;
	private int hpBefore;
	
	public EntityDirectAttackAction(Entity entity, Entity victim, int hpBefore) {
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