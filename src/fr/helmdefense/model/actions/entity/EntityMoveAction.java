package fr.helmdefense.model.entities.abilities.actions.entity;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.utils.Location;

public class EntityMoveAction extends EntityAction {
	private Location from;
	
	public EntityMoveAction(Entity entity, Location from) {
		super(entity);
		this.from = from;
	}
	
	public EntityMoveAction(Entity entity, int x, int y) {
		this(entity, new Location(x, y));
	}
	
	public Location getFrom() {
		return this.from;
	}
	
	public Location getTo() {
		return this.getEntity().getLoc();
	}
}