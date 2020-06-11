package fr.helmdefense.model.entities.abilities.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.actions.entity.EntityMoveAction;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Tier;

public class GrondAttackAbility extends AreaAttackAbility {
	private int numberOfGoblins;
	private int fireDuration;
	private double radius;
	private LivingEntity entity;
	private List<Integer> numberOfGoblinsList;
	private List<Double> radiusList;
	private List<LivingEntity> goblinsList;
	private Map<LivingEntity, Long> map;
	private long lastAttack;
	private int attackReloadingTime;
	
	public GrondAttackAbility(Tier unlock, Tier.Specification tierSpecification, Integer attackReloadingTime, Integer fireDuration, ArrayList<Integer> numberOfGoblinsList, ArrayList<Double> radiusList) {
		super(unlock, tierSpecification);
		this.radius = -1;
		this.radiusList = radiusList;
		this.numberOfGoblinsList = numberOfGoblinsList;
		this.goblinsList = new ArrayList<LivingEntity>();
		this.map = new HashMap<LivingEntity, Long>();
		this.fireDuration = fireDuration;
		this.lastAttack = -1;
		this.attackReloadingTime = attackReloadingTime;
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if (! (action.getEntity() instanceof LivingEntity))
			return;

		this.entity = (LivingEntity) action.getEntity();
		int tier = this.entity.data().getTier().getNumberTier();
		this.radius = radiusList.get(tier - 1);
		this.numberOfGoblins = this.numberOfGoblinsList.get(tier - 1);
		
		for (int i = 0; i < this.numberOfGoblins; i++) {
			LivingEntity e = new LivingEntity(LivingEntityType.GOBLIN, action.getSpawn());
			this.entity.getLevel().getCurrentWave().addAlreadySpawnedEntity(e);
			e.addFlags(LivingEntity.IMMOBILE);
			e.setVisible(false);
			e.spawn(this.entity.getLevel());
			this.goblinsList.add(e);
		}
	}
	
	@ActionHandler
	public void onMove(EntityMoveAction action) {
		this.goblinsList.forEach(e -> e.teleport(action.getTo()));
	}
		
	@ActionHandler
	public void onAttack(EntityDirectAttackAction action) {
		long ticks = this.entity.getLevel().getGameloop().getTicks();
		this.entity.addFlags(LivingEntity.IMMOBILE);
		this.lastAttack = ticks;
		
		LivingEntity victim = action.getVictim();
		this.areaAttackAbility(victim.getLoc(), this.entity, this.radius, this.entity.getType().getSide(), e -> {
			e.addFlags(LivingEntity.FIRE);
			this.map.put(e, ticks);
		}, victim);
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.lastAttack != -1 && action.getTicks() - this.lastAttack > this.attackReloadingTime)
			this.entity.removeFlags(LivingEntity.IMMOBILE);
		
		Iterator<Map.Entry<LivingEntity, Long>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<LivingEntity, Long> entry = it.next();
			if (action.getTicks() - entry.getValue() > this.fireDuration) {
				entry.getKey().removeFlags(LivingEntity.FIRE);
				it.remove();
			}
		}
	}
	
	@ActionHandler
	public void onDeath(LivingEntityDeathAction action) {
		this.goblinsList.forEach(e -> {
			e.removeFlags(LivingEntity.IMMOBILE);
			e.setVisible(true);
		});
	}
}