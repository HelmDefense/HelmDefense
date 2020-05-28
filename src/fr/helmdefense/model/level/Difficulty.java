package fr.helmdefense.model.level;

import fr.helmdefense.model.entities.utils.Tier;

public enum Difficulty {
	EASY(Tier.TIER_1),
	NORMAL(Tier.TIER_2),
	HARD(Tier.TIER_3);
	
	private Tier tier;
	
	public static final Difficulty DEFAULT = EASY;
	
	private Difficulty(Tier tier) {
		this.tier = tier;
	}
	
	public Tier getTier() {
		return this.tier;
	}
}