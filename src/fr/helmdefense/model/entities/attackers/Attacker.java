package fr.helmdefense.model.entities.attackers;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.list.RandomMoveAbility;
import fr.helmdefense.model.entities.utils.Location;
import fr.helmdefense.model.entities.utils.Tier;

public abstract class Attacker extends Entity {

	public Attacker(Location loc) {
		super(loc);
		
		this.addAbilities(new RandomMoveAbility(Tier.TIER_0, this));
	}

	public Attacker(int x, int y) {
		this(new Location(x, y));
	}

}
