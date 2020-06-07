package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class ProjectileImmunityAbility extends Ability {

	public ProjectileImmunityAbility(Tier unlock, Specification tierSpecification) {
		super(unlock, tierSpecification);
	}
	
	@ActionHandler
	public void recoverHealth(ProjectileEntityAttackAction action) {
		action.getVictim().gainHp(action.getHpBefore());
	}
}
