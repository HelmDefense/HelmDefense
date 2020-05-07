package fr.helmdefense.model.entities.abilities.actions.entity;

import fr.helmdefense.model.entities.Entity;

public class EntityDamagedAction extends EntityAction {
	private Entity attacker;
	private int hpBefore;
	
	public EntityDamagedAction(Entity entity, Entity attacker, int hpBefore) {
		super(entity);
		this.attacker = attacker;
		this.hpBefore = hpBefore;
	}
	
	public Entity getAttacker() {
		return this.attacker;
	}
	
	public int getHpBefore() {
		return this.hpBefore;
	}
	
	public int getDmg() {
		return this.attacker.getHp() - this.hpBefore;
	}
}