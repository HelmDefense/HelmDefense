package fr.helmdefense.model.entities.abilities.list;

import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.abilities.AbilityAction;
import fr.helmdefense.model.entities.abilities.actions.entity.EntityKillAction;
import fr.helmdefense.model.entities.utils.Tier;

public class GoldStealing extends Ability {
	public GoldStealing() {
		super(Tier.TIER_1);
	}
	
	@AbilityAction
	public void onEntityKillAction(EntityKillAction action) {
		/*if ( bourseDuJoueur - action.getVictim().getStats().getRewards() > 0 )
			Bourse du joueur -= 3 ( tier 1 ) ; 6 ( tier 2 ) ; 9 ( tier 3 );
		else if ( bourseDuJoueur < action.getVictim().getStats().getRewards())
			BourseDuJoueur = 0;
		*/
	}
}
