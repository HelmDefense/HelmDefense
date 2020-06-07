package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityHeroPowerAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class GalvanizeAbility extends Ability {
	private int duration;
	private double effectRange;
	private double value;
	
	public GalvanizeAbility(Tier unlock, Specification tierSpecification) {
		this(unlock, tierSpecification, 15, 6d, 0.05d);
	}

	public GalvanizeAbility(Tier unlock, Specification tierSpecification, Integer duration, Double effectRange, Double value) {
		super(unlock, tierSpecification);
		this.duration = duration;
		this.effectRange = effectRange;
		this.value = value;
	}
	
	@ActionHandler
	public void onLivingEntityHeroPower(LivingEntityHeroPowerAction action) {
		LivingEntity source = action.getEntity();
		for (Entity entity : source.getLevel().getEntities()) {
			if(entity instanceof LivingEntity
					&& ((LivingEntity) entity).getType().getSide() == source.getType().getSide()
					&& entity.getLoc().distance(source.getLoc()) < this.effectRange) {
				entity.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName() + "Dmg", Attribute.DMG, Operation.MULTIPLY, this.value, source.getLevel().getTicks(), this.duration));
				entity.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName() + "AtkSpd", Attribute.ATK_SPD, Operation.MULTIPLY, this.value, source.getLevel().getTicks(), this.duration));
			}
		}
	}

}
