package fr.helmdefense.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.actions.entity.EntityMoveAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.DamageCause;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.coords.Hitbox;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Entity implements DamageCause, ActionListener {
	private String id;
	private EntityType type;
	private Location loc;
	private Hitbox hitbox;
	private Level level;
	private List<AttributeModifier> modifiers;
	private List<Ability> abilities;
	private BooleanProperty visibleProperty;
	
	private static int ids = 0;
	
	public Entity(EntityType type, Location loc) {
		this.id = "E" + (++ids);
		this.type = type;
		this.loc = loc;
		this.hitbox = new Hitbox(this.loc, this.data().getSize());
		this.hitbox.lockLocation();
		this.modifiers = new ArrayList<AttributeModifier>();
		this.abilities = this.data().instanciateAbilities();
		this.visibleProperty = new SimpleBooleanProperty(true);
		this.level = null;
	}
	
	public Entity(EntityType type, double x, double y) {
		this(type, new Location(x, y));
	}
	
	public void spawn(Level lvl) {
		if (this.level != null || lvl.getEntities().contains(this))
			return;
		this.level = lvl;
		
		Actions.registerListeners(this.abilities);
		Actions.registerListeners(this);
		
		lvl.getEntities().add(this);
	}
	
	public void dispawn() {
		if (this instanceof LivingEntity)
			LivingEntity.DispawnDamageCause.attackWithInstance((LivingEntity) this);
		else
			this.delete();
		this.level = null;
	}
	
	@Override
	public void attack(LivingEntity victim) {
		int hpBefore = victim.getHp();
		int dmg = victim.looseHp((int) this.stat(Attribute.DMG), this);
		
		EntityDirectAttackAction attack = new EntityDirectAttackAction(this, victim, hpBefore, dmg);
		
		this.triggerAbilities(attack);
	}
	
	public void addAbilities(Ability... abilities) {
		Actions.registerListeners(abilities);
		this.abilities.addAll(Arrays.asList(abilities));
	}
	
	public void triggerAbilities(Action action) {
		Actions.trigger(action, this.abilities.stream()
				.filter(ability -> ability.isUnlocked(this.data().getTier(), this.data().getTierSpecification()))
				.collect(Collectors.toList()));
		Actions.trigger(action, Arrays.asList(this));
	}
	
	protected void delete() {
		this.level.getEntities().remove(this);
		Actions.unregisterListeners(this.abilities);
		Actions.unregisterListeners(this);
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
	
	public AttributeModifier getModifier(int id) {
		return this.modifiers.stream()
				.filter(mod -> mod.getId() == id)
				.findAny()
				.orElse(null);
	}
	
	public AttributeModifier getModifier(String name) {
		return this.modifiers.stream()
				.filter(mod -> mod.getName().equals(name))
				.findAny()
				.orElse(null);
	}
	
	public final boolean isVisible() {
		return this.visibleProperty.get();
	}
	
	public final void setVisible(boolean visible) {
		this.visibleProperty.set(visible);
	}
	
	public final BooleanProperty visibleProperty() {
		return this.visibleProperty;
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		this.modifiers.removeIf(mod -> {
			long end = mod.getStart() + mod.getDuration();
			return end >= 0 && end < action.getTicks();
		});
	}
	
	public EntityData data() {
		return this.type.getData();
	}
	
	public double stat(Attribute attr) {
		return AttributeModifier.calculate(this.data().getStats().get(attr), attr, this.modifiers);
	}
	
	public EntityType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", type=" + type + ", loc=" + loc + ", hitbox=" + hitbox
				+ ", modifiers=" + modifiers + ", abilities=" + abilities + "]";
	}
}