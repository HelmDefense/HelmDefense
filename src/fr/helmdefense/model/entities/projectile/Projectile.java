package fr.helmdefense.model.entities.projectile;

import java.util.ArrayList;
import java.util.List;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityFailAction;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityShootAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.entities.utils.coords.Vector;
import fr.helmdefense.model.level.GameLoop;

public class Projectile extends Entity implements ActionListener {
	private LivingEntity source;
	private Location spawn;
	private Vector vector;
	private double speed;
	private Location target;
	private boolean deleteOnHit;
	private double angle;
	private List<LivingEntity> hits;
	
	public Projectile(ProjectileType type, LivingEntity source, Location target, double angle, double speed) {
		this(type, source, target, true, angle, speed);
	}
	
	public Projectile(ProjectileType type, LivingEntity source, Location target, double speed) {
		this(type, source, target, true, speed);
	}
	
	public Projectile(ProjectileType type, LivingEntity source, Location target, boolean deleteOnHit, double angle, double speed) {
		super(type, source.getLoc());
		Location loc = source.getLoc();
		double d = target.distance(loc), a = Math.acos((target.getX() - loc.getX()) / d);
		if (Math.asin((target.getY() - loc.getY()) / d) < 0)
			a += (Math.PI - a) * 2;
		a += Math.toRadians(angle);
		this.vector = new Vector(Math.cos(a), Math.sin(a));
		
		this.init(source, loc, speed, target, deleteOnHit, angle);
	}
	
	public Projectile(ProjectileType type, LivingEntity source, Location target, boolean deleteOnHit, double speed) {
		super(type, source.getLoc());
		Location loc = source.getLoc();
		this.vector = new Vector(loc, target).divide(target.distance(loc));
		
		this.init(source, loc, speed, target, deleteOnHit, 0);
	}
	
	private void init(LivingEntity source, Location spawn, double speed, Location target, boolean deleteOnHit, double angle) {
		this.source = source;
		this.spawn = spawn;
		this.speed = speed;
		this.target = target;
		this.deleteOnHit = deleteOnHit;
		this.angle = angle;
		this.hits = new ArrayList<LivingEntity>();
		this.vector.multiply(this.speed / GameLoop.TPS);
		
		Actions.registerListeners(this);
		
		ProjectileEntityShootAction action = new ProjectileEntityShootAction(this);
		source.triggerAbilities(action);
	}

	@Override
	public void attack(LivingEntity victim) {
		if (this.hits.contains(victim))
			return;
		
		int hpBefore = victim.getHp();
		int dmg = victim.looseHp((int) (this.source.stat(Attribute.DMG) * Statistic.SHOOT_FACTOR), this.source);
		this.hits.add(victim);
		
		ProjectileEntityAttackAction attack = new ProjectileEntityAttackAction(this, victim, hpBefore, dmg);
		
		this.source.triggerAbilities(attack);
		
		if (this.deleteOnHit)
			this.delete();
	}
	
	public void fail() {
		ProjectileEntityFailAction action = new ProjectileEntityFailAction(this);
		
		this.source.triggerAbilities(action);
		
		this.delete();
	}
	
	public double angle() {
		return this.vector.angle(true);
	}
	
	@ActionHandler
	public void move(GameTickAction action) {
		Location loc = this.getLoc().add(this.vector);
		if (! loc.isInMap() || loc.distance(this.spawn) > this.source.stat(Attribute.SHOOT_RANGE))
			this.fail();
		else
			this.teleport(loc);
		
		List<Entity> list = new ArrayList<Entity>(this.getLevel().getEntities());
		for (Entity e : list)
			if (e instanceof LivingEntity
					&& this.source.isEnemy((LivingEntity) e)
					&& this.getHitbox().overlaps(e.getHitbox())) {
				this.attack((LivingEntity) e);
				if (this.deleteOnHit)
					break;
			}
	}
	
	public LivingEntity getSource() {
		return this.source;
	}
	
	public double getSpeed() {
		return this.speed;
	}

	public Location getTarget() {
		return this.target;
	}
	
	public boolean isDeleteOnHit() {
		return this.deleteOnHit;
	}
	
	public void setDeleteOnHit(boolean deleteOnHit) {
		this.deleteOnHit = deleteOnHit;
	}

	public double getAngle() {
		return this.angle;
	}

	@Override
	public ProjectileType getType() {
		return (ProjectileType) super.getType();
	}

	@Override
	public String toString() {
		return "Projectile [source=" + source + ", vector=" + vector + ", speed=" + speed + "]";
	}
}