package fr.helmdefense.model.entities.abilities.list;

import java.util.ArrayList;
import java.util.List;

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
	private List<Double> radius;
	private LivingEntity entity;
	
	
	public SlowingAbility(Tier unlock, Tier.Specification tierSpecification, ArrayList<Double> radius) {
		this(unlock, tierSpecification, 0.2d, 30, radius);
	}
	
	public SlowingAbility(Tier unlock, Tier.Specification tierSpecification, Double slowingPercentage, Integer effectDuration, ArrayList<Double> radius) {
		super(unlock, tierSpecification);
		this.radius = radius;
		this.slowingPercentage = slowingPercentage;
		this.start = -1;
		this.effectDuration = effectDuration;
	}
	
	@ActionHandler
	private void onSpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity) action.getEntity();
			this.start = action.getEntity().getLevel().getGameloop().getTicks();
		}
	}
	
	@ActionHandler
	private void slow(GameTickAction action) {
		if (this.start == -1)
			return;
		
		Location loc = this.entity.getLoc();
		AttributeModifier mod;
		for (Entity target : action.getLvl().getEntities()) {
			if (target instanceof LivingEntity
					&& ((LivingEntity) target).getType().getSide().isEnemy(this.entity.getType().getSide())
					&& loc.distance(target.getLoc()) <= this.radius.get(this.entity.data().getTier().getNumberTier() - 1))
				if ((mod = target.getModifier(this.getClass().getSimpleName())) == null)
					target.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName(), Attribute.ATK_SPD, Operation.MULTIPLY, - this.slowingPercentage, action.getTicks(), this.effectDuration));	
				else
					mod.setStart(action.getTicks());
		}
	}
}
