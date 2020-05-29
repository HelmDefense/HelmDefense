package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.LivingEntity;
import fr.helmdefense.model.entities.abilities.SpecifiedAbility;
import fr.helmdefense.model.entities.attackers.Attacker;
import fr.helmdefense.model.entities.defenders.Defender;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Location;

public class AreaDirectAttackAbility extends SpecifiedAbility {
	private double radius;
	private boolean attacking;
	
	public AreaDirectAttackAbility(Tier unlock, Tier.Specification tierSpecification) {
		this(unlock, tierSpecification, 1d);
	}
	
	public AreaDirectAttackAbility(Tier unlock, Tier.Specification tierSpecification, Double radius) {
		super(unlock, tierSpecification);
		this.radius = radius;
		this.attacking = false;
	}
	
	@ActionHandler
	public void damageAreaAbility(EntityDirectAttackAction action) {
		if ( attacking )
			return;
		
		attacking = true;
		Location victimLocation = action.getVictim().getLoc();
		Entity entity = action.getEntity();
		for ( Entity target : action.getEntity().getLevel().getEntities()) {
			if ( victimLocation.distance(target.getLoc()) <= radius && target != entity &&
					(entity instanceof Attacker ? target instanceof Defender : target instanceof Attacker))
				action.getEntity().attack((LivingEntity)target);
		}
		attacking = false;
	}
}
