package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;

public class EntitySummoningAbility extends Ability {
	private int timeBetweenSpawn;
	private long start;
	private int numberOfEntitySpawnedOnDeath;
	private LivingEntity entity;
	private LivingEntityType type;
	
	public EntitySummoningAbility(Tier unlock, Tier.Specification tierSpecification, String entityName) {
		this(unlock, tierSpecification, 20, entityName, 10);
	}
	
	public EntitySummoningAbility(Tier unlock, Tier.Specification tierSpecification, Integer timeBetweenSpawn, String entityName, Integer numberOfEntitySpawnedOnDeath) {
		super(unlock, tierSpecification);
		this.timeBetweenSpawn = timeBetweenSpawn;
		this.type = LivingEntityType.valueOf(entityName.toUpperCase());
		this.start = -1;
		this.numberOfEntitySpawnedOnDeath = numberOfEntitySpawnedOnDeath;
		}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity) action.getEntity();
			this.start = this.entity.getLevel().getGameloop().getTicks();
		}
	}
	
	@ActionHandler
	public void onDeath(LivingEntityDeathAction action) {
		Location loc = action.getEntity().getLoc();
		Level lvl = action.getEntity().getLevel();
		for (int i = 0; i < this.numberOfEntitySpawnedOnDeath; i++) {
			LivingEntity entity = new LivingEntity(this.type, loc);
			entity.spawn(lvl);
			lvl.getCurrentWave().addAlreadySpawnedEntity(entity);
		}
	}
	
	@ActionHandler
	public void summonEntity(GameTickAction action) {
		if (this.start != -1 && (action.getTicks() - this.start) % this.timeBetweenSpawn == 0) {
			LivingEntity entity = new LivingEntity(this.type, this.entity.getLoc());
			entity.spawn(action.getLvl());
			action.getLvl().getCurrentWave().addAlreadySpawnedEntity(entity);
		}
	}
}
