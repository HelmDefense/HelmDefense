package fr.helmdefense.model.entities;

import java.util.List;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.actions.entity.EntityMoveAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import javafx.beans.property.ReadOnlyDoubleProperty;

public abstract class Entity {

	private String id;
	private Location loc;
	private List<Ability> abilities;
	private Level level;
	private static int ids = 0;

	public Entity(Location loc) {
		this.id = "E" + (++ids);
		this.loc = loc;
		this.abilities = this.data().instanciateAbilities();
		Actions.registerListeners(this.abilities);
		this.level = null;
	}

	public void spawn(Level lvl) {
		if (this.level != null || lvl.getEntities().contains(this))
			return;
		this.level = lvl;
		lvl.getEntities().add(this);
	}

	public void triggerAbilities(Action action) {
		Actions.trigger(action, this.abilities);
	}

	public String getId() {
		return this.id;
	}

	public Location getLoc() {
		return this.loc.copy();
	}

	public ReadOnlyDoubleProperty xProperty() {
		return this.loc.xProperty().getReadOnlyProperty();
	}

	public ReadOnlyDoubleProperty yProperty() {
		return this.loc.yProperty().getReadOnlyProperty();
	}

	public void teleport(double x, double y) {
		EntityMoveAction action = new EntityMoveAction(this, this.getLoc());
		
		this.loc.setX(x);
		this.loc.setY(y);
		
		this.triggerAbilities(action);
	}

	public void teleport(Location loc) {
		this.teleport(loc.getX(), loc.getY());
	}

	public EntityData data() {
		return Entities.getData(this.getClass());
	}

	public Level getLevel() {
		return level;
	}
	
	public void unregisterAbilities() {
		Actions.unregisterListeners(this.abilities);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", loc=" + loc + ", abilities=" + abilities + "]";
	}
	

}