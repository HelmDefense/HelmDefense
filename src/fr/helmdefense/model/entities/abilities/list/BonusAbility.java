package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class BonusAbility extends Ability {
	private Attribute attr;
	private Operation op;
	private double val;
	
	public BonusAbility(Tier unlock, Specification tierSpecification, String attr, String op, Double val) {
		super(unlock, tierSpecification);
		this.attr = Attribute.valueOf(attr.toUpperCase());
		this.op = Operation.valueOf(op.toUpperCase());
		this.val = val;
	}
	
	@ActionHandler
	public void rangeBonus(EntitySpawnAction action) {
		action.getEntity().getModifiers().add(new AttributeModifier(this.attr, this.op, this.val));
	}
}