package fr.helmdefense.model.entities.utils;

import java.util.Map;

public class EntityData {
	private String name;
	private String path;
	private Map<Tier, Statistic> stats;
	
	public EntityData(String name, String path, Map<Tier, Statistic> stats) {
		this.name = name;
		this.path = path;
		this.stats = stats;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final String getPath() {
		return this.path;
	}
	
	public final Statistic getStats(Tier tier) {
		return this.stats.get(tier);
	}
}