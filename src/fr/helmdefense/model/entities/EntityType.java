package fr.helmdefense.model.entities;

import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.projectile.ProjectileType;
import fr.helmdefense.model.entities.utils.EntityData;

public interface EntityType {	
	public void setData(EntityData data);
	
	public EntityData getData();
	
	public static EntityType getFromName(String name) {
		try {
			return LivingEntityType.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e1) {
			try {
				return ProjectileType.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e2) {
				System.err.println("No EntityType found for input: " + name);
				return null;
			}
		}
	}
	
	public static EntityType getFromPath(String path) {
		EntityType[] values;
		if (path.startsWith("projectiles."))
			values = ProjectileType.values();
		else
			values = LivingEntityType.values();
		
		for (EntityType type : values)
			if (type.getData() != null && type.getData().getPath().equalsIgnoreCase(path))
				return type;
		return null;
	}
}