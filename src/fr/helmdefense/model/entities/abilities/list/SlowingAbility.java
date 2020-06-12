package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class SlowingAbility extends Ability {
	private int effectDuration;
	private double slowingPercentage;
	private long start;
	private LivingEntity entity;
	
	public SlowingAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 0.2d, 30);
	}
	
	public SlowingAbility(Tier unlock, Tier.Specification tierSpecification, Double slowingPercentage, Integer effectDuration) {
		super(unlock, tierSpecification);
		this.slowingPercentage = slowingPercentage;
		this.start = -1;
		this.effectDuration = effectDuration;
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity) action.getEntity();
			this.start = action.getEntity().getLevel().getGameloop().getTicks();
		}
	}
	
	@ActionHandler
	public void slow(GameTickAction action) {
		if (this.entity == null || this.start == -1)
			return;
		
		Location loc = this.entity.getLoc();
		AttributeModifier mod;
		for (Entity target : action.getLvl().getEntities()) {
			if (target instanceof LivingEntity
					&& ((LivingEntity) target).isEnemy(this.entity)
					&& loc.distance(target.getLoc()) <= this.entity.stat(Attribute.ATK_RANGE)) {
				if ((mod = target.getModifier(this.getClass().getSimpleName())) == null)
					target.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName(), Attribute.ATK_SPD, Operation.MULTIPLY, - this.slowingPercentage, action.getTicks(), this.effectDuration));	
				else
					mod.setStart(action.getTicks());
			}
		}
	}
}
