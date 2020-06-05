package fr.helmdefense.model.entities.abilities.list;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;

public class TickDamageProjectileAbility extends Ability {
	private int duration;
	private int freq;
	private int damages;
	private Entity entity;
	private Map<Long, LivingEntity> map;
	
	public TickDamageProjectileAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 30, 5, 100);
	}
	
	public TickDamageProjectileAbility(Tier unlock, Tier.Specification tierSpecification, Integer duration, Integer freq, Integer damages) {
		super(unlock, tierSpecification);
		this.duration = duration; 
		this.freq = freq;
		this.damages = damages;
		this.map = new HashMap<Long, LivingEntity>();
	}

	@ActionHandler
	public void saveEntity(EntitySpawnAction action) {
		this.entity = action.getEntity();
	}
	
	@ActionHandler
	public void onAttack(ProjectileEntityAttackAction action) {
		LivingEntity victim = action.getVictim();
		if (! victim.testFlags(LivingEntity.FIRE)) {
			this.map.put(victim.getLevel().getTicks(), victim);
			victim.addFlags(LivingEntity.FIRE);
		}
	}
	
	@ActionHandler
	public void hitOnTick(GameTickAction action) {
		long time;
		Iterator<Map.Entry<Long, LivingEntity>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Long, LivingEntity> entry = it.next();
			time = action.getTicks() - entry.getKey();
			if (time > this.duration) {
				entry.getValue().removeFlags(LivingEntity.FIRE);
				it.remove();
			}
			else if (time % this.freq == 0) 
				entry.getValue().looseHp(this.damages, this.entity, true);
		}
	}
}