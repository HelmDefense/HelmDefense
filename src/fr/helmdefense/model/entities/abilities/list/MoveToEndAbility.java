package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameAttackerPassedAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.entities.utils.coords.Vector;
import fr.helmdefense.model.level.GameLoop;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.Cell;

public class MoveToEndAbility extends Ability {
	private LivingEntity entity;
	private Cell movingTo;
	
	public MoveToEndAbility(Tier unlock, Tier.Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void onSpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity)
			this.entity = (LivingEntity) action.getEntity();
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.entity == null || this.entity.testFlags(LivingEntity.IMMOBILE))
			return;
		
		if (this.movingTo == null) {
			this.movingTo = this.entity.getLevel().getMap().getGraph().getCellAt(this.entity.getLoc()).getNext();
			
			if (this.movingTo == null) {
				GameAttackerPassedAction pass = new GameAttackerPassedAction(action.getLvl(), this.entity);
				
				Actions.trigger(pass);
				
				Level.EndDamageCause.attackWithInstance(this.entity);
				return;
			}
		}
		
		Location loc = this.entity.getLoc(), l = this.movingTo.getLoc().center();
		Vector v = new Vector(loc, l);
		double d = l.distance(loc);
		v.multiply(this.entity.stat(Attribute.MVT_SPD) / GameLoop.TPS / d);
		this.entity.teleport(loc.add(v));
		if (v.length() >= d)
			this.movingTo = this.movingTo.getNext();
	}
}