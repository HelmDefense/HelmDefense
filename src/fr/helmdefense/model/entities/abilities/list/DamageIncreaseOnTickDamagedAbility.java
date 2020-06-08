package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.projectile.ProjectileEntityAttackAction;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;

public class DamageIncreaseOnTickDamagedAbility extends Ability {
	private double value;
	private int flag;
	
	public DamageIncreaseOnTickDamagedAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, "FIRE", 0.5);
	}

	public DamageIncreaseOnTickDamagedAbility(Tier unlock, Tier.Specification tierSpecification, String flag, Double value) {
		super(unlock, tierSpecification);
		this.value = value;
		if (flag.equalsIgnoreCase("FIRE"))
			this.flag = LivingEntity.FIRE;
		else if (flag.equalsIgnoreCase("POISON"))
			this.flag = LivingEntity.POISON;
		else
			this.flag = -1;
	}
	
	@ActionHandler
	public void onProjectileEntityAttackAction(ProjectileEntityAttackAction action) {
		if (this.flag == -1)
			return;
		
		if (action.getVictim().testFlags(this.flag))
			action.getVictim().looseHp((int) (action.getDmg() * this.value), action.getEntity().getSource());
	}
}