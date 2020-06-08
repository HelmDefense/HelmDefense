package fr.helmdefense.model.level;

import java.util.Map;
import java.util.stream.Collectors;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntityType;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Tier;

public class Wave implements ActionListener {
	private String name;
	private int reward;
	private Map<Long, Entity> entities;
	private int entityCount;
	private Level lvl;
	private long startTick;
	private long endTick;
	
	public static final long TICKS_BEFORE_FIRST_WAVE = 100;
	public static final long TICKS_BETWEEN_EACH_WAVE = 50;
	
	public Wave(String name, int reward, Map<Integer, String> entities) {
		this.name = name;
		this.reward = reward;
		this.entities = entities.entrySet().stream()
				.collect(Collectors.toMap(
						e -> ((Number) e.getKey()).longValue(),
						e -> {
							Entity entity = new LivingEntity((LivingEntityType) EntityType.getFromName(e.getValue()), 0, 0);
							entity.addAbilities(new WaveDeathCountAbility());
							return entity;
						}
				));
		this.entityCount = this.entities.size();
		this.lvl = null;
		this.startTick = -1;
		this.endTick = -1;
	}
	
	public void start(Level lvl) {
		if (this.lvl != null)
			return;
		this.lvl = lvl;
		
		System.out.println("A wave started");
		
		Actions.registerListeners(this);
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.startTick == -1)
			this.startTick = action.getTicks();
		
		long ticks = action.getTicks() - this.startTick;
		Entity e = this.entities.get(ticks);
		if (e != null) {
			e.teleport(this.lvl.getMap().getSpawn());
			e.spawn(this.lvl);
		}
		
		if (this.isEnded()) {
			this.lvl.earnCoins(this.reward);
			this.endTick = action.getTicks();
			
			System.out.println("A wave ended");
			
			Actions.unregisterListeners(this);
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getReward() {
		return this.reward;
	}
	
	public boolean isEnded() {
		return this.entityCount <= 0;
	}
	
	public long getEndTick() {
		return this.endTick;
	}

	@Override
	public String toString() {
		return "Wave [reward=" + reward + ", entities=" + entities + "]";
	}
	
	public class WaveDeathCountAbility extends Ability {
		public WaveDeathCountAbility() {
			super(Tier.DEFAULT, Tier.Specification.DEFAULT);
		}
		
		@ActionHandler
		public void onDeath(LivingEntityDeathAction action) {
			entityCount--;
		}
	}
}