package fr.helmdefense.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.actions.entity.EntityMoveAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.Entities;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.coords.Hitbox;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import javafx.beans.property.ReadOnlyDoubleProperty;

public abstract class Entity {
	private String id;
	private Location loc;
	private Hitbox hitbox;
	private Level level;
	private List<AttributeModifier> modifiers;
	protected List<Ability> abilities;
	
	private static int ids = 0;
	
	public Entity(Location loc) {
		this.id = "E" + (++ids);
		this.loc = loc;
		this.hitbox = new Hitbox(this.loc, this.data().getSize());
		this.hitbox.lockLocation();
		this.modifiers = new ArrayList<AttributeModifier>();
		this.abilities = this.data().instanciateAbilities();
		Actions.registerListeners(this.abilities);
		this.level = null;
	}
	
	public Entity(double x, double y) {
		this(new Location(x, y));
	}
	
	public void spawn(Level lvl) {
		if (this.level != null || lvl.getEntities().contains(this))
			return;
		this.level = lvl;
		lvl.getEntities().add(this);
	}
	
	public void attack(LivingEntity victim) {
		EntityDirectAttackAction attack = new EntityDirectAttackAction(this, victim, victim.getHp());
		
		victim.looseHp(this.data().getStats().getDmg(), this);
		
		this.triggerAbilities(attack);
	}
	
	public void addAbilities(Ability... abilities) {
		Actions.registerListeners(abilities);
		this.abilities.addAll(Arrays.asList(abilities));
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
		
		this.loc.setX(x).setY(y);
		
		this.triggerAbilities(action);
	}
	
	public void teleport(Location loc) {
		this.teleport(loc.getX(), loc.getY());
	}
	
	public Hitbox getHitbox() {
		return this.hitbox;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public List<AttributeModifier> getModifiers() {
		return this.modifiers;
	}
	
	public EntityData data() {
		return Entities.getData(this.getClass());
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", hitbox=" + hitbox + ", abilities="
				+ abilities + "]";
	}
}