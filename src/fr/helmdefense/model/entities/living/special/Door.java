package fr.helmdefense.model.entities.living.special;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameLooseAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.coords.Location;

public class Door extends LivingEntity {
	private double effectRange;
	private int hp;
	private boolean isFinalDoor;
	
	public Door(LivingEntityType type, Location loc, Integer hp, Boolean isFinalDoor) {
		this(type, loc, 1d, hp, isFinalDoor);
	}

	public Door(LivingEntityType type, Location loc, Double effectRange, Integer hp, Boolean isFinalDoor) {
		super(type, loc);
		this.effectRange = effectRange;
		this.hp = hp;
		this.isFinalDoor = isFinalDoor;
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		for (Entity entity : action.getLvl().getEntities()) {
			if (entity instanceof LivingEntity
					&& entity.getLoc().distance(this.getLoc()) < this.effectRange) {
				((LivingEntity) entity).addFlags(LivingEntity.IMMOBILE);
			}
		}
	}
	
	@ActionHandler
	public void onEntityDeath(LivingEntityDeathAction action) {
		for (Entity entity : action.getEntity().getLevel().getEntities()) {
			if (entity instanceof LivingEntity
					&& entity.getLoc().distance(this.getLoc()) < this.effectRange) {
				((LivingEntity) entity).removeFlags(LivingEntity.IMMOBILE);
			}
		}
		if (isFinalDoor) {
			action.getEntity().triggerAbilities(new GameLooseAction(action.getEntity().getLevel()));
		}
	}
	
}
