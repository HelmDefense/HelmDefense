package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityHeroPowerAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class GalvanizeAbility extends Ability {
	private long startTick;
	private String modifierNameDmg;
	private String modifierNameAtkSpd;
	
	private static final int POWER_DELAY = 500;

	public GalvanizeAbility(Tier unlock, Specification tierSpecification) {
		super(unlock, tierSpecification);
		this.modifierNameDmg = "PowerDamage";
		this.modifierNameAtkSpd = "PowerAtkSpd";
	}
	
	@ActionHandler
	public void onLivingEntityHeroPowerAction(LivingEntityHeroPowerAction action) {
		for (Entity entity : action.getEntity().getLevel().getEntities()) {
			if(((LivingEntity) entity).getType().getSide() == EntitySide.DEFENDER) {
				entity.getModifiers().add(new AttributeModifier(this.modifierNameDmg, Attribute.DMG, Operation.ADD, entity.stat(Attribute.DMG) * 2));
				entity.getModifiers().add(new AttributeModifier(this.modifierNameAtkSpd, Attribute.ATK_SPD, Operation.ADD, entity.stat(Attribute.ATK_SPD) * 2));
			}
		}
		this.startTick = action.getEntity().getLevel().getTicks();
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (action.getTicks() - this.startTick > POWER_DELAY) {
			for (Entity entity : action.getLvl().getEntities()) {
				if(((LivingEntity) entity).getType().getSide() == EntitySide.DEFENDER) {
					for(AttributeModifier mod : entity.getModifiers()) {
						if(mod.getName().equals(this.modifierNameDmg) || mod.getName().equals(this.modifierNameAtkSpd)) {
							entity.getModifiers().remove(mod);
						}
					}
				}
			}
		}
	}

}
