package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Tier;

public class EntitySummoningAbility extends Ability {
	private int timeBetweenSpawn;
	private long start;
	private LivingEntity entity;
	private LivingEntityType type;
	
	public EntitySummoningAbility(Tier unlock, Tier.Specification tierSpecification, String entityName) {
		this(unlock, tierSpecification, 20, entityName);
	}
	
	public EntitySummoningAbility(Tier unlock, Tier.Specification tierSpecification, Integer timeBetweenSpawn, String entityName) {
		super(unlock, tierSpecification);
		this.timeBetweenSpawn = timeBetweenSpawn;
		this.type = LivingEntityType.valueOf(entityName.toUpperCase());
		this.start = -1;
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if ( action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity)action.getEntity();
			this.start = this.entity.getLevel().getTicks();
			}
		}
	
	@ActionHandler
	public void summonEntity(GameTickAction action) {
		if (this.start != -1 && (action.getTicks() - this.start) % this.timeBetweenSpawn == 0)
			new LivingEntity(this.type, this.entity.getLoc()).spawn(action.getLvl());
	}
}
