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

public class EyesOfElvenAbility extends Ability {
	private long startTick;
	private String modifierShootRangeName;
	
	private static final int POWER_DELAY = 500;

	public EyesOfElvenAbility(Tier unlock, Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void onLivingEntityHeroPowerAction(LivingEntityHeroPowerAction action) {
		for (Entity entity : action.getEntity().getLevel().getEntities()) {
			if(((LivingEntity) entity).getType().getSide() == EntitySide.DEFENDER) {
				entity.getModifiers().add(new AttributeModifier(this.modifierShootRangeName, Attribute.SHOOT_RANGE, Operation.ADD, entity.stat(Attribute.SHOOT_RANGE) * 2));
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
						if(mod.getName().equals(this.modifierShootRangeName)) {
							entity.getModifiers().remove(mod);
						}
					}
				}
			}
		}
	}

}
