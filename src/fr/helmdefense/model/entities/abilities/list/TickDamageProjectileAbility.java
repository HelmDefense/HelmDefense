package fr.helmdefense.model.entities.abilities.list;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;

public class TickDamageProjectileAbility extends Ability {
	private int duration;
	private int flag;
	private Map<LivingEntity, Long> map;
	
	public TickDamageProjectileAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, "FIRE", 30);
	}
	
	public TickDamageProjectileAbility(Tier unlock, Tier.Specification tierSpecification, String flag, Integer duration) {
		super(unlock, tierSpecification);
		this.duration = duration;
		if (flag.equalsIgnoreCase("FIRE"))
			this.flag = LivingEntity.FIRE;
		else if (flag.equalsIgnoreCase("POISON"))
			this.flag = LivingEntity.POISON;
		else
			this.flag = -1;
		this.map = new HashMap<LivingEntity, Long>();
	}
	
	@ActionHandler
	public void onAttack(ProjectileEntityAttackAction action) {
		if (this.flag == -1 || ! action.getEntity().getSource().isAlive())
			return;
		
		LivingEntity victim = action.getVictim();
		this.map.put(victim, victim.getLevel().getGameloop().getTicks());
		victim.addFlags(this.flag);
	}
	
	@ActionHandler
	public void hitOnTick(GameTickAction action) {
		if (this.flag == -1)
			return;
		
		Iterator<Map.Entry<LivingEntity, Long>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<LivingEntity, Long> entry = it.next();
			if (action.getTicks() - entry.getValue() > this.duration) {
				entry.getKey().removeFlags(this.flag);
				it.remove();
			}
		}
	}
	
	@ActionHandler
	public void onDeath(LivingEntityDeathAction action) {
		this.map.forEach((entity, tick) -> entity.removeFlags(this.flag));
	}
}