package fr.helmdefense.model.entities.living.special;

import java.util.HashSet;
import java.util.Set;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;

public class Door extends LivingEntity {
	private int initialHp;
	private Set<LivingEntity> entityMarked;

	public Door(Location loc, int hp) {
		super(LivingEntityType.DOOR, loc);
		this.initialHp = hp;
		this.entityMarked = new HashSet<LivingEntity>();
	}
	
	public Door(double x, double y, int hp) {
		this(new Location(x, y), hp);
	}
	
	@Override
	public void spawn(Level lvl) {
		super.spawn(lvl);
		this.setHp(this.initialHp);
	}
	
	@ActionHandler
	public void setImmobile(GameTickAction action) {
		if (! this.isAlive())
			return;
		
		LivingEntity testing;
		for (Entity entity : action.getLvl().getEntities()) {
			if (entity instanceof LivingEntity
					&& (testing = (LivingEntity) entity).getType().getSide() == EntitySide.ATTACKER
					&& entity.getHitbox().overlaps(this.getHitbox())) {
				testing.addFlags(LivingEntity.IMMOBILE);
				this.entityMarked.add(testing);
			}
		}
	}
	
	@ActionHandler
	public void onEntityDeath(LivingEntityDeathAction action) {
		this.entityMarked.forEach(entity -> entity.removeFlags(LivingEntity.IMMOBILE));
	}
	
	public int getInitialHp() {
		return this.initialHp;
	}
}