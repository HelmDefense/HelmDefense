package fr.helmdefense.model.entities.abilities.list;

import java.util.Random;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.GameLoop;
import fr.helmdefense.model.map.GameMap;

public class RandomMoveAbility extends Ability {
	private Entity entity;
	
	public RandomMoveAbility(Tier unlock) {
		super(unlock);
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		this.entity = action.getEntity();
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.entity == null)
			return;
		
		this.entity.teleport(this.entity.getLoc()
				.add(randomMove(), randomMove()));
		if (! this.entity.getLoc().isInMap())
			this.entity.teleport(GameMap.WIDTH / 2d, GameMap.HEIGHT / 2d);
	}
	
	private double randomMove() {
		return randomFactor() * this.entity.data().getStats(Tier.TIER_1).getMvtSpd() * GameLoop.TPS;
	}
	
	private int randomFactor() {
		return new Random().nextInt(3) - 1;
	}
}