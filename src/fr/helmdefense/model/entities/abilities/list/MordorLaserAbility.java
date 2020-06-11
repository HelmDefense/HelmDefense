package fr.helmdefense.model.entities.abilities.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Hitbox;

public class MordorLaserAbility extends Ability {
	private int fireDuration;
	private int damages;
	private int ticksBetweenTwoAttacks;
	private double radius;
	private long lastAttack;
	private LivingEntity entity;
	private ArrayList<Double> radiusList;
	private Map<LivingEntity, Long> map;
	
	public MordorLaserAbility(Tier unlock, Tier.Specification tierSpecification, Integer damages, Integer ticksBetweenTwoAttacks, Integer fireDuration, ArrayList<Double> radius) {
		super(unlock, tierSpecification);
		this.ticksBetweenTwoAttacks = ticksBetweenTwoAttacks;
		this.map = new HashMap<LivingEntity, Long>();
		this.radiusList = radius;
		this.damages = damages;
		this.lastAttack = -1;
		this.radius = -1;
		this.fireDuration = fireDuration;
	}

	@ActionHandler
	private void onSpawn(EntitySpawnAction action) {
		if( action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity)action.getEntity();
			this.radius = this.radiusList.get(this.entity.data().getTier().getNumberTier() - 1);
			this.lastAttack = action.getEntity().getLevel().getTicks();
		}
	}
	
	@ActionHandler
	private void attack(GameTickAction action) {
		if ( this.radius == -1 || this.lastAttack == -1 )
			return;
		long ticks = action.getTicks();
		if ( ticks - ticksBetweenTwoAttacks >= this.lastAttack ) {
			LivingEntity victim = (LivingEntity) action.getLvl().getEntities()
					.stream().
					filter(e -> e instanceof LivingEntity && e.getLoc().distance(this.entity.getLoc()) <= this.radius).findFirst().get();
			attack(victim, ticks);
			
		List<Hitbox> hitboxes = new ArrayList<Hitbox>();
			
			Hitbox laser = new Hitbox(entity.getLoc(), victim.getLoc());
			for (Entity entityOnTheLine : action.getLvl().getEntities())
				if (entityOnTheLine instanceof LivingEntity && entityOnTheLine.getHitbox().overlaps(laser)) 
					attack((LivingEntity)entityOnTheLine, ticks);
		} 
		
		Iterator<Entry<LivingEntity, Long>> it = this.map.entrySet().iterator();
		while ( it.hasNext()) {
			Entry<LivingEntity, Long> entry = it.next();
			if ( ticks + entry.getValue() >= this.fireDuration ) {
				entry.getKey().removeFlags(LivingEntity.FIRE);
				it.remove();
			}
		}
		this.lastAttack = ticks;
	}

	public void attack(LivingEntity victim,  long ticks) {
		victim.looseHp(this.damages, this.entity);
		victim.addFlags(LivingEntity.FIRE);
		this.map.put(victim, ticks);
	}
}
