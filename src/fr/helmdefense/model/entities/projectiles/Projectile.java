package fr.helmdefense.model.entities.projectiles;

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
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.attackers.Attacker;
import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.entities.utils.coords.Vector;
import fr.helmdefense.model.level.GameLoop;

public class Projectile extends Entity implements ActionListener {
	private LivingEntity source;
	private Vector vector;
	private double speed;
	
	public Projectile(LivingEntity source, Location target, double angle, double speed) {
		super(source.getLoc());
		Location loc = source.getLoc();
		double d = target.distance(loc), a = Math.acos((target.getX() - loc.getX()) / d);
		if (Math.asin((target.getY() - loc.getY()) / d) < 0)
			a += (Math.PI - a) * 2;
		a += Math.toRadians(angle);
		this.vector = new Vector(Math.cos(a), Math.sin(a));
		
		this.init(source, speed);
	}
	
	public Projectile(LivingEntity source, Location target, double speed) {
		super(source.getLoc());
		Location loc = source.getLoc();
		this.vector = new Vector(loc, target).divide(target.distance(loc));
		
		this.init(source, speed);
	}
	
	private void init(LivingEntity source, double speed) {
		this.source = source;
		this.speed = speed;
		
		Actions.registerListeners(this);
		
		ProjectileEntityShootAction action = new ProjectileEntityShootAction(this);
		source.triggerAbilities(action);
	}
	
	@Override
	public void attack(LivingEntity victim) {
		ProjectileEntityAttackAction attack = new ProjectileEntityAttackAction(this, victim, victim.getHp());
		
		victim.looseHp((int) (this.source.data().getStats().getDmg() * Statistic.SHOOT_FACTOR), this.source);
		
		this.source.triggerAbilities(attack);
		
		this.delete();
	}
	
	public void fail() {
		ProjectileEntityFailAction action = new ProjectileEntityFailAction(this);
		
		this.source.triggerAbilities(action);
		
		this.delete();
	}
	
	private void delete() {
		this.getLevel().getEntities().remove(this);
		Actions.unregisterListeners(this.abilities);
		Actions.unregisterListeners(this);
	}
	
	public double angle() {
		return this.vector.angle(true);
	}
	
	@ActionHandler
	public void move(GameTickAction action) {
		Location loc = this.getLoc().add(this.vector.copy().multiply(this.speed / GameLoop.TPS));
		if (! loc.isInMap() || loc.distance(this.source.getLoc()) > this.source.data().getStats().getShootRange() + 0.5)
			this.fail();
		else
			this.teleport(loc);
		
		List<Entity> list = new ArrayList<Entity>(this.getLevel().getEntities());
		for (Entity e : list)
			if ((this.source instanceof Defender ? e instanceof Attacker : e instanceof Defender)
					&& this.getHitbox().overlaps(e.getHitbox())) {
				this.attack((LivingEntity) e);
				break;
			}
	}
	
	public LivingEntity getSource() {
		return this.source;
	}

	@Override
	public String toString() {
		return "Projectile [source=" + source + ", vector=" + vector + ", speed=" + speed + "]";
	}
}