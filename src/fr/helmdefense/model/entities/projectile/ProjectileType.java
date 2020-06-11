package fr.helmdefense.model.entities.projectile;

import fr.helmdefense.model.entities.EntityType;
import fr.helmdefense.model.entities.utils.EntityData;

public enum ProjectileType implements EntityType {
	ARROW(SubType.CLASSIC),
	ROCKBALL(SubType.CLASSIC),
	LASER_BEAM(SubType.TRAILING);
	
	private EntityData data;
	private SubType subType;
	

	private ProjectileType(SubType subType) {
		this.subType = subType;
	}
	
	@Override
	public void setData(EntityData data) {
		this.data = data;
	}

	@Override
	public EntityData getData() {
		return this.data;
	}
	
	public SubType getSubType() {
		return subType;
	}

	public enum SubType {
		CLASSIC,
		TRAILING;
	}
}

