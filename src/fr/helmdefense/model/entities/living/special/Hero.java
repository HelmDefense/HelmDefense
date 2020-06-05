package fr.helmdefense.model.entities.living.special;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;

public class Hero extends LivingEntity {
	private long deathTick;
	private double hpUpgrade;
	private double dmgUpgrade;
	
	public static final int RESPAWN_DELAY = 150;
	public static final double UPGRADE_PERCENT = 0.1;
	public static final double MAXIMUM_UPGRADE = 1.5;
	
	public Hero(LivingEntityType type, Location loc) {
		super(type, loc);
		this.deathTick = -1;
		this.hpUpgrade = 0;
		this.dmgUpgrade = 0;
	}
	
	public Hero(LivingEntityType type, double x, double y) {
		this(type, new Location(x, y));
	}
	
	public void upgradeHp() {
		if (this.hpUpgrade + UPGRADE_PERCENT < MAXIMUM_UPGRADE)
			this.hpUpgrade += UPGRADE_PERCENT;
	}
	
	public void upgradeDmg() {
		if (this.dmgUpgrade + UPGRADE_PERCENT < MAXIMUM_UPGRADE)
			this.dmgUpgrade += UPGRADE_PERCENT;
	}
	
	@Override
	public void spawn(Level lvl) {
		super.spawn(lvl);
		this.respawn();
	}
	
	public void respawn() {
		this.deathTick = -1;
		this.getModifiers().add(new AttributeModifier(Attribute.HP, Operation.MULTIPLY, this.hpUpgrade));
		this.getModifiers().add(new AttributeModifier(Attribute.DMG, Operation.MULTIPLY, this.dmgUpgrade));
		this.setHp((int) this.data().getStats().get(Attribute.HP));
		this.setShield(0);
		this.getLevel().getEntities().add(this);
	}
	
	@Override
	public void delete() {
		super.delete();
		Actions.registerListeners(this);
		this.getModifiers().clear();
		this.removeFlags(LivingEntity.ALL);
		this.deathTick = this.getLevel().getTicks();
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.deathTick != -1 && action.getTicks() - this.deathTick > RESPAWN_DELAY)
			this.respawn();
	}

	@Override
	public String toString() {
		return "Hero [deathTick=" + deathTick + ", hpUpgrade=" + hpUpgrade + ", dmgUpgrade=" + dmgUpgrade + "]";
	}
}