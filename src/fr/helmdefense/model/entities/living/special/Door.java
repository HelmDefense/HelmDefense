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
	private int initialHp;
	private boolean isFinalDoor;
	private List<LivingEntity> entityMarked;

	public Door(Location loc, int hp, boolean isFinalDoor) {
		super(LivingEntityType.DOOR, loc);
		this.initialHp = hp;
		this.isFinalDoor = isFinalDoor;
		this.entityMarked = new ArrayList<LivingEntity>();
		this.addAbilities(new DoorDestructedAbility());
	}
	
	public Door(double x, double y, int hp, boolean isFinalDoor) {
		this(new Location(x, y), hp, isFinalDoor);
	}
	
	@Override
	public void spawn(Level lvl) {
		super.spawn(lvl);
		this.setHp(this.initialHp);
	}
	
	@ActionHandler
	public void setImmobile(GameTickAction action) {
		for (Entity entity : action.getLvl().getEntities()) {
			if (entity instanceof LivingEntity
					&& entity.getHitbox().overlaps(this.getHitbox())) {
				((LivingEntity) entity).addFlags(LivingEntity.IMMOBILE);
				this.entityMarked.add((LivingEntity) entity);
			}
		}
	}
	
	public int getInitialHp() {
		return this.initialHp;
	}
	
	public class DoorDestructedAbility extends Ability {
		public DoorDestructedAbility() {
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
				
				Actions.trigger(looseAction);
			}
		}
	}
}