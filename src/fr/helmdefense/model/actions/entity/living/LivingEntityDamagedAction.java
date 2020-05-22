package fr.helmdefense.model.actions.entity.living;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;

public class LivingEntityDamagedAction extends LivingEntityAction {
	private Entity attacker;
	private int hpBefore;
	
	public LivingEntityDamagedAction(LivingEntity entity, Entity attacker, int hpBefore) {
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
		return this.getEntity().getHp() - this.hpBefore;
	}
}