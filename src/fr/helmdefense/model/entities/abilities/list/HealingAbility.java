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
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class HealingAbility extends Ability {
	ArrayList<Double> radius;
	private Double healingPercentage;
	private int timeBetweenHeal;
	private long start;
	private LivingEntity entity;
	
	public HealingAbility(Tier unlock, Tier.Specification tierSpecification, ArrayList<Double> radius) {
		this(unlock, tierSpecification, 0.05d, 10, radius);
	}
	
	public HealingAbility(Tier unlock, Tier.Specification tierSpecification, Double healingPercentage, Integer timeBetweenHeal, ArrayList<Double> radius) {
		super(unlock, tierSpecification);
		this.radius = new ArrayList<Double>(radius);
		this.healingPercentage = healingPercentage;
		this.timeBetweenHeal = timeBetweenHeal;
		this.entity = null;
		this.start = -1;
		if ( this.radius.isEmpty() || this.radius == null) {
			for (int i = 0; i < 3; i++) 
				this.radius.add((3d+1/2*i));
		}
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if ( action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity)action.getEntity();
			this.start = action.getEntity().getLevel().getTicks();
		}
	}
	
	@ActionHandler
	public void heal(GameTickAction action) {
		EntitySide side = this.entity.getType().getSide();
		Location loc = this.entity.getLoc();
		double radius = this.radius.get(this.entity.data().getTier().getNumberTier() - 1);
		if ( this.start != -1 && (action.getTicks() - this.start) % this.timeBetweenHeal == 0) 
			for (Entity e : action.getLvl().getEntities()) {
				if (e instanceof LivingEntity && ((LivingEntity)e).getType().getSide() == side && e.getLoc().distance(loc) <= radius)
					((LivingEntity)e).gainHp((int)(e.stat(Attribute.HP)*this.healingPercentage));
			}
	}
}
