package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

public class EntityDamagedAction extends EntityAction {
	private LivingEntity attacker;
	private int hpBefore;
	
	public EntityDamagedAction(LivingEntity entity, LivingEntity attacker, int hpBefore) {
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