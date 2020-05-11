package fr.helmdefense.model.level;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public class Wave {
	private int duration;
	private int reward;
	private Map<Integer, Entity> entities;
	
	public Wave(int duration, int reward, Map<Integer, String> entities) {
		this.duration = duration;
		this.reward = reward;
		this.entities = entities.entrySet().stream()
				.collect(Collectors.toMap(
						e -> e.getKey(),
						e -> {
							try {
								return Entity.EntityList.valueOf(e.getValue()).getType().getConstructor(Location.class).newInstance(new Location(0, 0));
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
								e1.printStackTrace();
								return null;
							}
						}
				));
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public int getReward() {
		return this.reward;
	}

	@Override
	public String toString() {
		return "Wave [duration=" + duration + ", reward=" + reward + ", entities=" + entities + "]";
	}
}