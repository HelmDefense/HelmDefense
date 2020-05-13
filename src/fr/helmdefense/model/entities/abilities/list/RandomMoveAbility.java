package fr.helmdefense.model.entities.abilities.list;

import java.util.Random;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.level.GameLoop;
import fr.helmdefense.model.map.GameMap;

public class RandomMoveAbility extends Ability {
	private Entity entity;
	
	public RandomMoveAbility(Tier unlock, Entity entity) {
		super(unlock);
		this.entity = entity;
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		this.entity.teleport(this.entity.getLoc()
				.add(randomMove(), randomMove()));
		if (! this.entity.getLoc().isInMap())
			this.entity.teleport(GameMap.WIDTH / 2d, GameMap.HEIGHT / 2d);
	}
	
	public double randomMove() {
		return randomFactor() * Entities.getData(this.entity.getClass()).getStats(Tier.TIER_1).getMvtSpd() * GameLoop.TPS;
	}
	
	public int randomFactor() {
		return new Random().nextInt(3) - 1;
	}
}