package fr.helmdefense.model.entities.abilities.list;

import java.util.Iterator;
import java.util.Map;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;

public class TickDamageProjectileAbility extends Ability {
	private int duration;
	private int freq;
	private Entity entity;
	Map<LivingEntity, Long> map;
	
	public TickDamageProjectileAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 30, 5);
	}
	
	public TickDamageProjectileAbility(Tier unlock, Tier.Specification tierSpecification, Integer duration, Integer freq) {
		super(unlock, tierSpecification);
		this.duration = duration; 
		this.freq = freq;
	}

	@ActionHandler
	public void saveEntity(EntitySpawnAction action) {
		this.entity = action.getEntity();
	}
	
	@ActionHandler
	public void onAttack(ProjectileEntityAttackAction action) {
		LivingEntity victim = action.getVictim();
		this.map.putIfAbsent(victim, victim.getLevel().getTicks());
		victim.addFlags(LivingEntity.FIRE);
	}
	
	@ActionHandler
	public void hitOnTick(GameTickAction action) {
		long time;
		Iterator<Map.Entry<LivingEntity, Long>> it = map.entrySet().iterator();
		while ( it.hasNext()) {
			Map.Entry<LivingEntity, Long> entry = it.next();
			time = action.getTicks() - entry.getValue();
			if ( time > this.duration ) {
				entry.getKey().removeFlags(LivingEntity.FIRE);
				it.remove();
			}
			else if ( time % freq == 0 ) 
				entry.getKey().looseHp((int)(this.entity.stat(Attribute.DMG)*0.2),this.entity);
		}
	}
}
