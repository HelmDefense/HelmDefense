package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.attackers.Attacker;
import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Tier;

public class DirectAttackAbility extends Ability {
	private Entity entity;
	private long lastAtk;
	
	public DirectAttackAbility(Tier unlock) {
		super(unlock);
		this.lastAtk = 0;
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		this.entity = action.getEntity();
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.entity == null)
			return;
		
		Entity enemy = this.getClosestEnemy();
		
		if (enemy != null
				&& enemy.getLoc().distance(this.entity.getLoc()) < this.entity.data().getStats(Tier.TIER_1).getAtkRange() + 0.5
				&& action.getTicks() - this.lastAtk > 10 / this.entity.data().getStats(Tier.TIER_1).getAtkSpd()) {
			this.lastAtk = action.getTicks();
			this.entity.attack(enemy);
		}
	}
	
	private Entity getClosestEnemy() {
		Entity closest = null;
		double dMax = Double.MAX_VALUE, d;
		for (Entity entity : this.entity.getLevel().getEntities()) {
			if ((this.entity instanceof Defender ? entity instanceof Attacker : entity instanceof Defender)
					&& (d = entity.getLoc().distance(this.entity.getLoc())) < dMax) {
				dMax = d;
				closest = entity;
			}
		}
		return closest;
	}
}