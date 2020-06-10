package fr.helmdefense.model.entities.living.special;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameLooseAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;

public class Door extends LivingEntity {
	private boolean isFinalDoor;
	private List<LivingEntity> entityMarked;

	public Door(LivingEntityType type, Location loc, int hp, Level lvl, boolean isFinalDoor) {
		super(type, loc);
		this.setHp(hp);
		this.isFinalDoor = isFinalDoor;
		this.entityMarked = new ArrayList<LivingEntity>();
		this.addAbilities(new DeathAbility());
		
		this.spawn(lvl);
	}
	
	@ActionHandler
	public void setImmobile(GameTickAction action) {
		for (Entity entity : action.getLvl().getEntities()) {
			if (entity instanceof LivingEntity
					&& entity.getHitbox().overlaps(this.getHitbox())) {
				((LivingEntity) entity).addFlags(LivingEntity.IMMOBILE);
				entityMarked.add((LivingEntity) entity);
			}
		}
	}
	
	public class DeathAbility extends Ability {
		
		public DeathAbility() {
			super(Tier.DEFAULT, Tier.Specification.DEFAULT);
		}

		@ActionHandler
		public void onEntityDeath(LivingEntityDeathAction action) {
			Iterator<LivingEntity> it = entityMarked.iterator();
			while (it.hasNext()) {
				LivingEntity entity = it.next();
				entity.removeFlags(LivingEntity.IMMOBILE);
				it.remove();
			}
			if (isFinalDoor) {
				GameLooseAction looseAction = new GameLooseAction(getLevel());
				
				getLevel().end();
				
				Actions.trigger(looseAction);
			}
		}
		
	}
	
}
