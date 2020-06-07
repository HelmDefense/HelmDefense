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

public class EyesOfElvenAbility extends Ability {
	private int duration;
	private double value;
	private double effectRange;

	public EyesOfElvenAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 150);
	}
	
	public EyesOfElvenAbility(Tier unlock, Tier.Specification tierSpecification, Integer duration) {
		this(unlock, tierSpecification, duration, 2d, 10d);
	}
	
	public EyesOfElvenAbility(Tier unlock, Tier.Specification tierSpecification, Double value) {
		this(unlock, tierSpecification, 150, value, 10d);
	}
	
	public EyesOfElvenAbility(Tier unlock, Tier.Specification tierSpecification, Integer duration, Double value, Double effectRange) {
		super(unlock, tierSpecification);
		this.duration = duration;
		this.value = value;
		this.effectRange = effectRange;
	}
	
	@ActionHandler
	public void onLivingEntityHeroPower(LivingEntityHeroPowerAction action) {
		LivingEntity source = action.getEntity();
		for (Entity entity : source.getLevel().getEntities()) {
			if(entity instanceof LivingEntity
					&& ((LivingEntity) entity).getType().getSide() == source.getType().getSide()
					&& entity.getLoc().distance(source.getLoc()) < this.effectRange) {
				entity.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName(), Attribute.SHOOT_RANGE, Operation.MULTIPLY, this.value, source.getLevel().getTicks(), this.duration));
			}
		}
	}
	
	

}
