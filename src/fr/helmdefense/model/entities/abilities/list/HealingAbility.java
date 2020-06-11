package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class HealingAbility extends Ability {
	private Double healingPercentage;
	private int timeBetweenHeal;
	private long start;
	private LivingEntity entity;
	
	public HealingAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 0.05d, 10);
	}
	
	public HealingAbility(Tier unlock, Tier.Specification tierSpecification, Double healingPercentage, Integer timeBetweenHeal) {
		super(unlock, tierSpecification);
		this.healingPercentage = healingPercentage;
		this.timeBetweenHeal = timeBetweenHeal;
		this.start = -1;
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity) action.getEntity();
			this.start = action.getEntity().getLevel().getGameloop().getTicks();
		}
	}
	
	@ActionHandler
	public void heal(GameTickAction action) {
		if (this.entity == null || this.start == -1 || (action.getTicks() - this.start) % this.timeBetweenHeal != 0)
			return;
		
		Location loc = this.entity.getLoc();
		for (Entity e : action.getLvl().getEntities())
			if (e instanceof LivingEntity
					&& ((LivingEntity) e).getType().getSide() == this.entity.getType().getSide()
					&& e.getLoc().distance(loc) <= this.entity.stat(Attribute.SHOOT_RANGE))
				((LivingEntity) e).gainHp((int) (e.stat(Attribute.HP) * this.healingPercentage));
	}
}