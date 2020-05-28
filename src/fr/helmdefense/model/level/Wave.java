package fr.helmdefense.model.level;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.Tier;

public class Wave implements ActionListener {
	private int reward;
	private Map<Long, Entity> entities;
	private int entityCount;
	private Level lvl;
	private long startTick;
	private long endTick;
	
	public static final long TICKS_BEFORE_FIRST_WAVE = 100;
	public static final long TICKS_BETWEEN_EACH_WAVE = 50;
	
	public Wave(int reward, Map<Integer, String> entities) {
		this.reward = reward;
		this.entities = entities.entrySet().stream()
				.collect(Collectors.toMap(
						e -> ((Number) e.getKey()).longValue(),
						e -> {
							try {
								Entity entity = Entities.getClass("attackers." + e.getValue()).getConstructor(double.class, double.class).newInstance(0, 0);
								entity.addAbilities(new WaveDeathCountAbility());
								return entity;
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
								e1.printStackTrace();
								return null;
							}
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
	
	public int getReward() {
		return this.reward;
	}
	
	public boolean isEnded() {
		return this.entityCount == 0;
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
			super(Tier.TIER_1);
		}
		
		@ActionHandler
		public void onDeath(LivingEntityDeathAction action) {
			entityCount--;
		}
	}
}