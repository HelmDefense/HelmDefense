package fr.helmdefense.model.entities.projectiles;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.entity.EntityProjectileAttackAction;
import fr.helmdefense.model.actions.entity.EntityProjectileFailAction;
import fr.helmdefense.model.actions.entity.EntityShootAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.entities.utils.coords.Vector;
import fr.helmdefense.model.level.GameLoop;

public class Projectile extends Entity implements ActionListener {
	private LivingEntity source;
	private Vector vector;
	
	public static final double SPEED = 1;
	
	public Projectile(LivingEntity source, Location target, double angle) {
		super(source.getLoc());
		Location loc = source.getLoc();
		double d = target.distance(loc), a = Math.acos((target.getX() - loc.getX()) / d);
		if(Math.asin((target.getY() - loc.getY()) / d) < 0)
			a += (Math.PI - a) * 2;
		a += Math.toRadians(angle);
		this.vector = new Vector(Math.cos(a), Math.sin(a));
		
		this.init(source);
	}
	
	public Projectile(LivingEntity source, Location target) {
		super(source.getLoc());
		Location loc = source.getLoc();
		this.vector = new Vector(loc, target).divide(target.distance(loc));
		
		this.init(source);
	}
	
	private void init(LivingEntity source) {
		this.source = source;
		
		Actions.registerListeners(this);
		
		EntityShootAction action = new EntityShootAction(this.source, this);
		source.triggerAbilities(action);
	}
	
	public void attack(LivingEntity victim) {
		EntityProjectileAttackAction attack = new EntityProjectileAttackAction(this.source, victim, this, victim.getHp());
		
		victim.looseHp((int) (this.source.data().getStats(Tier.TIER_1).getDmg() * Statistic.SHOOT_FACTOR), this.source);
		
		this.source.triggerAbilities(attack);
	}
	
	public void fail() {
		EntityProjectileFailAction action = new EntityProjectileFailAction(this.source, this);
		
		this.source.triggerAbilities(action);
	}
	
	@ActionHandler
	public void move(GameTickAction action) {
		this.teleport(this.getLoc().add(this.vector.copy().multiply(SPEED / GameLoop.TPS)));
	}
	
}