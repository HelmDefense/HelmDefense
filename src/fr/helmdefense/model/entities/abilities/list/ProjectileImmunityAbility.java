package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.living.LivingEntityDamagedAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.projectile.Projectile;
import fr.helmdefense.model.entities.utils.Tier;

public class ProjectileImmunityAbility extends Ability {
	public ProjectileImmunityAbility(Tier unlock, Tier.Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void recoverHealth(LivingEntityDamagedAction action) {
		if (action.getAttacker() instanceof Projectile)
			action.getEntity().gainHp(action.getDmg());
	}
}