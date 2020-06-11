package fr.helmdefense.model.entities.abilities.list;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;

public class AreaTickDamageAbility extends Ability {
	private double radius;
	private LivingEntity source;
	private int flag;
	private int duration;
	private Map<LivingEntity, Long> map;
	
	public AreaTickDamageAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, "POISON", 2d, 30);
	}

	public AreaTickDamageAbility(Tier unlock, Tier.Specification tierSpecification, String flag, Double radius, Integer duration) {
		super(unlock, tierSpecification);
		this.radius = radius;
		if (flag.equalsIgnoreCase("FIRE"))
			this.flag = LivingEntity.FIRE;
		else if (flag.equalsIgnoreCase("POISON"))
			this.flag = LivingEntity.POISON;
		else
			this.flag = -1;
		this.duration = duration;
		this.map = new HashMap<LivingEntity, Long>();
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity)
			this.source = (LivingEntity) action.getEntity();
	}
	
	@ActionHandler
	public void manageFlag(GameTickAction action) {
		for (Entity entity : this.source.getLevel().getEntities()) {
			if (entity instanceof LivingEntity
					&& ((LivingEntity) entity).isEnemy(this.source)
					&& entity.getLoc().distance(this.source.getLoc()) < this.radius) {
				this.map.put((LivingEntity) entity, action.getTicks());
				((LivingEntity) entity).addFlags(this.flag);
			}
		}
		
		Iterator<Map.Entry<LivingEntity, Long>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<LivingEntity, Long> entry = it.next();
			if (action.getTicks() - entry.getValue() > this.duration) {
				entry.getKey().removeFlags(this.flag);
				it.remove();
			}
		}	
	}

}
