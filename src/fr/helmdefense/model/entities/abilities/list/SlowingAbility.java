package fr.helmdefense.model.entities.abilities.list;

import java.util.ArrayList;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
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
	ArrayList<Double> radius;
	private LivingEntity entity;
	
	
	public SlowingAbility(Tier unlock, Tier.Specification tierSpecification, ArrayList<Double> radius) {
		this(unlock, tierSpecification, 0.2d, 30, radius);
	}
	
	public SlowingAbility(Tier unlock, Tier.Specification tierSpecification, Double slowingPercentage, Integer effectDuration, ArrayList<Double> radius) {
	super(unlock, tierSpecification);
	this.radius = new ArrayList<Double>(radius);
	this.slowingPercentage = slowingPercentage;
	this.start = -1;
	this.effectDuration = effectDuration;
	this.entity = null;
	
	if ( this.radius.isEmpty() || this.radius == null)
		for (int i = 0; i < 3; i++) {
			radius.add(3d + 1/2*i);
		}
	}
	
	@ActionHandler
	private void onSpawn(EntitySpawnAction action) {
		if ( action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity)action.getEntity();
			this.start = action.getEntity().getLevel().getTicks();
		}
	}
	
	@ActionHandler
	private void slow(GameTickAction action) {
		Location loc = this.entity.getLoc();
		double radius = this.radius.get(this.entity.data().getTier().getNumberTier() - 1);
		EntitySide side = this.entity.getType().getSide();
		
		for (Entity target : action.getLvl().getEntities().filtered(e -> e instanceof LivingEntity)) {
				if ( this.start != -1 && ((LivingEntity)target).getType().getSide().isEnemy(side) && loc.distance(target.getLoc()) <= radius )
					if ( target.getModifier(this.getClass().getSimpleName()) != null )
						target.getModifier(this.getClass().getSimpleName()).setStart(action.getTicks());
					else
						target.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName(), Attribute.MVT_SPD,
								Operation.MULTIPLY, - this.slowingPercentage, action.getTicks(), this.effectDuration));	
		}
	}
}
