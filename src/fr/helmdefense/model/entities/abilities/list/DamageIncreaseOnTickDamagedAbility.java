package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.Tier.Specification;

public class DamageIncreaseOnTickDamagedAbility extends Ability {
	private double value;

	public DamageIncreaseOnTickDamagedAbility(Tier unlock, Specification tierSpecification, Double value) {
		super(unlock, tierSpecification);
		this.value = value;
	}
	
	public void onProjectileEntityAttackAction(ProjectileEntityAttackAction action) {
		if(action.getVictim().testFlags(LivingEntity.FIRE)) {
			action.getVictim().looseHp((int) (action.getDmg() * value), action.getEntity());
		}
	}

}
