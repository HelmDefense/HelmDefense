package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class HeroBonusAbility extends Ability {
	private Attribute attr;
	private int pourcentValue;
	private String modifierName;

	public HeroBonusAbility(Tier unlock, Specification tierSpecification, Attribute attr) {
		this(unlock, tierSpecification, attr, 5);
	}
	
	public HeroBonusAbility(Tier unlock, Specification tierSpecification, Attribute attr, int pourcentValue) {
		super(unlock, tierSpecification);
		this.attr = attr;
		this.pourcentValue = pourcentValue;
		this.modifierName = "Bonus";
	}
	
	@ActionHandler
	public void onEntitySpawnAction(EntitySpawnAction action) {
		for (Entity entity : action.getEntity().getLevel().getEntities()) {
			if(((LivingEntity) entity).getType().getSide() == EntitySide.DEFENDER) {
				entity.getModifiers().add(new AttributeModifier(this.modifierName, attr, Operation.ADD, (entity.stat(attr) * pourcentValue) / 100));
			}
		}
	}
	
	@ActionHandler
	public void onLivingEntityDeathAction(LivingEntityDeathAction action) {
		for (Entity entity : action.getEntity().getLevel().getEntities()) {
			if(((LivingEntity) entity).getType().getSide() == EntitySide.DEFENDER) {
				for(AttributeModifier mod : entity.getModifiers()) {
					if(mod.getName().equals(this.modifierName)) {
						entity.getModifiers().remove(mod);
					}
				}
			}
		}
	}
}
