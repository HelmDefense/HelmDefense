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
	private Vector vector;
	private double speed;
	private Location target;
	private double angle;
	
	public Projectile(ProjectileType type, LivingEntity source, Location target, double angle, double speed) {
		super(type, source.getLoc());
		Location loc = source.getLoc();
		double d = target.distance(loc), a = Math.acos((target.getX() - loc.getX()) / d);
		if (Math.asin((target.getY() - loc.getY()) / d) < 0)
			a += (Math.PI - a) * 2;
		a += Math.toRadians(angle);
		this.vector = new Vector(Math.cos(a), Math.sin(a));
		
		this.init(source, speed, target, angle);
	}
	
	public Projectile(ProjectileType type, LivingEntity source, Location target, double speed) {
		super(type, source.getLoc());
		Location loc = source.getLoc();
		this.vector = new Vector(loc, target).divide(target.distance(loc));
		
		this.init(source, speed, target, 0);
	}
	
	private void init(LivingEntity source, double speed, Location target, double angle) {
		this.source = source;
		this.speed = speed;
		this.target = target;
		this.angle = angle;
		
		Actions.registerListeners(this);
		
		ProjectileEntityShootAction action = new ProjectileEntityShootAction(this);
		source.triggerAbilities(action);
	}

	@Override
	public void attack(LivingEntity victim) {
		ProjectileEntityAttackAction attack = new ProjectileEntityAttackAction(this, victim, victim.getHp());
		
		victim.looseHp((int) (this.source.stat(Attribute.DMG) * Statistic.SHOOT_FACTOR), this.source);
		
		this.source.triggerAbilities(attack);
		
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
		Location loc = this.getLoc().add(this.vector.copy().multiply(this.speed / GameLoop.TPS));
		if (! loc.isInMap() || loc.distance(this.source.getLoc()) > this.source.stat(Attribute.SHOOT_RANGE) + 0.5)
			this.fail();
		else
			this.teleport(loc);
		
		List<Entity> list = new ArrayList<Entity>(this.getLevel().getEntities());
		for (Entity e : list)
			if (e instanceof LivingEntity
					&& this.source.isEnemy((LivingEntity) e)
					&& this.getHitbox().overlaps(e.getHitbox())) {
				this.attack((LivingEntity) e);
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