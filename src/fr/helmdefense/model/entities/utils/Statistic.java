package fr.helmdefense.model.entities.utils;

import java.util.HashMap;
import java.util.Map;

public class Statistic {
	private Map<Attribute, Double> stats;
	
	public static final double SHOOT_FACTOR = 0.75;
	public static final int FIRE_DAMAGE = 200;
	public static final double FIRE_FREQUENCE = 5;
	
	public Statistic() {
		this.stats = new HashMap<Attribute, Double>();
	}
	
	public void register(Attribute attr, double stat) {
		if (! this.stats.containsKey(attr) && stat >= 0)
			this.stats.put(attr, stat);
	}

	public double get(Attribute attr) {
		return this.stats.getOrDefault(attr, -1d);
	}
}