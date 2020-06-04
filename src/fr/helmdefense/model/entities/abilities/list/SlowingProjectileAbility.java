package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class SlowingProjectileAbility extends Ability {
	private int duration; 
	private double value;
	
	public SlowingProjectileAbility(Tier unlock, Specification tierSpecification) {
		this(unlock, tierSpecification, 30);
	}
	
	public SlowingProjectileAbility(Tier unlock, Specification tierSpecification, Integer duration) {
		this(unlock, tierSpecification, duration, 0.8d);
	}
	public SlowingProjectileAbility(Tier unlock, Specification tierSpecification, Double value) {
		this(unlock, tierSpecification, 30, value);
	}
	
	public SlowingProjectileAbility(Tier unlock, Specification tierSpecification, Integer duration, Double value) {
		super(unlock, tierSpecification);
		this.duration = duration;
		this.value = value;
	}
	
	@ActionHandler
	public void effectSlowAbility(ProjectileEntityAttackAction action) {
		Entity entity = action.getEntity();
		AttributeModifier attribute = entity.getModifier(this.getClass().getSimpleName());
		if ( attribute == null ) {
			attribute = new AttributeModifier(this.getClass().getSimpleName(), Attribute.MVT_SPD, Operation.MULTIPLY, this.value, entity.getLevel().getTicks(), this.duration);
			action.getVictim().getModifiers().add(attribute);
		}
		else
			attribute.setStart(entity.getLevel().getTicks());
	}
}
	