package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityKillAction;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityHeroPowerAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class KillingSpreeHealingAbility extends Ability {
	private LivingEntity entity;
	private int killed;
	private long killingSpreeStart;
	private int healPerKill;
	private int duration;
	private double radius;

	public KillingSpreeHealingAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 100, 100, 6d);
	}

	public KillingSpreeHealingAbility(Tier unlock, Tier.Specification tierSpecification, Integer healPerKill, Integer duration, Double radius) {
		super(unlock, tierSpecification);
		this.healPerKill = healPerKill;
		this.duration = duration;
	  	this.killingSpreeStart = -1;
	  	this.radius = radius;
	}

	@ActionHandler
	public void onEntitySpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity)
			this.entity = (LivingEntity) action.getEntity();
	}

	@ActionHandler
	public void onEntityKill(EntityKillAction action) {
		if (this.killingSpreeStart != -1)
			this.killed++;
	}

	@ActionHandler
	public void onLivingEntityHeroPower(LivingEntityHeroPowerAction action) {
		this.killingSpreeStart = action.getEntity().getLevel().getGameloop().getTicks();
		this.killed = 0;
	}

	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.entity == null || this.killingSpreeStart == -1 || action.getTicks() - this.killingSpreeStart < this.duration)
			return;

		this.killingSpreeStart = -1;
		int heal = this.killed * this.healPerKill;

		LivingEntity ent;
		Location loc = this.entity.getLoc();
		for (Entity e : this.entity.getLevel().getEntities())
			if (e instanceof LivingEntity
					&& (ent = (LivingEntity) e).getType().getSide() == this.entity.getType().getSide()
					&& e.getLoc().distance(loc) < this.radius)
				ent.gainHp(heal, false);
	}
}
