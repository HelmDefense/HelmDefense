package fr.helmdefense.model.entities.living.special;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityHeroPowerAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.GameLoop;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.Dir;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;

public class Hero extends LivingEntity {
	private long deathTick;
	private long powerTick;
	private ReadOnlyDoubleWrapper hpUpgradeProperty;
	private ReadOnlyDoubleWrapper dmgUpgradeProperty;
	
	public static final int RESPAWN_DELAY = 150;
	public static final int POWER_DELAY = 300;
	public static final double UPGRADE_PERCENT = 0.1;
	public static final double MAXIMUM_UPGRADE = 1.5;
	
	public Hero(LivingEntityType type, Location loc) {
		super(type, loc);
		this.deathTick = -1;
		this.powerTick = -1;
		this.hpUpgradeProperty = new ReadOnlyDoubleWrapper();
		this.dmgUpgradeProperty = new ReadOnlyDoubleWrapper();
	}
	
	public Hero(LivingEntityType type, double x, double y) {
		this(type, new Location(x, y));
	}
	
	public void upgradeHp() {
		double next = roundOneDecimal(this.getHpUpgrade() + UPGRADE_PERCENT);
		if (next <= MAXIMUM_UPGRADE)
			this.setHpUpgrade(next);
	}
	
	public void upgradeDmg() {
		double next = roundOneDecimal(this.getDmgUpgrade() + UPGRADE_PERCENT);
		if (next <= MAXIMUM_UPGRADE)
			this.setDmgUpgrade(next);
	}
	
	private static double roundOneDecimal(double val) {
		return Math.round(val * 10) / 10d;
	}
	
	public void usePower() {
		if (! this.isAlive()
				|| (this.powerTick != -1 && this.getLevel().getGameloop().getTicks() - this.powerTick < POWER_DELAY))
			return;
		
		LivingEntityHeroPowerAction action = new LivingEntityHeroPowerAction(this);
		
		this.triggerAbilities(action);
	}
	
	public void move(Dir dir) {
		if (! this.isAlive())
			return;
		
		Location loc = dir.n(this.getLoc(), this.stat(Attribute.MVT_SPD) / GameLoop.TPS * this.getLevel().getGameloop().getSpeedness());
		if (loc.isInMap(this.getHitbox().getSize()))
			this.teleport(loc);
	}
	
	@Override
	public void spawn(Level lvl) {
		super.spawn(lvl);
		this.spawn();
	}
	
	public void respawn() {
		Level lvl = this.getLevel();
		this.dispawn();
		this.spawn(lvl);
	}
	
	private void spawn() {
		this.deathTick = -1;
		this.setupStatUpgradeModifiers();
		this.setHp((int) this.stat(Attribute.HP));
		this.setShield(0);
	}
	
	@Override
	protected void delete() {
		super.delete();
		this.getModifiers().clear();
		this.removeFlags(LivingEntity.ALL);
		if (this.deathTick != -1)
			Actions.registerListeners(this);
	}
	
	private void setupStatUpgradeModifiers() {
		// Check for old modifiers
		AttributeModifier hp = this.getModifier("HeroStatUpgradeHp"), dmg = this.getModifier("HeroStatUpgradeDmg");
		
		// If so, remove them
		if (hp != null)
			this.getModifiers().remove(hp);
		if (dmg != null)
			this.getModifiers().remove(dmg);
		
		// Apply new ones
		this.getModifiers().add(new AttributeModifier("HeroStatUpgradeHp", Attribute.HP, Operation.MULTIPLY, this.getHpUpgrade()));
		this.getModifiers().add(new AttributeModifier("HeroStatUpgradeDmg", Attribute.DMG, Operation.MULTIPLY, this.getDmgUpgrade()));
	}
	
	@ActionHandler
	public void onDeath(LivingEntityDeathAction action) {
		this.deathTick = this.getLevel().getGameloop().getTicks();
		this.getModifiers().clear();
		this.removeFlags(LivingEntity.ALL);
	}
	
	@ActionHandler
	public void onTickHero(GameTickAction action) {
		if (this.deathTick != -1 && action.getTicks() - this.deathTick > RESPAWN_DELAY)
			this.respawn();
	}
	
	public final double getHpUpgrade() {
		return this.hpUpgradeProperty.get();
	}
	
	private final void setHpUpgrade(double hpUpgrade) {
		this.hpUpgradeProperty.set(hpUpgrade);
	}
	
	public final ReadOnlyDoubleProperty hpUpgradeProperty() {
		return this.hpUpgradeProperty.getReadOnlyProperty();
	}
	
	public final double getDmgUpgrade() {
		return this.dmgUpgradeProperty.get();
	}
	
	private final void setDmgUpgrade(double dmgUpgrade) {
		this.dmgUpgradeProperty.set(dmgUpgrade);
	}
	
	public final ReadOnlyDoubleProperty dmgUpgradeProperty() {
		return this.dmgUpgradeProperty.getReadOnlyProperty();
	}
}