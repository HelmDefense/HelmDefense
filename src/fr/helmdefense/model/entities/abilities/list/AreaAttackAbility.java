package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public abstract class AreaAttackAbility extends Ability {
	private boolean attacking;

	public AreaAttackAbility(Tier unlock, Tier.Specification specificationTier) {
		super(unlock, specificationTier);
		this.attacking = false;
	}

	public void areaAttackAbility(Location center, Entity attacker, double radius, EntitySide attackingSide, LivingEntity... immunes) {
		if (this.attacking)
			return;
		
		this.attacking = true;
		LivingEntity testing;
		
		for (Entity target : attacker.getLevel().getEntities())
			if (target instanceof LivingEntity
					&& (testing = (LivingEntity) target) != attacker
					&& ! contains(immunes, testing)
					&& attackingSide.isEnemy(testing.getType().getSide())
					&& testing.getLoc().distance(center) <= radius)
				attacker.attack(testing);
		
		this.attacking = false;
	}

	public boolean contains(LivingEntity[] immunes, LivingEntity target) {
		for (LivingEntity entity : immunes) {
			if (entity == target)
				return true;
		}
		return false;
	}
}
