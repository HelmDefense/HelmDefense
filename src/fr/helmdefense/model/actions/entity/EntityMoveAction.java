package fr.helmdefense.model.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.coords.Location;

/**
 * Action triggered when an entity moves.
 * 
 * @author	indyteo
 * @see		EntityAction
 */
public class EntityMoveAction extends EntityAction {
	private Location from;
	
	public EntityMoveAction(Entity entity, Location from) {
		super(entity);
		this.from = from;
	}
	
	public EntityMoveAction(Entity entity, int x, int y) {
		this(entity, new Location(x, y));
	}
	
	/**
	 * Return the previous location of the entity.
	 * 
	 * @return	The {@link Location} of the entity
	 * 			before the movement.
	 */
	public Location getFrom() {
		return this.from;
	}
	
	/**
	 * Return the actual location of the entity.
	 * 
	 * @return	The {@link Location} of the entity
	 * 			after the movement.
	 */
	public Location getTo() {
		return this.getEntity().getLoc();
	}
}