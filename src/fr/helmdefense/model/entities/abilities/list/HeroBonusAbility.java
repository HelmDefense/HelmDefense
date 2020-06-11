package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;

public class HeroBonusAbility extends Ability {
	private Attribute attr;
	private double pourcentValue;
	private EntitySide entitySide;

	public HeroBonusAbility(Tier unlock, Tier.Specification tierSpecification, String attr) {
		this(unlock, tierSpecification, attr, 0.05);
	}
	
	public HeroBonusAbility(Tier unlock, Tier.Specification tierSpecification, String attr, Double pourcentValue) {
		super(unlock, tierSpecification);
		this.attr = Attribute.valueOf(attr.toUpperCase());
		this.pourcentValue = pourcentValue;
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.entitySide == null)
			return;
			
		for (Entity entity : action.getLvl().getEntities())
			if (entity instanceof LivingEntity 
					&& ((LivingEntity) entity).getType().getSide() == this.entitySide
					&& entity.getModifier(this.getClass().getSimpleName()) == null)
				entity.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName(), this.attr, Operation.MULTIPLY, this.pourcentValue));
	}
	
	@ActionHandler
	public void onEntitySpawn(EntitySpawnAction action) {
		if (action.getEntity() instanceof LivingEntity)
			this.entitySide = ((LivingEntity) action.getEntity()).getType().getSide();
	}
	
	@ActionHandler
	public void onLivingEntityDeath(LivingEntityDeathAction action) {
		AttributeModifier mod;
		for (Entity entity : action.getEntity().getLevel().getEntities())
			if ((mod = entity.getModifier(this.getClass().getSimpleName())) != null)
				entity.getModifiers().remove(mod);
	}
}