package fr.helmdefense.model.entities.utils;

import java.util.HashMap;
import java.util.Map;

import fr.helmdefense.model.entities.Entity;

public class Entities {
	
	private static Map<Class<? extends Entity>, Statistic> entityData = new HashMap<Class<? extends Entity>, Statistic>();
	
	private Entities() {}
	
	public static Statistic getData(Class<? extends Entity> type) {
		return entityData.get(type);
	}
	
	public static void registerData(Class<? extends Entity> type, Statistic stat) {
		entityData.put(type, stat);
	}
	
}
