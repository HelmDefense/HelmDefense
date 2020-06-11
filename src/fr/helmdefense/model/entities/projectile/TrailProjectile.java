package fr.helmdefense.model.entities.projectile;

import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.coords.Location;

public class TrailProjectile extends Projectile {

	public TrailProjectile(ProjectileType type, LivingEntity source, Location target, double angle, double speed) {
		this(type, source, target, true, angle, speed);
	}

	public TrailProjectile(ProjectileType type, LivingEntity source, Location target, double speed) {
		this(type, source, target, true, speed);
	}

	public TrailProjectile(ProjectileType type, LivingEntity source, Location target, boolean deleteOnHit, double angle, double speed) {
		super(type, source, target, angle, speed);
		
	}

	public TrailProjectile(ProjectileType type, LivingEntity source, Location target, boolean deleteOnHit, double speed) {
		super(type, source, target, deleteOnHit, speed);
	}
}
