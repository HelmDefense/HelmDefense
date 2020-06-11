package fr.helmdefense.model.entities.projectile;

import fr.helmdefense.model.entities.EntityType;
import fr.helmdefense.model.entities.utils.EntityData;

public enum ProjectileType implements EntityType {
	ARROW,
	ROCKBALL;
	
	private EntityData data;
	
	@Override
	public void setData(EntityData data) {
		this.data = data;
	}

	@Override
	public EntityData getData() {
		return this.data;
	}

	public enum SubType {
		CLASSIC,
		TRAILING;
	}
}

